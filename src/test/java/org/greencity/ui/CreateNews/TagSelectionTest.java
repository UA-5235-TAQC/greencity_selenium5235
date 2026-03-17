package org.greencity.ui.CreateNews;

import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.UbsCourierPage;
import org.greencity.ui.testrunners.CreateNews.CreateNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class TagSelectionTest extends CreateNewsENTestRunner {

    @Test
    public void checkOneTagSelection() {
        // 1. Navigate to GreenCity News and click "Create News".
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");

        // 2. In the "Tag" field, select one tag ("News").
        // 3. Fill in the required fields: Title: "Test", Main Text: "Test content with 20 chars"
        // 4. Click "Publish".
        List<String> tagNames = List.of(EcoNewsTag.NEWS.getEn());
        createNewsPage.createNews("Test", tagNames, null, "Test content with 20 chars", null);
        createNewsPage.clickPublish();

        // 5. Verify that the news is published with the "News" tag.
        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.waitUntilOpened();
        Assert.assertTrue(ecoNewsPage.isPageOpened(),
                "Eco News page should be opened after publishing a news");

        NewsListItemComponent newsListItem = ecoNewsPage.getNewsCardByIndex(0);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(newsListItem.getTitle(), "Test", "News should have 'Test' title");
        softAssert.assertTrue(newsListItem.hasTags(tagNames), "News should have 'News' tag");
        softAssert.assertAll();
        ecoNewsPage.removeAllSelectedTags();
    }

    @Test(dependsOnMethods = {"checkOneTagSelection"})
    public void checkThreeTagsSelection() {
        // 6. Open the "Create News" form again.
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");

        // 7. Select three tags: "News", "Events", "Education".
        // 8. Click "Publish".
        List<String> tagNames = List.of(EcoNewsTag.NEWS.getEn(), EcoNewsTag.EVENTS.getEn(), EcoNewsTag.EDUCATION.getEn());
        createNewsPage.createNews("Test_2", tagNames, null, "Test content with 20 chars", null);
        createNewsPage.clickPublish();

        // 9. Verify that the news is published with all three selected tags.
        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.waitUntilOpened();
        Assert.assertTrue(ecoNewsPage.isPageOpened(),
                "Eco News page should be opened after publishing a news");

        NewsListItemComponent newsListItem = ecoNewsPage.getNewsCardByIndex(0);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(newsListItem.getTitle(), "Test_2", "News should have 'Test_2' title");
        softAssert.assertTrue(newsListItem.hasTags(tagNames), "News should have 'News', 'Events' and 'Education' tags");
        softAssert.assertAll();
        ecoNewsPage.removeAllSelectedTags();
    }

    @Test(dependsOnMethods = {"checkThreeTagsSelection"})
    public void checkFourTagsSelection() {
        // 10. Attempt to select a fourth tag ("Initiatives").
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");

        List<String> tagNames = List.of(EcoNewsTag.NEWS.getEn(), EcoNewsTag.EVENTS.getEn(), EcoNewsTag.EDUCATION.getEn(), EcoNewsTag.INITIATIVES.getEn());
        createNewsPage.selectTags(tagNames);

        // 11. Verify that selecting a fourth tag is blocked.
        // NOTE: in TagItem rootElement (button) doesn't work, so we click on a name inside the button, and it actually allows us to select more than 3 items.
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publishing a news with 4 tags should be blocked");

        // NOTE: we need to cancel news creation here, as without it the confirmation modal will appear and prevent other tests from working correctly.
        createNewsPage.clearAllSelectedTags();
        createNewsPage.clickCancel();
        Assert.assertTrue(createNewsPage.isCancelModalDisplayed(),
                "Confirmation modal should appear after clicking Cancel");
        CancelModalComponent cancelModal = createNewsPage.getCancelModal();
        cancelModal.clickYesCancel();
        UbsCourierPage ubsCourierPage = cancelModal.clickYesCancel().open();
        Assert.assertFalse(createNewsPage.isCancelModalDisplayed(),
                "Cancel modal should be closed after clicking 'Yes, cancel'");
        Assert.assertFalse(createNewsPage.isPageOpenedSafe(),
                "User should be redirected away from Create News page after canceling");
        Assert.assertTrue(ubsCourierPage.isPageOpenedAfterCancelModalClickYesCancel(),
                "User should be directed to Ubs Courier page after canceling");
    }
}
