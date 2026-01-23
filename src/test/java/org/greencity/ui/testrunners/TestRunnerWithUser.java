package org.greencity.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.greencity.ui.pages.HomePage;
import org.greencity.utils.TestValueProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class TestRunnerWithUser extends BaseTestRunner {




    @BeforeClass
    public void loginUser() {
        new HomePage(driver)
                .open()
                .getHeader()
                .clickSignInLink()
                .enterEmail(testValueProvider.getUserEmail())
                .enterPassword(testValueProvider.getUserPassword())
                .clickSubmit();
    }



}
