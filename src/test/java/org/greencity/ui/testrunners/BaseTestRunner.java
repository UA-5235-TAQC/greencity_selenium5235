package org.greencity.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.greencity.utils.ui.DriverManager;
import org.greencity.utils.ui.BaseAllureListener;
import org.greencity.utils.TestValueProvider;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.time.Duration;

@Listeners(BaseAllureListener.class)
public class BaseTestRunner {
    protected WebDriver driver;
    protected static TestValueProvider testValueProvider;


    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        WebDriverManager.chromedriver().setup();
        testValueProvider = new TestValueProvider();
    }

    public WebDriver initDriver() {
        ChromeOptions options = new ChromeOptions();
        if (testValueProvider.isHeadlessMode()) {
            // use new headless mode when available
            options.addArguments("--headless=new");
        }
        // Allow remote origins (used by newer Chrome/Chromedriver combinations)
        options.addArguments("--window-size=2560,1440");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popups-blocking");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // Extra flags that help Chrome run reliably in CI containers
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");


        WebDriver driver = new ChromeDriver(options);
        Dimension dimension = new Dimension(2560, 1440);
        driver.manage().window().setSize(dimension);
        Long implicitlyWait = testValueProvider.getImplicitlyWait();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = initDriver();
        DriverManager.setDriver(driver);
        driver.get(testValueProvider.getBaseUIGreenCityUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.quit();
        }
        DriverManager.removeDriver();
    }

    @Severity(SeverityLevel.CRITICAL)
    protected MySpaceHabitsTabPage LoginUser() {
        return new HomePage(getDriver())
                .open()
                .getHeader()
                .changeToEN()
                .clickSignInLink()
                .loginAs(testValueProvider.getUserEmail(), testValueProvider.getUserPassword());
    }

}
