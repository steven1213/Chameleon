package com.steven.chameleon.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(LdapProperties.class)
@ConditionalOnProperty(prefix = "chameleon.auth.ldap", name = "enabled", havingValue = "true")
public class LdapConfig {

    @Bean
    public LdapContextSource ldapContextSource(LdapProperties properties) {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(properties.getUrls());
        contextSource.setBase(properties.getBase());
        contextSource.setUserDn(properties.getUsername());
        contextSource.setPassword(properties.getPassword());
        
        Map<String, Object> baseEnv = new HashMap<>();
        baseEnv.put("com.sun.jndi.ldap.connect.pool", "true");
        baseEnv.put("com.sun.jndi.ldap.connect.pool.maxsize", 
            String.valueOf(properties.getPool().getMaxTotal()));
        contextSource.setBaseEnvironmentProperties(baseEnv);
        
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(LdapContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
}
