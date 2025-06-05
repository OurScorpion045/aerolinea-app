package com.aerolinea.util;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                throw new RuntimeException("No se encontro config.properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
