package org.greencity.ui;

import org.greencity.ui.pages.HomePage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstTest extends BaseTestRunner {

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://www.greencity.cx.ua/#/greenCity");
    }

    @Test
    public void firstTest() {
        HomePage homePage = new HomePage(driver);
        String currentUrl = homePage.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.greencity.cx.ua/#/greenCity");
        homePage.getHeader().clickEcoNewsLink();
        currentUrl = homePage.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.greencity.cx.ua/#/greenCity/news");

    }
}
