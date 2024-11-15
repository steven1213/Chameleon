package com.steven.chameleon.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "chameleon.spring")
public class ChameleonProperties {

    /**
     * 是否启用Web功能
     */
    private boolean enableWeb = true;

    /**
     * 是否启用AOP功能
     */
    private boolean enableAop = true;

    /**
     * 包扫描路径
     */
    private String[] basePackages;

    /**
     * Web相关配置
     */
    private WebProperties web = new WebProperties();

    public static class WebProperties {
        /**
         * 跨域配置
         */
        private CorsProperties cors = new CorsProperties();

        public static class CorsProperties {
            private boolean enabled = false;
            private String[] allowedOrigins = {"*"};
            private String[] allowedMethods = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
            private String[] allowedHeaders = {"*"};
            private boolean allowCredentials = true;
            private long maxAge = 3600;

            // getters and setters
            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String[] getAllowedOrigins() {
                return allowedOrigins;
            }

            public void setAllowedOrigins(String[] allowedOrigins) {
                this.allowedOrigins = allowedOrigins;
            }

            public String[] getAllowedMethods() {
                return allowedMethods;
            }

            public void setAllowedMethods(String[] allowedMethods) {
                this.allowedMethods = allowedMethods;
            }

            public String[] getAllowedHeaders() {
                return allowedHeaders;
            }

            public void setAllowedHeaders(String[] allowedHeaders) {
                this.allowedHeaders = allowedHeaders;
            }

            public boolean isAllowCredentials() {
                return allowCredentials;
            }

            public void setAllowCredentials(boolean allowCredentials) {
                this.allowCredentials = allowCredentials;
            }

            public long getMaxAge() {
                return maxAge;
            }

            public void setMaxAge(long maxAge) {
                this.maxAge = maxAge;
            }
        }

        // getters and setters
        public CorsProperties getCors() {
            return cors;
        }

        public void setCors(CorsProperties cors) {
            this.cors = cors;
        }
    }

    // getters and setters
    public boolean isEnableWeb() {
        return enableWeb;
    }

    public void setEnableWeb(boolean enableWeb) {
        this.enableWeb = enableWeb;
    }

    public boolean isEnableAop() {
        return enableAop;
    }

    public void setEnableAop(boolean enableAop) {
        this.enableAop = enableAop;
    }

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public WebProperties getWeb() {
        return web;
    }

    public void setWeb(WebProperties web) {
        this.web = web;
    }
}
