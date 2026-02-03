package org.greencity.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.greencity.ui.pages.BasePage;
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
    protected static TestValueProvider testValueProvider;
    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
        testValueProvider = new TestValueProvider();
    }

    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        // Allow remote origins (used by newer Chrome/Chromedriver combinations)
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popups-blocking");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // Extra flags that help Chrome run reliably in CI containers
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");

        if (testValueProvider.isHeadlessMode()) {
            // use new headless mode when available
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        Long implicitlyWait = testValueProvider.getImplicitlyWait();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("beforeClass in BaseTestRunner");
        System.out.println("getBaseUIGreenCityUrl: " + testValueProvider.getBaseUIGreenCityUrl());
        initDriver();
        System.out.println("driver: " + driver.toString());
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

    public MySpaceHabitsTabPage loginUser(BasePage basePage) {
        MySpaceHabitsTabPage mySpace = basePage
                .open()
                .getHeader()
                .clickSignInLink()
                .enterEmail(testValueProvider.getUserEmail())
                .enterPassword(testValueProvider.getUserPassword())
                .clickSubmit()
                .waitUntilOpened();

        return mySpace;
    }
}
