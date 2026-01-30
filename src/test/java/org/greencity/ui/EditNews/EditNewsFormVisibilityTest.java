package org.greencity.ui.EditNews;

import org.greencity.ui.components.CancelModalComponent;
import org.greencity.ui.components.ContentComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.enums.MySpaceTab;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.greencity.ui.pages.CreateEditNews.EditNewsPage;
import org.greencity.ui.pages.CreateEditNews.NewsPreviewPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.greencity.utils.NewsTestData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.greencity.utils.NewsTestData.*;

public class EditNewsFormVisibilityTest extends BaseTestRunner {

    private EditNewsPage editNewsPage;
    private CreateNewsPage createNewsPage;

    @BeforeClass
    public void LoginUser() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);
        editNewsPage = new EditNewsPage(driver, 830);
//        createNewsPage = homePage.open().getHeader().clickEcoNewsLink().clickCreateNews();
//        new NewsTestData()
//                .applyTo(createNewsPage);
//        createNewsPage.clickPublish();
//        editNewsPage = homePage.open().getHeader().clickMySpace().switchTo(MySpaceTab.NEWS).getFirstNews().click().clickEditButton();
//        editNewsPage = homePage.open().getHeader().clickMySpace().switchTo(MySpaceTab.NEWS).getNewsById(825L).click().clickEditButton();
    }

    @BeforeMethod
    public void beforeMethod() {
        editNewsPage = editNewsPage.open();
        editNewsPage.getHeader().changeToUK();
    }

    @Test(description = "Verify that the Edit News form contains the particular fields")
    public void verifyEditNewsFormFieldsVisibility() {

        // 1. Title
        Assert.assertTrue(editNewsPage.isPageOpened());
        String titleCounter = editNewsPage.getTitleCounterText();
        Assert.assertEquals(titleCounter, "4/170", "Title counter should be 4/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 4, "Title length should be 4");
        Assert.assertEquals(TEST_TITLE, editNewsPage.getTitleValue(), "Title should be the same as the test title");

        // 2. Tags
        Assert.assertTrue(editNewsPage.areTagsVisible(),
                "Tags should be visible");
        List<String> expectedUkTags = EcoNewsTag.getAllUa();
        Assert.assertEquals(
                editNewsPage.getAllTags(),
                expectedUkTags,
                "Tags should be in Ukrainian"
        );

        List<String> actualTagsUa = editNewsPage.getSelectedTags();
        List<TagItem> tagItemsUa = editNewsPage.getTagItems();
        boolean anySelectedUa = tagItemsUa.stream().anyMatch(TagItem::isSelected);
        Assert.assertTrue(anySelectedUa, "Test tag should be selected by default");
        Assert.assertEquals(EcoNewsTag.getUa(TEST_TAGS), actualTagsUa, "Selected tags do not match expected tags");

        editNewsPage.getHeader().changeToEN();

        List<String> expectedEnTags = EcoNewsTag.getAllEn();
        Assert.assertEquals(
                editNewsPage.getAllTags(),
                expectedEnTags,
                "Tags should be in English"
        );

        List<String> actualTagsEn = editNewsPage.getSelectedTags();
        List<TagItem> tagItemsEn = editNewsPage.getTagItems();
        boolean anySelectedEn = tagItemsEn.stream().anyMatch(TagItem::isSelected);
        Assert.assertTrue(anySelectedEn, "Test tag should be selected by default");
        Assert.assertEquals(EcoNewsTag.getEn(TEST_TAGS), actualTagsEn, "Selected tags do not match expected tags");

        // 3. Image Upload
        Assert.assertTrue(editNewsPage.isImageVisible(),
                "News image input should be displayed on Preview page");
        String src = editNewsPage.getEditImageSrc();
        Assert.assertNotNull(src, "Preview image src should not be null");
        Assert.assertFalse(src.isEmpty(), "Preview image src should not be empty");

        Assert.assertTrue(editNewsPage.isCancelCropperButtonVisible(),
                "Cancel button on cropper should be visible");
        Assert.assertTrue(editNewsPage.isSubmitCropperButtonVisible(),
                "Submit button on cropper should be visible");

//
//        // 4. Source
//        editNewsPage.getHeader().changeToUK();
//        Assert.assertTrue(editNewsPage.isSourceVisible(),
//                "Source should be visible");
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertEquals(
//                editNewsPage.getSourceMessage(),
//                "Джерело (не обов'язково)\n" +
//                        "Будь ласка, додайте посилання на оригінальну статтю/новину/публікацію. Посилання повинно починатись з http(s)://",
//                "Incorrect source validation message"
//        );
//        softAssert.assertEquals(
//                editNewsPage.getSourcePlaceholder(),
//                "Посилання на зовнішнє джерело",
//                "Incorrect source placeholder text"
//        );
//        softAssert.assertAll();
//        Assert.assertEquals(editNewsPage.getSource(), "");
//
//        editNewsPage.getHeader().changeToEN();
//        softAssert = new SoftAssert();
//        softAssert.assertEquals(
//                editNewsPage.getSourceMessage(),
//                "Source (optional)\n" +
//                        "Please add the link of original article/news/post. Link must start with http(s)://",
//                "Incorrect source validation message"
//        );
//        softAssert.assertEquals(
//                editNewsPage.getSourcePlaceholder(),
//                "Link to external source",
//                "Incorrect source placeholder text"
//        );
//        softAssert.assertAll();
//
//        // 5. Content
//        ContentComponent content = editNewsPage.getContentComponent();
//        Assert.assertTrue(content.isContentVisible(),
//                "Content should be visible");
//        Assert.assertTrue(content.isContentToolbarVisible(),
//                "Content toolbar should be visible");
//        Assert.assertTrue(content.isContentCounterVisible(),
//                "Content counter should be visible");
//        Assert.assertTrue(content.isContentMessageVisible(),
//                "Content message should be visible");
//
//        editNewsPage.getHeader().changeToUK();
//        Assert.assertEquals(content.getContent(), "",
//                "Content should be empty by default");
//        Assert.assertEquals(content.getContentCounter(), "",
//                "Content counter should be empty by default");
//        softAssert = new SoftAssert();
//        softAssert.assertEquals(
//                content.getContentMessage(),
//                "Поле повинно містити не менше 20 та не більше 63 206 символів",
//                "Incorrect content validation message"
//        );
//        softAssert.assertEquals(
//                content.getContentPlaceholder(),
//                "напр. Короткий опис новини, план заходу",
//                "Incorrect content placeholder text"
//        );
//        softAssert.assertAll();
//
//        editNewsPage.getHeader().changeToEN();
//        softAssert = new SoftAssert();
//        softAssert.assertEquals(
//                content.getContentMessage(),
//                "Must be minimum 20 and maximum 63 206 symbols",
//                "Incorrect content validation message"
//        );
//        softAssert.assertEquals(
//                content.getContentPlaceholder(),
//                "e.g. Short description of news, agenda for event",
//                "Incorrect content placeholder text"
//        );
//        softAssert.assertAll();
//
//        // 6. Author
//        Assert.assertTrue(editNewsPage.isAuthorVisible(),
//                "Author name should be visible");
//        Assert.assertEquals(editNewsPage.getAuthor(),
//                editNewsPage.getHeader().getUser(),
//                "Author should be pre-filled");
//
//        // 7. Date
//        Assert.assertTrue(editNewsPage.isPostDateVisible(),
//                "Post date should be visible");
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.US);;
//        String expectedDate = today.format(formatter);
//        Assert.assertEquals(editNewsPage.getPostDate(), expectedDate,
//                "Date should be today's date");
//
//        // 8. Publish, Preview, Cancel buttons
//        Assert.assertTrue(editNewsPage.isCancelButtonVisible(),
//                "Cancel button should be visible");
//        Assert.assertTrue(editNewsPage.isPreviewButtonVisible(),
//                "Preview button should be visible");
//        Assert.assertTrue(editNewsPage.isPublishButtonVisible(),
//                "Publish button should be visible");
//
//        editNewsPage.clickCancel();
//        Assert.assertTrue(editNewsPage.isCancelModalDisplayed(),
//                "Confirmation modal should appear after clicking Cancel");
//
//        CancelModalComponent cancelModal = editNewsPage.getCancelModal();
//        Assert.assertEquals(
//                cancelModal.getWarningTitleText(),
//                "All created content will be lost.",
//                "Warning title text is incorrect");
//        Assert.assertEquals(
//                cancelModal.getWarningSubtitleText(),
//                "Do you still want to cancel news creating?",
//                "Warning subtitle text is incorrect");
//        Assert.assertTrue(cancelModal.isCancelButtonVisible(),
//                "'Yes, cancel' button should be visible");
//        Assert.assertTrue(cancelModal.isContinueEditingButtonVisible(),
//                "'Continue editing' button should be visible");
//        cancelModal.clickYesCancel();
//
//        editNewsPage = new EditNewsPage(driver);
//        editNewsPage.open();
//        Assert.assertTrue(editNewsPage.isPageOpened(),
//                "User should be redirected to CreateNewsPage");
//        String currentUrl = driver.getCurrentUrl();
//        Assert.assertNotNull(currentUrl, "Current URL should not be null");
//        Assert.assertTrue(currentUrl.contains("/create-news"), "URL should contain /create-news after cancel");
//
//        new NewsTestData()
//                .applyTo(editNewsPage);
//        NewsPreviewPage preview = editNewsPage.clickPreview();
//        Assert.assertTrue(preview.isPageOpened(),
//                "User should be directed to NewsPreviewPage");
//        Assert.assertTrue(preview.getBackToCreateNewsBtnElement().isDisplayed(),
//                "Back to Create News button should be displayed");
//        Assert.assertTrue(preview.getPublicNewsBtnElement().isDisplayed(),
//                "Publish News button should be displayed");
//        Assert.assertEquals(preview.getNewsTitle(), TEST_TITLE,
//                "News title on Preview page should match entered title");
//        Assert.assertFalse(preview.getTagItems().isEmpty(),
//                "Tags list should not be empty on Preview page");
//        Assert.assertTrue(preview.getNewsCreatingDateElement().isDisplayed(),
//                "News creating date should be displayed");
//        Assert.assertFalse(preview.getAuthorName().isEmpty(),
//                "Author name should be displayed on Preview page");
//        Assert.assertEquals(preview.getNewsText(), TEST_CONTENT,
//                "News content on Preview page should match entered content");
//        Assert.assertEquals(preview.getNewsSource(), TEST_SOURCE,
//                "News source on Preview page should match entered source");
//        Assert.assertTrue(preview.isImageUploadInputVisible(),
//                "News image input should be displayed on Preview page");
//        editNewsPage = preview.clickBackToCreateNewsBtn();
//        Assert.assertTrue(editNewsPage.isPageOpenedAfterPreviewClickBack(),
//                "User should be redirected to CreateNewsPage after clicking Back button");
//
//        editNewsPage.reload();
//        Assert.assertTrue(editNewsPage.isPageOpened(),
//                "Create News page should be opened before creating news");
//        new NewsTestData()
//                .applyTo(editNewsPage);
//        Assert.assertTrue(editNewsPage.isPublishButtonEnabled(),
//                "Publish button should become enabled after all fields are valid.");
//        editNewsPage.clickPublish();
//        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
//        Assert.assertTrue(ecoNewsPage.isPageOpened(),
//                "User should be directed to EcoNewsPage");
//        String message = ecoNewsPage.getMessageText();
//        Assert.assertEquals(
//                message,
//                "Your news has been successfully published",
//                "Success message text should be correct"
//        );
//
    }
}
