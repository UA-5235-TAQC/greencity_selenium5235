package org.greencity.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestValueProvider {
    private Properties properties;

    public TestValueProvider() {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties")) {
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

    // Added: generic getter that reads from config.properties when available,
    // otherwise falls back to a system property with the same key.
    public String get(String key) {
        if (properties != null) {
            return properties.getProperty(key);
        }
        return System.getProperty(key);
    }

    // Convenience accessors for values present in src/test/resources/config.properties
    public String getUserName() {
        return get("user.name");
    }

    public String getUserEmail() { return get("user.email"); }

    public String getUserPassword() {
        return get("user.password");
    }

    public String getUserId() {
        return get("user.id");
    }

}
