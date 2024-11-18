package com.steven.chameleon.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chameleon.auth.ldap")
public class LdapProperties {
    private boolean enabled = false;
    private String urls;
    private String base;
    private String username;
    private String password;
    
    @NestedConfigurationProperty
    private Pool pool = new Pool();
    
    @Data
    public static class Pool {
        private int maxTotal = 8;
        private int maxIdle = 8;
        private int minIdle = 0;
    }
}
