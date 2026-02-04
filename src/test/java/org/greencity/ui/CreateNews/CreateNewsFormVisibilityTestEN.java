package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.components.CreateEditNewsPage.ContentComponent;
import org.greencity.ui.components.CreateEditNewsPage.ImageComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
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
import java.util.List;
import java.util.Locale;

import static org.greencity.utils.NewsTestData.*;

public class CreateNewsFormVisibilityTestEN extends BaseTestRunner {

    private CreateNewsPage createNewsPage;

    @BeforeMethod
    public void beforeMethod() {
        createNewsPage = LoginUser()
                .getHeader()
                .changeToEN()
                .clickEcoNewsLink()
                .clickCreateNews();
    }

    @Tag("Create")
    @Feature("Create news page")
    @Issue("3")
    @Description("Verify that the Create News form contains the particular fields in English locale")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyCreateNewsFormFieldsVisibilityInEnglishLocale() {

        // 1. Title
        Assert.assertTrue(createNewsPage.isPageOpened());
        String titleCounter = createNewsPage.getTitleCounterText();
        Assert.assertEquals(titleCounter, "0/170", "Title counter should be 0/170");
        Assert.assertEquals(createNewsPage.getTitleLength(), 0, "Title length should be 0 by default");
        Assert.assertEquals(createNewsPage.getTitleValue(), "", "Title should be empty by default");

        // 2. Tags
        Assert.assertTrue(createNewsPage.areTagsVisible(),
                "Tags should be visible");
        List<String> expectedEnTags = EcoNewsTag.getAllEn();
        Assert.assertEquals(
                createNewsPage.getAllTags(),
                expectedEnTags,
                "Tags should be in English"
        );

        List<String> actualTags = createNewsPage.getSelectedTags();
        List<TagItem> tagItems = createNewsPage.getTagItems();
        boolean anySelected = tagItems.stream().anyMatch(TagItem::isSelected);
        Assert.assertFalse(anySelected, "No tag should be selected by default");
        Assert.assertTrue(actualTags.isEmpty(),
                "Selected tags should be empty by default");

        // 3. Image Upload
        ImageComponent imageComponent = createNewsPage.getImageComponent();
        Assert.assertNotNull(imageComponent.getImageInputInfo(),
                "Image upload field should be present");
        Assert.assertTrue(
                imageComponent.isImageFieldVisible(),
                "Image field should be visible"
        );
        Assert.assertFalse(imageComponent.isUploadedImagePresent(),
                "Loaded image should not be present by default");
        Assert.assertTrue(
                imageComponent.isPlaceholderImagePresent(),
                "Image zone should be visible"
        );
        Assert.assertTrue(createNewsPage.getImageComponent().isCancelCropperButtonVisible(),
                "Cancel button on cropper should be visible");
        Assert.assertTrue(createNewsPage.getImageComponent().isSubmitCropperButtonVisible(),
                "Submit button on cropper should be visible");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                imageComponent.getDropZoneText(),
                "Drop your image here or",
                "Drop zone text should match"
        );
        softAssert.assertEquals(
                imageComponent.getBrowseText(),
                "browse",
                "Browse link text should match"
        );
        Assert.assertEquals(
                imageComponent.getImageError(),
                "Upload only PNG or JPG. File size must be less than 10MB",
                "Error message should match"
        );
        softAssert.assertEquals(
                imageComponent.getCancelCropperText(),
                "Cancel",
                "Cancel cropper button text should match"
        );
        softAssert.assertEquals(
                imageComponent.getSubmitCropperText(),
                "Submit",
                "Submit cropper button text should match"
        );
        softAssert.assertAll();

        // 4. Source
        Assert.assertTrue(createNewsPage.isSourceVisible(),
                "Source should be visible");
        Assert.assertEquals(createNewsPage.getSource(), "");
        softAssert = new SoftAssert();
        softAssert.assertEquals(
                createNewsPage.getSourceMessage(),
                "Source (optional)\n" +
                        "Please add the link of original article/news/post. Link must start with http(s)://",
                "Incorrect source validation message"
        );
        softAssert.assertEquals(
                createNewsPage.getSourcePlaceholder(),
                "Link to external source",
                "Incorrect source placeholder text"
        );
        softAssert.assertAll();

        // 5. Content
        ContentComponent content = createNewsPage.getContentComponent();
        Assert.assertTrue(content.isContentVisible(),
                "Content should be visible");
        Assert.assertTrue(content.isContentToolbarVisible(),
                "Content toolbar should be visible");
        Assert.assertTrue(content.isContentCounterVisible(),
                "Content counter should be visible");
        Assert.assertTrue(content.isContentMessageVisible(),
                "Content message should be visible");

        Assert.assertEquals(content.getContentText(), "",
                "Content should be empty by default");
        Assert.assertEquals(content.getContentCounter(), "",
                "Content counter should be empty by default");

        softAssert = new SoftAssert();
        softAssert.assertEquals(
                content.getContentMessage(),
                "Must be minimum 20 and maximum 63 206 symbols",
                "Incorrect content validation message"
        );
        softAssert.assertEquals(
                content.getContentPlaceholder(),
                "e.g. Short description of news, agenda for event",
                "Incorrect content placeholder text"
        );
        softAssert.assertAll();

        // 6. Author
        Assert.assertTrue(createNewsPage.isAuthorVisible(),
                "Author name should be visible");
        Assert.assertEquals(createNewsPage.getAuthor(),
                createNewsPage.getHeader().getUser(),
                "Author should be pre-filled");
        Assert.assertEquals(createNewsPage.getAuthor(),
                testValueProvider.getUserName(),
                "Author should be the test author");

