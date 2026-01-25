package org.greencity.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
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
        options.setBinary("/usr/bin/brave-browser"); //TODO: remove for others
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popups-blocking");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30L));
    }

    @BeforeClass
    public void beforeClass() {
        initDriver();
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
}
