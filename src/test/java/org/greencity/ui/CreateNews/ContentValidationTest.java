package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.testrunners.CreateNews.CreateNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.greencity.utils.ui.NewsTestData.VALID_CONTENT;

@Tag("Create News")
@Epic("EcoNews Management")
@Feature("Create News")
@Story("Content field validation")
@Severity(SeverityLevel.NORMAL)
public class ContentValidationTest extends CreateNewsENTestRunner {

    private static String tooLongText = "A".repeat(100);

    @BeforeMethod
    public void beforeMethod() {
        super.beforeMethod();
        createNewsPage.enterTitle("Test");
        createNewsPage.clickTagByName(EcoNewsTag.NEWS.getEn());
    }

    @Description("Verify that the publish button is disabled and a warning is displayed when the content is shorter than 20 characters")
    @Test
    public void verifyContentValidationAndPublishButtonLogic20() {

        createNewsPage.getContentComponent().enterContent("Short text");
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publish button must be disabled when content is invalid");
        Assert.assertTrue(createNewsPage.getContentComponent().isContentWarningDisplayed(), "Warning should be displayed for content shorter than 20 symbols");
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publish button must be disabled when content is invalid");

    }

    @Description("Verify that the publish button is enabled and the warning disappears when the content is valid")
    @Test
    public void verifyContentValidationAndPublishButtonLogicValidContent() {
        String validContent = "This is a valid test content with more than twenty symbols.";
        createNewsPage.getContentComponent().enterContent(validContent);

        Assert.assertFalse(createNewsPage.getContentComponent().isContentMessageInvalid(), "Warning should disappear for valid content");

        Assert.assertTrue(createNewsPage.isPublishButtonEnabled(), "Publish button should be enabled for valid content");
        createNewsPage.clickPublish();
        String message = new EcoNewsPage(driver).getMessageText();
        Assert.assertEquals(message, "Your news has been successfully published", "Success message text is incorrect");
    }

    @Description("Stress test: verify that the content is truncated correctly to 63,206 symbols")
    @Test(enabled = false)
    public void verifyContentValidationAndPublishButtonLogic63206() {
        for (int i = 0; i < 270; i++) {
            createNewsPage.getContentComponent().enterContentNotClear(tooLongText);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        Assert.assertEquals(createNewsPage.getContentComponent().getContentText().length(), 63206, "Content should be truncated to 63 206 symbols");

        Assert.assertFalse(createNewsPage.getContentComponent().isContentWarningDisplayed(), "Warning should not be displayed after truncation");
    }

    @Description("Verify that the content field validation fails for too short input and passes for valid content")
    @Test
    public void verifyTooShortContent() {
        createNewsPage.getContentComponent().enterContent("1");

        Assert.assertFalse(createNewsPage.getContentComponent().isContentValid());

        createNewsPage.getContentComponent().enterContent(VALID_CONTENT);
        Assert.assertTrue(createNewsPage.getContentComponent().isContentValid(), "Content should be valid after all fields are valid.");
    }
}
