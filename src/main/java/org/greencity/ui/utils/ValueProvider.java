package org.greencity.ui.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ValueProvider {
    private Properties properties;

    public ValueProvider() {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException err) {
            System.out.println("Error loading config.properties");
            System.out.println("Use system properties");
        }
    }

    public String getBaseUIGreenCityUrl() {
        return properties != null ? properties.getProperty("base.ui.greencity.url") : System.getProperty("BASE_UI_GREEN_CITY_URL");
    }

    // Added: generic getter that reads from application.properties when available,
    // otherwise falls back to a system property with the same key.
    public String get(String key) {
        if (properties != null) {
            return properties.getProperty(key);
        }
        return System.getProperty(key);
    }
}
