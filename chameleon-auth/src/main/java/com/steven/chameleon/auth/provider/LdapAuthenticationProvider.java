package com.steven.chameleon.auth.provider;

import com.steven.chameleon.auth.service.LoginAttemptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(prefix = "chameleon.auth.ldap", name = "enabled", havingValue = "true")
@Slf4j
public class LdapAuthenticationProvider implements AuthenticationProvider {

    Logger log = LoggerFactory.getLogger(LdapAuthenticationProvider.class);
    
    @Autowired
    private LdapTemplate ldapTemplate;
    
    @Autowired
    private LoginAttemptService loginAttemptService;
    
    @Value("${chameleon.auth.ldap.base}")
    private String ldapBaseDn;
    
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        if (loginAttemptService.isBlocked(username)) {
            throw new LockedException("账户已被锁定");
        }
        
        try {
            AndFilter filter = new AndFilter();
            filter.and(new EqualsFilter("objectClass", "person"))
                  .and(new EqualsFilter("uid", username));
            
            boolean authenticated = ldapTemplate.authenticate(ldapBaseDn, 
                filter.toString(), password);
            
            if (authenticated) {
                List<GrantedAuthority> authorities = getLdapUserAuthorities(username);
                return new UsernamePasswordAuthenticationToken(username, password, authorities);
            }
            
            loginAttemptService.loginFailed(username);
            throw new BadCredentialsException("Invalid credentials");
            
        } catch (Exception e) {
            log.error("LDAP authentication failed for user: " + username, e);
            loginAttemptService.loginFailed(username);
            throw new AuthenticationServiceException("LDAP authentication failed", e);
        }
    }
    
    private List<GrantedAuthority> getLdapUserAuthorities(String username) {
        // 从LDAP获取用户组信息并映射为权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}