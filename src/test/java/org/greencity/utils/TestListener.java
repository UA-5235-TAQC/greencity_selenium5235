package org.greencity.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();

        if (instance instanceof BaseTestRunner baseTest) {
            WebDriver driver = baseTest.getDriver();

            if (driver != null && Allure.getLifecycle().getCurrentTestCase().isPresent()) {
                try {
                    saveScreenshot(driver);
                } catch (Exception ignored) {}
            }
        }
    }


    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
