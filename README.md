# greencity_selenium5235

This repository contains UI tests for the GreenCity project using Selenium and Maven.

---

## 📊 Allure Test Report

Проєкт використовує **Allure Report**, який автоматично генерується та публікується через **GitHub Actions**.

### 🔗 Main branch report

Актуальний звіт для гілки `main` доступний за посиланням:

👉 **[https://ua-5235-taqc.github.io/greencity_selenium5235/main](https://ua-5235-taqc.github.io/greencity_selenium5235/main)**

У звіті ви можете знайти:

* ✅ результати всіх тестів
* 📈 історію запусків (history & trends)
* 🔁 flaky та retried тести
* ⏱️ тривалість виконання
* 📊 статистику по тест-кейсам

---

### 🔍 Pull Request reports

Для кожного Pull Request автоматично створюється окремий Allure Report.
Посилання на нього додається **коментарем у PR** після завершення CI.


## Requirements

- Java 21 (JDK)
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

### Running tests in parallel with TestNG

A TestNG suite `testng.xml` is provided at the repository root that runs all tests in parallel at the method level. By default it sets `parallel="methods"` and `thread-count="8"`.

Run the suite with Maven:

```powershell
mvn -Dsurefire.suiteXmlFiles=testng.xml test
```

Notes and precautions:

- Adjust `thread-count` in `testng.xml` to match the available CPU and memory of your runner (CI or local machine).
- Make sure each test creates/uses its own WebDriver instance (avoid sharing a single driver across threads). The project uses `DriverManager` (in test classes) — ensure the implementation returns a thread-local driver when running in parallel.
- Parallel runs can surface race conditions and flaky tests; consider increasing waits or using explicit waits where needed.

## Configuration

Tests read configuration from `src/test/resources/config.properties` when present. The following keys are used by the tests and the `TestValueProvider` helper (defaults are applied when values are missing):

- `base.ui.greencity.url` - base UI URL for GreenCity
- `user.name` - test user name
- `user.email` - test user email
- `user.password` - test user password
- `user.id` - test user id
- `user.location` - user location
- `user.rating` - user rating (integer)
- `implicitlyWait` - implicit wait in seconds (integer, default 5)
- `headless.mode` - `true` or `false` (when absent, tests default to headless in CI environments)

If `config.properties` is not available, values may be provided via system properties using the same key names (for example `-Duser.name=...`). Note: the existing `getBaseUIGreenCityUrl()` method also falls back to the system property `BASE_UI_GREEN_CITY_URL` (uppercase) for backward compatibility.

### CI / GitHub Actions

When running in CI (GitHub Actions) the environment variable `CI=true` is normally present. `TestValueProvider` will default to headless mode when it detects `CI` to avoid browser startup issues on Linux runners. If you need to run with a visible browser in CI, explicitly pass `-Dheadless.mode=false` to `mvn test`.

If your CI workflow requires any additional Chrome options, update `src/test/java/org/greencity/ui/testrunners/BaseTestRunner.java` where ChromeOptions are configured.

## TestValueProvider

`src/test/java/org/greencity/utils/TestValueProvider.java` provides a small helper to load test values. Highlights:

- Constructor attempts to load `src/test/resources/config.properties`.
- `get(String key)` returns the property value from the file (if loaded) or falls back to `System.getProperty(key)`.
- Convenience getters added:
  - `getUserName()` -> `user.name`
  - `getUserEmail()` -> `user.email`
  - `getUserPassword()` -> `user.password`
  - `getUserId()` -> `user.id`
  - `getImplicitlyWait()` -> `implicitlyWait` (default 5)
  - `isHeadlessMode()` -> `headless.mode` (defaults to true in CI)

Example usage in a test:

```java
TestValueProvider tvp = new TestValueProvider();
String user = tvp.getUserName();
String baseUrl = tvp.getBaseUIGreenCityUrl();
```

## HTML Report Generation with Allure

After running your tests, you can generate and view the Allure HTML report.

### 1 Serve the Report (Quick View)

This command will automatically generate the report from your test results and open it in your default browser:

```powershell
allure serve target/allure-results
```

### 2 Save the Report (Permanent Copy)

To generate a report that you can save and open later:

```powershell
allure generate target/allure-results -o target/allure-report --clean
```

Then, open the report in your browser with one of the following options:

```powershell
allure open target\allure-report
```

Or directly open the HTML file:
`target/allure-report/index.html`


