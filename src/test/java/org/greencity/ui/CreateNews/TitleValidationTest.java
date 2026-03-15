package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.testrunners.CreateNews.CreateNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.greencity.utils.ui.NewsTestData.TEST_TITLE_EN;
import static org.greencity.utils.ui.NewsTestData.VALID_CONTENT;

@Tag("Create News")
@Epic("EcoNews Management")
@Feature("Create News")
@Story("Title field validation")
@Severity(SeverityLevel.NORMAL)
@Issue("4")
public class TitleValidationTest extends CreateNewsENTestRunner {

    private final int MAX_LENGTH = 170;

    @Description("Verify the validation of the title field: empty, exceeded limit, and valid states.")
    @Test
    public void verifyTitleFieldAndPublishButtonLogic() {

        Allure.step("Step 1: Validate mandatory field with empty title", () -> {
            createNewsPage.enterTitle("");
            createNewsPage.getContentComponent().enterContent(""); // Triggering validation state

            Assert.assertTrue(createNewsPage.isTitleInvalid(),
                    "Title border should be red (ng-invalid) when empty.");
            Assert.assertFalse(createNewsPage.isPublishButtonEnabled(),
                    "Publish button should be disabled when the title is empty.");

            String expectedCounter = "0/" + MAX_LENGTH;
            Assert.assertEquals(createNewsPage.getTitleCounterText(), expectedCounter,
                    "Counter should display " + expectedCounter);
        });

        Allure.step("Step 2: Validate character limit (entering " + (MAX_LENGTH + 1) + " characters)", () -> {
            int exceededLength = MAX_LENGTH + 1;
            String longTitle = "A".repeat(exceededLength);
            createNewsPage.enterTitle(longTitle);

            Assert.assertEquals(createNewsPage.getTitleLength(), exceededLength,
                    "The field should contain " + exceededLength + " characters.");

            String expectedCounter = exceededLength + "/" + MAX_LENGTH;
            Assert.assertEquals(createNewsPage.getTitleCounterText(), expectedCounter,
                    "Counter should show " + expectedCounter);

            Assert.assertTrue(createNewsPage.isTitleInvalid(),
                    "Character counter should be highlighted in red (warning state) when limit exceeded.");
            Assert.assertFalse(createNewsPage.isPublishButtonEnabled(),
                    "Publish button should be disabled when the title exceeds " + MAX_LENGTH + " characters.");
        });

        Allure.step("Step 3: Return title to a valid state", () -> {
            createNewsPage.enterTitle(TEST_TITLE_EN);

            String expectedCounter = TEST_TITLE_EN.length() + "/" + MAX_LENGTH;
            Assert.assertEquals(createNewsPage.getTitleCounterText(), expectedCounter,
                    "Counter should display current length: " + expectedCounter);

            Assert.assertFalse(createNewsPage.isTitleInvalid(),
                    "Red highlight should disappear when the title length is valid (<= " + MAX_LENGTH + ").");
        });

        Allure.step("Step 4: Fill remaining mandatory fields", () -> {
            createNewsPage.clickTagByName(EcoNewsTag.NEWS.getEn());
            createNewsPage.getContentComponent().enterContent(VALID_CONTENT);
        });

        Allure.step("Step 5: Final check of Publish button activation", () -> {
            Assert.assertTrue(createNewsPage.isPublishButtonEnabled(),
                    "Publish button should become enabled after all fields (Title, Tag, Content) are valid.");
        });
    }
}