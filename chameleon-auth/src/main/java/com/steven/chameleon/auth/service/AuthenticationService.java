package com.steven.chameleon.auth.service;

import com.steven.chameleon.auth.model.LoginRequest;
import com.steven.chameleon.auth.provider.LdapAuthenticationProvider;
import com.steven.chameleon.auth.provider.LocalAuthenticationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    @Autowired(required = false)
    private LdapAuthenticationProvider ldapAuthenticationProvider;
    
    @Autowired
   private LocalAuthenticationProvider localAuthenticationProvider;
    
    @Autowired
    private LoginAttemptService loginAttemptService;
    
    public Authentication authenticate(LoginRequest request) {
        String username = request.getUsername();
        
        if (loginAttemptService.isBlocked(username)) {
            throw new LockedException("账户已被锁定");
        }
        
        try {
            Authentication token = new UsernamePasswordAuthenticationToken(
                username, request.getPassword());
                
            return switch (request.getLoginType()) {
                case LDAP -> {
                    if (ldapAuthenticationProvider == null) {
                        throw new AuthenticationServiceException("LDAP认证未启用");
                    }
                    yield ldapAuthenticationProvider.authenticate(token);
                }
                case LOCAL -> localAuthenticationProvider.authenticate(token);
                case SSO -> null;
            };
            
        } catch (AuthenticationException e) {
            loginAttemptService.loginFailed(username);
            throw e;
        }
    }
}