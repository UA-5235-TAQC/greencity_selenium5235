package org.greencity.ui;

import org.greencity.ui.components.AuthModal.SignUpModal;
import org.greencity.ui.components.NewsListItemComponent;
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

//    @Test
//    public void testTest() {
//        EcoNewsPage signUpModal = new HomePage(driver)
//                .getHeader()
//                .clickEcoNewsLink();
//
//        List<NewsListItemComponent> list = signUpModal.getNewsList();
//
//        NewsListItemComponent firstNews = list.get(1);
//        firstNews.clickBookmark();
//        try {
//            Thread.sleep(3000);
//        } catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        System.out.println(">=>------------> " + firstNews.getTitle());
//        System.out.println(">=>------------> " + firstNews.getAuthorName());
//        System.out.println(">=>------------> " + firstNews.getNewsText());
//        System.out.println(">=>------------> " + firstNews.getCommentsCount());
//        System.out.println(">=>------------> " + firstNews.getCreationDate());
//        System.out.println(">=>------------> " + firstNews.getLikesCount());
//    }
}
