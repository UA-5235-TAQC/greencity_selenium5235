package org.greencity.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.greencity.ui.pages.BasePage;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.greencity.utils.DriverManager;
import org.greencity.utils.BaseAllureListener;
import org.greencity.utils.TestValueProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.time.Duration;

@Listeners(BaseAllureListener.class)
public class BaseTestRunner {
    protected static TestValueProvider testValueProvider;
    public WebDriver driver;


    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
        testValueProvider = new TestValueProvider();
    }

    public WebDriver initDriver() {
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
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        Long implicitlyWait = testValueProvider.getImplicitlyWait();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        WebDriver driver = initDriver();
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
