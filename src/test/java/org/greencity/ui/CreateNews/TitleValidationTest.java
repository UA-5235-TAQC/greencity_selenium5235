package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.testrunners.CreateNews.CreateNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.greencity.utils.NewsTestData.VALID_CONTENT;

@Tag("Create News")
@Epic("EcoNews Management")
@Feature("Create News")
@Story("Title field validation")
@Severity(SeverityLevel.NORMAL)
@Issue("4")
public class TitleValidationTest extends CreateNewsENTestRunner {

    @Description("Verify the validation of the title field")
    @Test
    public void verifyTitleFieldAndPublishButtonLogic() {
        // 1. Mandatory field validation (Empty title)
        createNewsPage.enterTitle("");
        createNewsPage.getContentComponent().enterContent(""); // Triggering "touched" state for validation
        Assert.assertTrue(createNewsPage.isTitleInvalid(), "Title border should be red (ng-invalid) when empty.");
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publish button should be disabled when the title is empty.");
        Assert.assertEquals(createNewsPage.getTitleCounterText(), "0/170", "Counter should display 0/170.");

        // 2. Character limit validation (Entering 171 characters)
        String longTitle = "A".repeat(171);
        createNewsPage.enterTitle(longTitle);

        // Verify that the field currently contains 171 characters, even though the maxlength requirement is 170
        Assert.assertEquals(createNewsPage.getTitleLength(), 171, "The field should not allow inputting 171 characters.");

        // Verify that the counter shows "171/170" and is highlighted in red
        Assert.assertEquals(createNewsPage.getTitleCounterText(), "171/170", "Counter should show 171/170.");
        Assert.assertTrue(createNewsPage.isTitleInvalid(), "Character counter should be highlighted in red (warning state).");

        // Key point: the button must remain disabled because the limit is 170
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publish button should be disabled when the title exceeds 170 characters.");

        // 3. Returning to a valid state (9 characters)
        createNewsPage.enterTitle("Test News");
        Assert.assertEquals(createNewsPage.getTitleCounterText(), "9/170", "Counter should display 9/170.");
        Assert.assertFalse(createNewsPage.isTitleInvalid(), "Red highlight should disappear when the title is valid.");

        // 4. Filling the remaining mandatory fields
        createNewsPage.clickTagByName(EcoNewsTag.NEWS.getEn());
        createNewsPage.getContentComponent().enterContent(VALID_CONTENT);

        // 5. Final activation check
        Assert.assertTrue(createNewsPage.isPublishButtonEnabled(), "Publish button should become enabled after all fields are valid.");
    }
}
