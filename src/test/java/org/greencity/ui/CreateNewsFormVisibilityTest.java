package org.greencity.ui;

import org.greencity.ui.components.CancelModalComponent;
import org.greencity.ui.components.ContentComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.NewsPreviewPage;
import org.greencity.ui.testrunners.BaseTestRunner;
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

public class CreateNewsFormVisibilityTest extends BaseTestRunner {

    private CreateNewsPage createNewsPage;
    private static final String TEST_TITLE = "Test";
    private static final String TEST_CONTENT = "Test content with 20 chars";
    private static final String TEST_SOURCE = "https://chatgpt.com/";

    @BeforeClass
    public void LoginUser() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);
        createNewsPage = homePage.open().getHeader().clickEcoNewsLink().clickCreateNews();;
    }

    @BeforeMethod
    public void beforeMethod() {
//        createNewsPage = createNewsPage.open();
        createNewsPage.getHeader().changeToUK();
    }

    @Test(description = "Verify that the Create News form contains the particular fields")
    public void verifyCreateNewsFormFieldsVisibility() {

        // 1. Title
        Assert.assertTrue(createNewsPage.isPageOpened());
        String titleCounter = createNewsPage.getTitleCounterText();
        Assert.assertEquals(titleCounter, "0/170", "Title counter should be 0/170");
        Assert.assertEquals(createNewsPage.getTitleLength(), 0, "Title length should be 0 by default");

        // 2. Tags
        Assert.assertTrue(createNewsPage.areTagsVisible(),
                "Tags should be visible");
        List<String> expectedUkTags = Arrays.stream(EcoNewsTag.values())
                .map(EcoNewsTag::getUa)
                .collect(Collectors.toList());

        Assert.assertEquals(
                createNewsPage.getAllTags(),
                expectedUkTags,
                "Tags should be in Ukrainian"
        );

        createNewsPage.getHeader().changeToEN();

        List<String> expectedEnTags = Arrays.stream(EcoNewsTag.values())
                .map(EcoNewsTag::getEn)
                .collect(Collectors.toList());

        Assert.assertEquals(
                createNewsPage.getAllTags(),
                expectedEnTags,
                "Tags should be in English"
        );
        List<TagItem> tagItems = createNewsPage.getTagItems();
        boolean anySelected = tagItems.stream().anyMatch(TagItem::isSelected);
        Assert.assertFalse(anySelected, "No tag should be selected by default");

        // 3. Image Upload
        Assert.assertNotNull(createNewsPage.getUploadedImageInfo(),
                "Image upload field should be present");
        Assert.assertTrue(
                createNewsPage.isImageUploadInputVisible(),
                "Image zone should be visible"
        );
        Assert.assertTrue(createNewsPage.isCancelCropperButtonVisible(),
                "Cancel button on cropper should be visible");

        Assert.assertTrue(createNewsPage.isSubmitCropperButtonVisible(),
                "Submit button on cropper should be visible");

        long fileSize = createNewsPage.getUploadedImageSize();
        Assert.assertEquals(fileSize, 0,
                "Uploaded image size should be 0 bytes by default");

        // 4. Source
        createNewsPage.getHeader().changeToUK();
        Assert.assertTrue(createNewsPage.isSourceVisible(),
                "Source should be visible");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                createNewsPage.getSourceMessage(),
                "Джерело (не обов'язково)\n" +
                        "Будь ласка, додайте посилання на оригінальну статтю/новину/публікацію. Посилання повинно починатись з http(s)://",
                "Incorrect source validation message"
        );
        softAssert.assertEquals(
                createNewsPage.getSourcePlaceholder(),
                "Посилання на зовнішнє джерело",
                "Incorrect source placeholder text"
        );
        softAssert.assertAll();
        Assert.assertEquals(createNewsPage.getSource(), "");

        createNewsPage.getHeader().changeToEN();
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

        createNewsPage.getHeader().changeToUK();
        Assert.assertEquals(content.getContent(), "",
                "Content should be empty by default");
        Assert.assertEquals(content.getContentCounter(), "",
                "Content counter should be empty by default");
        softAssert = new SoftAssert();
        softAssert.assertEquals(
                content.getContentMessage(),
                "Поле повинно містити не менше 20 та не більше 63 206 символів",
                "Incorrect content validation message"
        );
        softAssert.assertEquals(
                content.getContentPlaceholder(),
                "напр. Короткий опис новини, план заходу",
                "Incorrect content placeholder text"
        );
        softAssert.assertAll();

        createNewsPage.getHeader().changeToEN();
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

        // 7. Date
        Assert.assertTrue(createNewsPage.isPostDateVisible(),
                "Post date should be visible");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.US);;
        String expectedDate = today.format(formatter);
        Assert.assertEquals(createNewsPage.getPostDate(), expectedDate,
                "Date should be today's date");

        // 8. Publish, Preview, Cancel buttons
        Assert.assertTrue(createNewsPage.isCancelButtonVisible(),
                "Cancel button should be visible");
        Assert.assertTrue(createNewsPage.isPreviewButtonVisible(),
                "Preview button should be visible");
        Assert.assertTrue(createNewsPage.isPublishButtonVisible(),
                "Publish button should be visible");

        createNewsPage.clickCancel();
        Assert.assertTrue(createNewsPage.isCancelModalDisplayed(),
                "Confirmation modal should appear after clicking Cancel");

        CancelModalComponent cancelModal = createNewsPage.getCancelModal();
        Assert.assertEquals(
                cancelModal.getWarningTitleText(),
                "All created content will be lost.",
                "Warning title text is incorrect");
        Assert.assertEquals(
                cancelModal.getWarningSubtitleText(),
                "Do you still want to cancel news creating?",
                "Warning subtitle text is incorrect");
        Assert.assertTrue(cancelModal.isCancelButtonVisible(),
                "'Yes, cancel' button should be visible");
        Assert.assertTrue(cancelModal.isContinueEditingButtonVisible(),
                "'Continue editing' button should be visible");
        cancelModal.clickYesCancel();

        createNewsPage = new CreateNewsPage(driver);
        createNewsPage.open();
        Assert.assertTrue(createNewsPage.isPageOpened(),
                "User should be redirected to CreateNewsPage");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertTrue(currentUrl.contains("/create-news"), "URL should contain /create-news after cancel");

        createDefaultNews(createNewsPage);
        NewsPreviewPage preview = createNewsPage.clickPreview();
        Assert.assertTrue(preview.isPageOpened(),
                "User should be directed to NewsPreviewPage");
        Assert.assertTrue(preview.getBackToCreateNewsBtnElement().isDisplayed(),
                "Back to Create News button should be displayed");
        Assert.assertTrue(preview.getPublicNewsBtnElement().isDisplayed(),
                "Publish News button should be displayed");
        Assert.assertEquals(preview.getNewsTitle(), TEST_TITLE,
                "News title on Preview page should match entered title");
        Assert.assertFalse(preview.getTagItems().isEmpty(),
                "Tags list should not be empty on Preview page");
        Assert.assertTrue(preview.getNewsCreatingDateElement().isDisplayed(),
                "News creating date should be displayed");
        Assert.assertFalse(preview.getAuthorName().isEmpty(),
                "Author name should be displayed on Preview page");
        Assert.assertEquals(preview.getNewsText(), TEST_CONTENT,
                "News content on Preview page should match entered content");
        Assert.assertEquals(preview.getNewsSource(), TEST_SOURCE,
                "News source on Preview page should match entered source");
        Assert.assertTrue(preview.isImageUploadInputVisible(),
                "News image input should be displayed on Preview page");
        createNewsPage = preview.clickBackToCreateNewsBtn();
        Assert.assertTrue(createNewsPage.isPageOpenedAfterPreviewClickBack(),
                "User should be redirected to CreateNewsPage after clicking Back button");

        createNewsPage.reload();
        Assert.assertTrue(createNewsPage.isPageOpened(),
                "Create News page should be opened before creating news");
        createDefaultNews(createNewsPage);
        Assert.assertTrue(createNewsPage.isPublishButtonEnabled(),
                "Publish button should become enabled after all fields are valid.");
        createNewsPage.clickPublish();
        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        Assert.assertTrue(ecoNewsPage.isPageOpened(),
                "User should be directed to EcoNewsPage");
        String message = ecoNewsPage.getMessageText();
        Assert.assertEquals(
                message,
                "Your news has been successfully published",
                "Success message text should be correct"
        );

    }

    private void createDefaultNews(CreateNewsPage page) {
        page.createNews(
                TEST_TITLE,
                List.of(EcoNewsTag.NEWS.getEn()),
                TEST_SOURCE,
                TEST_CONTENT,
                null
        );
    }
}
