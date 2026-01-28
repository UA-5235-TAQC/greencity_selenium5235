package org.greencity.ui;

import org.greencity.ui.pages.EcoNewsPage;
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
        homePage.getHeader().changeToEN();
    }

    @Test
    public void isLogin() {
        String currentUrl = homePage.getCurrentUrl();
        Assert.assertEquals(currentUrl, testValueProvider.getBaseUIGreenCityUrl() + "#/greenCity");
        EcoNewsPage ecoNewsPage = homePage.getHeader().clickEcoNewsLink();
        currentUrl = ecoNewsPage.getCurrentUrl();
        Assert.assertEquals(currentUrl, testValueProvider.getBaseUIGreenCityUrl() + "#/greenCity/news");
    }

    @Test
    public  void isUserLoggedIn() {
        MySpaceHabitsTabPage mySpace = homePage.getHeader().clickMySpace();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(mySpace.getUserName(), testValueProvider.getUserName());
        softAssert.assertEquals(mySpace.getProfilePanel().getLocation(), testValueProvider.getUserLocation());
        softAssert.assertEquals(mySpace.getProfilePanel().getRate(), testValueProvider.getUserRate());
        softAssert.assertAll();
    }
}
