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

        if (properties != null) {
            return properties.getProperty("base.ui.greencity.url");
        }

        return System.getenv("BASE_UI_GREEN_CITY_URL");
    }


    // Convenience accessors for values present in src/test/resources/config.properties
    public String getUserName() {
        if (properties != null) {
            return properties.getProperty("user.name");
        }
        return System.getenv("USER_NAME");
    }

    public String getUserEmail() {
        if (properties != null) {
            return properties.getProperty("user.email");
        }
        return System.getenv("USER_EMAIL");
    }

    public String getUserPassword() {
        if (properties != null) {
            return properties.getProperty("user.password");
        }
        return System.getenv("USER_PASSWORD");
    }

    public String getUserId() {

        if (properties != null) {
            return properties.getProperty("user.id");
        }
        return System.getenv("USER_ID");
    }

    public Long getImplicitlyWait() {

        if (properties != null) {
            return Long.parseLong(properties.getProperty("implicitlyWait"));
        }
        return Long.parseLong(System.getenv("IMPLICITLY_WAIT"));
    }

    public String getUserLocation() {
        if (properties != null) {
            return properties.getProperty("user.location");
        }
        return System.getenv("USER_LOCATION");
    }

    public Integer getUserRate() {
        if (properties != null) {
            return Integer.parseInt(properties.getProperty("user.rating"));
        }
        return Integer.parseInt(System.getenv("USER_RATING"));
    }

    public boolean isHeadlessMode() {
        if (properties != null) {
            return Boolean.parseBoolean(properties.getProperty("headless.mode"));
        }
        return Boolean.parseBoolean(System.getenv("HEADLESS_MODE"));
    }
}
