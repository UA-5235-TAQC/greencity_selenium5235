package org.greencity.ui.EditNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateEditNews.EditNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.NewsDetailsPage;
import org.greencity.ui.testrunners.NewsDetails.NewsDetailsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.greencity.utils.ui.NewsTestData.*;

@Tag("Edit News")
@Epic("EcoNews Management")
@Feature("Edit existing news")
@Story("Verify author can edit their own news and changes are saved")
@Severity(SeverityLevel.CRITICAL)
@Issue("13")
public class EditOwnNewsTest extends NewsDetailsENTestRunner {

    @Description("Verify that the author can edit their own news and the changes are saved")
    @Test
    public void verifyAuthorCanEditOwnNews() {
        String originalTitle = newsDetailsPage.getTitleValue();

        Assert.assertTrue(newsDetailsPage.areTagsVisible(),
                "Tags should be visible");
        List<String> originalTags = newsDetailsPage.getTags();

        Assert.assertTrue(newsDetailsPage.isPostDateVisible(),
                "Post date should be visible");
        String originalCreatedDate = newsDetailsPage.getPostDate();

        Assert.assertTrue(newsDetailsPage.isAuthorVisible(),
                "Author name should be visible");
        String author = newsDetailsPage.getAuthor();
        Assert.assertEquals(author,
                newsDetailsPage.getHeader().getUser(),
                "Author should be pre-filled");

        Assert.assertTrue(newsDetailsPage.isContentVisible(),
                "Content should be visible");
        String originalContent = newsDetailsPage.getContentText();

        Assert.assertTrue(newsDetailsPage.isNewsImageVisible(),
                "News image should be visible");
        String src = newsDetailsPage.getNewsImageSrc();
        Assert.assertFalse(src.isEmpty(),
                "News Image source should not be empty");
        Assert.assertTrue(newsDetailsPage.isNewsImagePresent(),
                "News image should be present");

        long ecoNewsId = newsDetailsPage.getNewsIdFromUrl();
        EditNewsPage editNewsPage = newsDetailsPage.clickEditButton();
        Assert.assertTrue(editNewsPage.isPageOpened(), "Edit News page should be opened");

        String updatedTitle = originalTitle + " Updated";
        String updatedContent = "Updated content for verification of edit functionality.";
        List<String> updatedTags = EcoNewsTag.getEn(
                List.of(EcoNewsTag.EVENTS, EcoNewsTag.EDUCATION)
        );

        editNewsPage.editNews(
                updatedTitle,
                updatedTags,
                TEST_SOURCE,
                updatedContent,
                TEST2_FILEPATH
        );

        Assert.assertTrue(editNewsPage.isEditButtonEnabled(),
                "Submit button should be enabled after valid changes");
        editNewsPage.clickEdit();

        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.getHeader().changeToEN();
        Assert.assertTrue(ecoNewsPage.isPageOpened(),
                "User should be directed to EcoNewsPage");

        newsDetailsPage = new NewsDetailsPage(driver, ecoNewsId).open();
        newsDetailsPage.getHeader().changeToEN();
        Assert.assertTrue(newsDetailsPage.isPageOpened(), "News Details page should be opened");

        List<String> actualTags = newsDetailsPage.getTags();
        Assert.assertEquals(actualTags, updatedTags,
                "Actual tags should be the same as the updated tags"
        );
        Assert.assertNotEquals(actualTags, originalTags,
                "Actual tags should not be the same as the original tags before editing"
        );

        Assert.assertTrue(newsDetailsPage.checkNewsTitle(updatedTitle),
                "Title should be the same as the updated title");
        Assert.assertFalse(newsDetailsPage.checkNewsTitle(originalTitle),
                "Title should be not the same as the original title before editing");

        String createdDate = newsDetailsPage.getPostDate();
        Assert.assertEquals(createdDate, originalCreatedDate,
                "Created date should be the same as the original date");

        Assert.assertEquals(newsDetailsPage.getAuthor(),
                newsDetailsPage.getHeader().getUser(),
                "Author should be pre-filled");
        Assert.assertEquals(newsDetailsPage.getAuthor(), author,
                "Author should be the same as the original author");
        Assert.assertEquals(newsDetailsPage.getAuthor(),
                testValueProvider.getUserName(),
                "Author should be the test author");

        String actualContent = newsDetailsPage.getContentText();
        Assert.assertEquals(actualContent, updatedContent,
                "Actual content should be the same as the updated content"
        );
        Assert.assertNotEquals(actualContent, originalContent,
                "Actual content should not be the same as the original content before editing"
        );

        Assert.assertTrue(newsDetailsPage.isNewsImageVisible(),
                "News image should be visible");
        String actualSrc = newsDetailsPage.getNewsImageSrc();
        Assert.assertFalse(actualSrc.isEmpty(),
                "News Image source should not be empty");
        Assert.assertTrue(newsDetailsPage.isNewsImagePresent(),
                "News image should be present");
        Assert.assertNotEquals(actualSrc, src,
                "News image should not be the same as the original image before editing");

        editNewsPage = new EditNewsPage(driver, ecoNewsId).open();
        editNewsPage.getHeader().changeToEN();
        editNewsPage.editNews(TEST_TITLE_EN, EcoNewsTag.getEn(TEST_TAGS), TEST_SOURCE, TEST_CONTENT_EN, TEST_FILEPATH);
        editNewsPage.clickEdit();

        EcoNewsPage newsPage = new EcoNewsPage(driver);
        newsPage.getHeader().changeToEN();
        Assert.assertTrue(newsPage.isPageOpened(),
                "User should be directed to EcoNewsPage");
    }
}
