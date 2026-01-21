# greencity_selenium5235

This repository contains UI tests for the GreenCity project using Selenium and Maven.

## Requirements

- Java 8+ (JDK)
- Maven

## Running tests

From the repository root run:

```powershell
mvn test
```

You can pass system properties to override values from the properties file, for example:

```powershell
mvn test -Duser.name=localuser -Duser.email=you@example.com
```

## Configuration

Tests read configuration from `src/test/resources/config.properties` when present. The following keys are used by the tests and the new `TestValueProvider` helper:

- `base.ui.greencity.url` - base UI URL for GreenCity
- `user.name` - test user name
- `user.email` - test user email
- `user.password` - test user password
- `user.id` - test user id

If `config.properties` is not available, values may be provided via system properties using the same key names (for example `-Duser.name=...`). Note: the existing `getBaseUIGreenCityUrl()` method falls back to the system property `BASE_UI_GREEN_CITY_URL` (uppercase) for backward compatibility.

## TestValueProvider

`src/test/java/org/greencity/utils/TestValueProvider.java` provides a small helper to load test values. Highlights:

- Constructor attempts to load `src/test/resources/config.properties`.
- `get(String key)` returns the property value from the file (if loaded) or falls back to `System.getProperty(key)`.
- Convenience getters added:
  - `getUserName()` -> `user.name`
  - `getUserEmail()` -> `user.email`
  - `getUserPassword()` -> `user.password`
  - `getUserId()` -> `user.id`

Example usage in a test:

```java
TestValueProvider tvp = new TestValueProvider();
String user = tvp.getUserName();
String baseUrl = tvp.getBaseUIGreenCityUrl();
```

## Notes and next steps

- If you prefer `getBaseUIGreenCityUrl()` to use the same key-based fallback as `get(...)`, I can update it to call `get("base.ui.greencity.url")` and fall back to a single system property naming convention.
- I can also add a small unit test for `TestValueProvider` that verifies file load and system-property fallback.

---

If you want any changes to this README content or additional examples, tell me which sections to expand.
