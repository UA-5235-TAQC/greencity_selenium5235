package org.greencity.ui;

import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MainTextFieldValidationTest extends BaseTestRunner {

    private CreateNewsPage createNewsPage;
    private static String tooLongText = "A".repeat(100);

    @BeforeClass
    public void LoginUser() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);
        createNewsPage = homePage.getHeader().clickEcoNewsLink().clickCreateNews();
        createNewsPage.getHeader().changeToEN();
    }

    @BeforeMethod
    public void beforeMethod() {
        createNewsPage = createNewsPage.open().reload();
        createNewsPage.enterTitle("Test");
        createNewsPage.clickTagByName(EcoNewsTag.NEWS.getEn());
    }

    @Test
    public void verifyMainTextValidationAndPublishButtonLogic20() {

        createNewsPage.getContentComponent().enterContent("Short text");
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publish button must be disabled when content is invalid");
        Assert.assertTrue(createNewsPage.getContentComponent().isContentWarningDisplayed(), "Warning should be displayed for content shorter than 20 symbols");
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publish button must be disabled when content is invalid");

    }


    @Test
    public void verifyMainTextValidationAndPublishButtonLogicValidContent() {
        String validContent = "This is a valid test content with more than twenty symbols.";
        createNewsPage.getContentComponent().enterContent(validContent);

        Assert.assertFalse(createNewsPage.getContentComponent().isContentWarningDisplayed(), "Warning should disappear for valid content");

        Assert.assertTrue(createNewsPage.isPublishButtonEnabled(), "Publish button should be enabled for valid content");
        createNewsPage.clickPublish();
        String message = new EcoNewsPage(driver).getMessageText();
        Assert.assertEquals(message, "Your news has been successfully published", "Success message text is incorrect");
    }

    @Test(enabled = false)
    public void verifyMainTextValidationAndPublishButtonLogic63206() {
        for (int i = 0; i < 270; i++) {
            createNewsPage.getContentComponent().enterContentNotClear(tooLongText);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        Assert.assertEquals(createNewsPage.getContentComponent().getContent().length(), 63206, "Content should be truncated to 63 206 symbols");

        Assert.assertFalse(createNewsPage.getContentComponent().isContentWarningDisplayed(), "Warning should not be displayed after truncation");
    }
}