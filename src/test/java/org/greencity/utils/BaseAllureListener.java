//package org.greencity.utils;
//
//import io.qameta.allure.Allure;
//import io.qameta.allure.Attachment;
//import org.openqa.selenium.*;
//import org.openqa.selenium.logging.LogType;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//public class BaseAllureListener implements ITestListener {
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        attachFailureArtifacts();
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        attachFailureArtifacts();
//    }
//
//    private void attachFailureArtifacts() {
//        WebDriver driver = DriverManager.getDriver();
//        if (driver == null) return;
//
//        saveScreenshot(driver);
//        savePageSource(driver);
//        saveBrowserLogs(driver);
//
//    }
//
//    @Attachment(value = "Screenshot", type = "image/png", fileExtension = ".png")
//    public byte[] saveScreenshot(WebDriver driver) {
//        return ((TakesScreenshot) driver)
//                .getScreenshotAs(OutputType.BYTES);
//    }
//
//    @Attachment(value = "Page source", type = "text/html", fileExtension = ".html")
//    public String savePageSource(WebDriver driver) {
//        return driver.getPageSource();
//    }
//
//    @Attachment(value = "Browser logs", type = "text/plain", fileExtension = ".log")
//    public String saveBrowserLogs(WebDriver driver) {
//        try {
//            return driver.manage().logs()
//                    .get(LogType.BROWSER)
//                    .getAll()
//                    .toString();
//        } catch (Exception e) {
//            return "No browser logs";
//        }
//    }
//
//    // API attachments
//    public static void attachApiRequest(String request) {
//        Allure.addAttachment("API Request", "application/json", request, ".json");
//    }
//
//    public static void attachApiResponse(String response) {
//        Allure.addAttachment("API Response", "application/json", response, ".json");
//    }
//}

package org.greencity.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestListener;
import org.testng.ITestResult;


import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BaseAllureListener implements ITestListener {

    /* ==========================
       Test lifecycle hooks
       ========================== */

    @Override
    public void onTestFailure(ITestResult result) {
        attachArtifacts("Test failure");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Якщо skip через dependency — інколи теж корисно
        attachArtifacts("Test skipped");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // ❌ НІЧОГО НЕ ДОДАЄМО
        // Allure має бути чистим
    }

    /* ==========================
       Helpers
       ========================== */

    private void attachArtifacts(String reason) {
        // 🛑 Якщо тест кейс ще не стартував — виходимо
        if (!isTestRunning()) {
            return;
        }

        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            return;
        }

        attachScreenshot(driver);
        attachPageSource(driver);
        attachCurrentUrl(driver);
    }

    private boolean isTestRunning() {
        Optional<String> testCase = Allure.getLifecycle().getCurrentTestCase();
        return testCase.isPresent();
    }


    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachScreenshot(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }

    @Attachment(value = "Page source", type = "text/html")
    private byte[] attachPageSource(WebDriver driver) {
        try {
            return driver.getPageSource().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            return new byte[0];
        }
    }

    @Attachment(value = "Current URL", type = "text/plain")
    private String attachCurrentUrl(WebDriver driver) {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            return "URL not available";
        }
    }
}
