package com.automiz.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties PROPERTIES = loadProperties();

    private ConfigManager() {
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream in = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("Missing config.properties on test classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config.properties", e);
        }
        return props;
    }

    public static String get(String key) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp.trim();
        }

        String envKey = key.replace('.', '_').replace('-', '_').toUpperCase();
        String envVal = System.getenv(envKey);
        if (envVal != null && !envVal.isBlank()) {
            return envVal.trim();
        }

        String fileVal = PROPERTIES.getProperty(key);
        if (fileVal != null && !fileVal.isBlank()) {
            return fileVal.trim();
        }

        throw new IllegalArgumentException("Configuration key not found: " + key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}