package org.greencity.ui;

import org.greencity.ui.pages.HomePage;
import org.greencity.ui.testrunners.TestRunnerWithUser;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstTestWithUser extends TestRunnerWithUser {

    @BeforeClass
    public void loginUser() {
        loginUser(new HomePage(driver).open());
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(testValueProvider.getBaseUIGreenCityUrl());
    }

    @Test
    public void isLogin() {
        HomePage homePage = new HomePage(driver);
        String currentUrl = homePage.getCurrentUrl();
        Assert.assertEquals(currentUrl, testValueProvider.getBaseUIGreenCityUrl());
        homePage.getHeader().clickEcoNewsLink();
        currentUrl = homePage.getCurrentUrl();
        Assert.assertEquals(currentUrl, testValueProvider.getBaseUIGreenCityUrl() + "/news");
    }
}
