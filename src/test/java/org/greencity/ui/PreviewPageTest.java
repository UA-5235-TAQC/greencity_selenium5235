package org.greencity.ui;

import org.greencity.ui.components.AuthModal.SignInModal;
import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.MySpace.MySpaceBasePage;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.greencity.ui.pages.NewsPreviewPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.greencity.utils.TestValueProvider;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.thread.ThreadExecutionException;

import java.util.List;

public class PreviewPageTest extends BaseTestRunner {
    CreateNewsPage createNewsPage;

    @BeforeClass
    public void LoginUser() {
        createNewsPage = new CreateNewsPage(driver);
        loginUser(createNewsPage);
    }

    @Test
    public void checkPreviewPage() {
        String newsTitle = "Super news title";
        String newsText = "This is a test preview content";

        createNewsPage
                .getHeader()
                .changeToEN();
        createNewsPage
                .enterTitle(newsTitle)
                .enterContent(newsText)
                .clickPreview();

        NewsPreviewPage previewPage = new NewsPreviewPage(driver);
        Assert.assertEquals(previewPage.getNewsTitle(), newsTitle);
        Assert.assertEquals(previewPage.getNewsText(), newsText);
    }
}
