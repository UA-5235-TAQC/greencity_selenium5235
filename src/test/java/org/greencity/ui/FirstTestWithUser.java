package org.greencity.ui;

import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class FirstTestWithUser extends BaseTestRunner {

    HomePage homePage;

    @BeforeClass
    public void LoginUser() {
        homePage = new HomePage(driver);
        loginUser(homePage);
    }

    @BeforeMethod
    public void beforeMethod() {
        homePage.open();
    }

    @Test
    public void isLogin() {
        String currentUrl = homePage.getCurrentUrl();
        Assert.assertEquals(currentUrl, testValueProvider.getBaseUIGreenCityUrl());
        homePage.getHeader().clickEcoNewsLink();
        currentUrl = homePage.getCurrentUrl();
        Assert.assertEquals(currentUrl, testValueProvider.getBaseUIGreenCityUrl() + "/news");
    }

    @Test
    public void isUserLoggedIn() {
        MySpaceHabitsTabPage mySpace = homePage.getHeader().clickMySpace();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(mySpace.getUserName(), testValueProvider.getUserName());
        softAssert.assertEquals(mySpace.getProfilePanel().getLocation(), "Kyiv, Ukraine");
        softAssert.assertEquals(mySpace.getUserRating(), "Rate: 1940");
        softAssert.assertAll();
    }
}
