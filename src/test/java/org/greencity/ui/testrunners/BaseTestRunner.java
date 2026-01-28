package org.greencity.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.greencity.ui.pages.BasePage;
import org.greencity.ui.pages.MySpace.MySpaceEventsTabPage;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.greencity.utils.TestValueProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseTestRunner {

    protected WebDriver driver;
    protected static TestValueProvider testValueProvider;

    @BeforeSuite
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
        testValueProvider = new TestValueProvider();
    }

    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popups-blocking");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        Long implicitlyWait = testValueProvider.getImplicitlyWait();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
    }

    @BeforeClass
    public void beforeClass() {
        initDriver();
        driver.get(testValueProvider.getBaseUIGreenCityUrl());
    }

    @AfterClass
    public void afterClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void loginUser(BasePage basePage) {
        MySpaceHabitsTabPage mySpace = basePage.open()
                .getHeader()
                .clickSignInLink()
                .enterEmail(testValueProvider.getUserEmail())
                .enterPassword(testValueProvider.getUserPassword())
                .clickSubmit();

        if (!mySpace.isPageOpened()) {
            throw new AssertionError("Login failed: MySpace page was not opened");
        }
    }
}
