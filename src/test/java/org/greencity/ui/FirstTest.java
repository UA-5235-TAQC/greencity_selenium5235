package org.greencity.ui;

import org.greencity.ui.components.AuthModal.SignUpModal;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class FirstTest extends BaseTestRunner {

    @BeforeMethod
    public void beforeMethod() {
        driver.get(testValueProvider.getBaseUIGreenCityUrl());
    }

//    @Test
//    public void firstTest() {
//        HomePage homePage = new HomePage(driver);
//        String currentUrl = homePage.getCurrentUrl();
//        Assert.assertEquals(currentUrl, testValueProvider.getBaseUIGreenCityUrl());
//        homePage.getHeader().clickEcoNewsLink();
//        currentUrl = homePage.getCurrentUrl();
//        Assert.assertEquals(currentUrl, testValueProvider.getBaseUIGreenCityUrl() + "/news");
//
//    }
//
//    @Test
//    public void secondTest() {
//        SignUpModal signUpModal = new HomePage(driver)
//                .getHeader()
//                .clickSignUpLink()
//                .enterUsername(testValueProvider.getUserName())
//                .enterPassword(testValueProvider.getUserPassword())
//                .enterConfirmPassword(testValueProvider.getUserPassword())
//                .togglePasswordVisibility();
//
//    }

    @Test
    public void secondTest() {
        SignUpModal signUpModal = new HomePage(driver)
                .getHeader()
                .clickSignUpLink()
                .enterUsername(testValueProvider.getUserName())
                .enterPassword(testValueProvider.getUserPassword())
                .enterConfirmPassword(testValueProvider.getUserPassword())
                .togglePasswordVisibility();

    }

    @Test
    public void thirdTest() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened());

        String currentUrl = homePage.getCurrentUrl();
        System.out.println(currentUrl);

        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.open();
        currentUrl = ecoNewsPage.getCurrentUrl();
        System.out.println(currentUrl);

    }
}