        // 7. Date
        Assert.assertTrue(createNewsPage.isPostDateVisible(),
                "Post date should be visible");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatterEn = DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.US);;
        String expectedDateEn = today.format(formatterEn);
        Assert.assertEquals(createNewsPage.getPostDate(), expectedDateEn,
                "Date should be today's date");

        // 8. Publish, Preview, Cancel buttons
        Assert.assertTrue(createNewsPage.isCancelButtonVisible(),
                "Cancel button should be visible");
        Assert.assertTrue(createNewsPage.isPreviewButtonVisible(),
                "Preview button should be visible");
        Assert.assertTrue(createNewsPage.isPublishButtonVisible(),
                "Publish button should be visible");

        softAssert = new SoftAssert();
        softAssert.assertEquals(
                createNewsPage.getCancelButtonText(),
                "Cancel",
                "Cancel button text is incorrect"
        );
        softAssert.assertEquals(
                createNewsPage.getPreviewButtonText(),
                "Preview",
                "Preview button text is incorrect"
        );
        softAssert.assertEquals(
                createNewsPage.getPublishButtonText(),
                "Publish",
                "Publish button text is incorrect"
        );
        softAssert.assertAll();

        createNewsPage.clickCancel();
        Assert.assertTrue(createNewsPage.isCancelModalDisplayed(),
                "Confirmation modal should appear after clicking Cancel");

        CancelModalComponent cancelModal = createNewsPage.getCancelModal();
        Assert.assertTrue(cancelModal.isCancelButtonVisible(),
                "'Yes, cancel' button should be visible");
        Assert.assertTrue(cancelModal.isContinueEditingButtonVisible(),
                "'Continue editing' button should be visible");
        softAssert = new SoftAssert();
        softAssert.assertEquals(
                cancelModal.getWarningTitleText(),
                "All created content will be lost.",
                "Warning title text is incorrect");
        softAssert.assertEquals(
                cancelModal.getWarningSubtitleText(),
                "Do you still want to cancel news creating?",
                "Warning subtitle text is incorrect");
        softAssert.assertEquals(cancelModal.getYesCancelButtonText(), "Yes, cancel");
        softAssert.assertEquals(cancelModal.getContinueEditingButtonText(), "Continue editing");
        softAssert.assertAll();
        cancelModal.clickYesCancel();
        cancelModal.waitUntilClosed();

        createNewsPage = new CreateNewsPage(driver).open();
        createNewsPage.getHeader().changeToEN();
        Assert.assertTrue(createNewsPage.isPageOpened(),
                "User should be redirected to CreateNewsPage");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertTrue(currentUrl.contains("/create-news"), "URL should contain /create-news after cancel");

        new NewsTestData().applyToEn(createNewsPage);
        NewsPreviewPage preview = createNewsPage.clickPreview();
        Assert.assertTrue(preview.isPageOpened(),
                "User should be directed to NewsPreviewPage");
        Assert.assertTrue(preview.getBackToCreateNewsBtnElement().isDisplayed(),
                "Back to Create News button should be displayed");
        Assert.assertTrue(preview.getPublicNewsBtnElement().isDisplayed(),
                "Publish News button should be displayed");
        Assert.assertEquals(preview.getNewsTitle(), TEST_TITLE_EN,
                "News title on Preview page should match entered title");
        Assert.assertFalse(preview.getTagItems().isEmpty(),
                "Tags list should not be empty on Preview page");
        String expectedTag = EcoNewsTag.getEn(TEST_TAGS).getFirst();
        List<String> previewTags = preview.getTagTexts();
        Assert.assertTrue(previewTags.contains(expectedTag),
                "Preview page should contain tag: " + expectedTag);
        Assert.assertTrue(preview.getNewsCreatingDateElement().isDisplayed(),
                "News creating date should be displayed");
        Assert.assertFalse(preview.getAuthorName().isEmpty(),
                "Author name should be displayed on Preview page");
        Assert.assertEquals(preview.getNewsText(), TEST_CONTENT_EN,
                "News content on Preview page should match entered content");
        Assert.assertEquals(preview.getNewsSource(), TEST_SOURCE,
                "News source on Preview page should match entered source");
        Assert.assertTrue(preview.isImageUploadInputVisible(),
                "News image input should be displayed on Preview page");
        String src = preview.getPreviewImageSrc();
        Assert.assertNotNull(src, "Preview image src should not be null");
        Assert.assertFalse(src.isEmpty(), "Preview image src should not be empty");

        createNewsPage = preview.clickBackToCreateNewsBtn();
        Assert.assertTrue(createNewsPage.isPageOpenedAfterPreviewClickBack(),
                "User should be redirected to CreateNewsPage after clicking Back button");

        createNewsPage.reload();
        createNewsPage.getHeader().changeToEN();
        Assert.assertTrue(createNewsPage.isPageOpened(),
                "Create News page should be opened before creating news");
        new NewsTestData()
                .applyToEn(createNewsPage);
        Assert.assertTrue(createNewsPage.isPublishButtonEnabled(),
                "Publish button should become enabled after all fields are valid.");
        createNewsPage.clickPublish();
        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.getHeader().changeToEN();
        Assert.assertTrue(ecoNewsPage.isPageOpened(),
                "User should be directed to EcoNewsPage");
        String message = ecoNewsPage.getMessageText();
        Assert.assertEquals(
                message,
                "Your news has been successfully published",
                "Success message text should be correct"
        );
    }
}
