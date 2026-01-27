package org.greencity.ui;

import org.greencity.ui.components.AuthModal.SignInModal;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.MySpace.MySpaceBasePage;
import org.greencity.ui.pages.NewsPreviewPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.greencity.utils.TestValueProvider;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.thread.ThreadExecutionException;

import java.util.List;

public class PreviewPageTest extends BaseTestRunner {
    TestValueProvider testValueProvider = new TestValueProvider();

    @BeforeMethod
    public void beforeMethod() {
        driver.get(testValueProvider.getBaseUIGreenCityUrl());
    }

    @Test
    public void checkPreviewPage () {
        String newsTitle = "Super news title";
        String newsText = "This is a test preview content";

        new HomePage(driver)
                .getHeader()
                .clickSignInLink()
                .loginAs(testValueProvider.getUserEmail(), testValueProvider.getUserPassword());
        NewsPreviewPage ecoNewsPage = new MySpaceBasePage(driver)
                .getHeader()
                .changeToEN()
                .clickEcoNewsLink()
                .clickCreateNews()
                .enterTitle(newsTitle)
                .enterContent(newsText)
                .clickPreview();

        NewsPreviewPage previewPage = new NewsPreviewPage(driver);
        Assert.assertEquals(previewPage.getNewsTitle(), newsTitle);
        Assert.assertEquals(previewPage.getNewsText(), newsText);

        try {
            Thread.sleep(3000);
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }
    }
}
