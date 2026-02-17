package org.greencity.utils.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class BaseAllureListener implements ITestListener {

    // API attachments
    public static void attachApiRequest(String request) {
        Allure.addAttachment("API Request", "application/json", request, ".json");
    }

    public static void attachApiResponse(String response) {
        Allure.addAttachment("API Response", "application/json", response, ".json");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachFailureArtifacts();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        attachFailureArtifacts();
    }

    private void attachFailureArtifacts() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) return;

        saveScreenshot(driver);
        savePageSource(driver);
        saveBrowserLogs(driver);

    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = ".png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/html", fileExtension = ".html")
    public String savePageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    @Attachment(value = "Browser logs", type = "text/plain", fileExtension = ".log")
    public String saveBrowserLogs(WebDriver driver) {
        try {
            return driver.manage().logs().get(LogType.BROWSER).getAll().toString();
        } catch (Exception e) {
            return "No browser logs";
        }
    }
}
