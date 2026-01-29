package org.greencity.ui;

import org.greencity.ui.components.CancelModalComponent;
import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class TagSelectionTest extends BaseTestRunner {

    private EcoNewsPage ecoNewsPage;

    @BeforeClass
    public void loginUserAndChangeLanguage() {
        HomePage homePage = new HomePage(driver);
        MySpaceHabitsTabPage mySpace = loginUser(homePage);
        mySpace.getHeader().changeToEN();
    }

    @BeforeMethod
    public void openEcoNewsPage() {
        ecoNewsPage = new EcoNewsPage(driver).open();
        Assert.assertTrue(ecoNewsPage.isPageOpened(), "Eco News page should be opened");
    }

    @AfterMethod
    public void removeAllTags() {
        // NOTE: due to site logic, selected tags aren't removed after publishing news.
        // NOTE: so if we don't remove the tags here, they will be automatically selected when creating new news and in the EcoNewsPage filter too.
        ecoNewsPage.removeAllSelectedTags();
    }

    @Test
    public void checkOneTagSelection() {
        // 1. Navigate to GreenCity News and click "Create News".
        CreateNewsPage createNewsPage = ecoNewsPage.clickCreateNews();
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");

        // 2. In the "Tag" field, select one tag ("News").
        // 3. Fill in the required fields: Title: "Test", Main Text: "Test content with 20 chars"
        // 4. Click "Publish".
        List<String> tagNames = List.of(EcoNewsTag.NEWS.getEn());
        createNewsPage.createNews("Test", tagNames, null, "Test content with 20 chars", null)
                .clickPublish();

        // 5. Verify that the news is published with the "News" tag.
        Assert.assertTrue(ecoNewsPage.isPageOpened(), "Eco News page should be opened after publishing a news");

        NewsListItemComponent newsListItem = ecoNewsPage.getNewsCardByIndex(0);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(newsListItem.getTitle(), "Test", "News should have 'Test' title");
        softAssert.assertTrue(newsListItem.hasTags(tagNames), "News should have 'News' tag");
        softAssert.assertAll();
    }

    @Test
    public void checkThreeTagsSelection() {
        // 6. Open the "Create News" form again.
        CreateNewsPage createNewsPage = ecoNewsPage.clickCreateNews();
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");

        // 7. Select three tags: "News", "Events", "Education".
        // 8. Click "Publish".
        List<String> tagNames = List.of(EcoNewsTag.NEWS.getEn(), EcoNewsTag.EVENTS.getEn(), EcoNewsTag.EDUCATION.getEn());
        createNewsPage.createNews("Test_2", tagNames, null, "Test content with 20 chars", null)
                .clickPublish();

        // 9. Verify that the news is published with all three selected tags.
        Assert.assertTrue(ecoNewsPage.isPageOpened(), "Eco News page should be opened after publishing a news");

        NewsListItemComponent newsListItem = ecoNewsPage.getNewsCardByIndex(0);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(newsListItem.getTitle(), "Test_2", "News should have 'Test_2' title");
        softAssert.assertTrue(newsListItem.hasTags(tagNames), "News should have 'News', 'Events' and 'Education' tags");
        softAssert.assertAll();
    }

    @Test
    public void checkFourTagsSelection() {
        // 10. Attempt to select a fourth tag ("Initiatives").
        CreateNewsPage createNewsPage = ecoNewsPage.clickCreateNews();
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");

        List<String> tagNames = List.of(EcoNewsTag.NEWS.getEn(), EcoNewsTag.EVENTS.getEn(), EcoNewsTag.EDUCATION.getEn(), EcoNewsTag.INITIATIVES.getEn());
        createNewsPage.selectTags(tagNames);

        // 11. Verify that selecting a fourth tag is blocked.
        // NOTE: in TagItem rootElement (button) doesn't work, so we click on a name inside the button, and it actually allows us to select more than 3 items.
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publishing a news with 4 tags should be blocked");

        // NOTE: we need to get out of the CreateNewsPage to let other tests work as expected, as they run in different order
        createNewsPage.clickCancel();
        Assert.assertTrue(createNewsPage.isCancelModalDisplayed(),
                "Confirmation modal should appear after clicking Cancel");

        CancelModalComponent cancelModal = createNewsPage.getCancelModal();
        cancelModal.clickYesCancel();
    }
}
