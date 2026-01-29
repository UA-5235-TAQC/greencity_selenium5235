package org.greencity.ui;

import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class TagSelectionTest extends BaseTestRunner {

    @Test
    public void checkTagSelection() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);

        // 1. Navigate to GreenCity News and click "Create News".
        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver).open();
        ecoNewsPage.getHeader().changeToEN();
        Assert.assertTrue(ecoNewsPage.isPageOpened(), "Eco News page should be opened");

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

        // 6. Open the "Create News" form again.
        createNewsPage = ecoNewsPage.clickCreateNews();
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");

        // 7. Select three tags: "News", "Events", "Education".
        // 8. Click "Publish".
        tagNames = List.of(EcoNewsTag.NEWS.getEn(), EcoNewsTag.EVENTS.getEn(), EcoNewsTag.EDUCATION.getEn());
        createNewsPage.createNews("Test_2", tagNames, null, "Test content with 20 chars", null)
                .clickPublish();

        // 9. Verify that the news is published with all three selected tags.
        Assert.assertTrue(ecoNewsPage.isPageOpened(), "Eco News page should be opened after publishing a news");

        newsListItem = ecoNewsPage.getNewsCardByIndex(0);
        softAssert = new SoftAssert();
        softAssert.assertEquals(newsListItem.getTitle(), "Test_2", "News should have 'Test_2' title");
        softAssert.assertTrue(newsListItem.hasTags(tagNames), "News should have 'News', 'Events' and 'Education' tags");
        softAssert.assertAll();

        // 10. Attempt to select a fourth tag ("Initiatives").
        // 11. Verify that selecting a fourth tag is blocked.
        ecoNewsPage.clickCreateNews();
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");

        tagNames = List.of(EcoNewsTag.NEWS.getEn(), EcoNewsTag.EVENTS.getEn(), EcoNewsTag.EDUCATION.getEn(), EcoNewsTag.INITIATIVES.getEn());
        createNewsPage.selectTags(tagNames);
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publishing a news with 4 tags should be blocked");
    }
}
