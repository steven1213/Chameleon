package com.steven.chameleon.core.util;

import com.steven.chameleon.core.config.DataSourceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    /**
     * 加载配置文件
     */
    public static Properties loadProperties(String fileName) {
        Properties props = new Properties();
        try (InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + fileName, e);
        }
        return props;
    }

    /**
     * 获取数据源配置
     */
    public static DataSourceConfig getDataSourceConfig(Properties props, String prefix) {
        DataSourceConfig config = new DataSourceConfig();
        config.setUrl(props.getProperty(prefix + ".url"));
        config.setUsername(props.getProperty(prefix + ".username"));
        config.setPassword(props.getProperty(prefix + ".password"));
        config.setDriverClassName(props.getProperty(prefix + ".driver-class-name"));
        return config;
    }

    /**
     * 验证必要的配置项
     */
    public static void validateConfig(Properties props, String... requiredKeys) {
        for (String key : requiredKeys) {
            if (!props.containsKey(key)) {
                throw new IllegalArgumentException("Missing required configuration: " + key);
            }
        }
    }
}
