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

        return System.getProperty("BASE_UI_GREEN_CITY_URL");
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

    public Long getImplicitlyWait() {
        String value = get("implicitlyWait");
        if (value != null) {
            try {
                return Long.valueOf(value);
            } catch (NumberFormatException ignored) {
            }
        }
        // sensible default to avoid failures when config isn't present
        return 5L;
    }

    public String getUserLocation() {
        return get("user.location");
    }

    public Integer getUserRate() {
        String value = get("user.rating");
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ignored) {
            }
        }
        return 0;
    }

    public boolean isHeadlessMode() {
        String value = get("headless.mode");
        if (value != null && !value.isEmpty()) {
            return Boolean.parseBoolean(value);
        }
        // Default to headless in CI environments (GitHub Actions sets CI=true)
        String ci = System.getenv("CI");
        if (ci != null && !ci.isEmpty()) {
            return true;
        }
        return false;
    }


}
