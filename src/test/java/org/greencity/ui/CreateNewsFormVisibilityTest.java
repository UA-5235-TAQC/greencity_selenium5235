package org.greencity.ui;

import org.greencity.ui.components.CancelModalComponent;
import org.greencity.ui.components.ContentComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.NewsPreviewPage;
import org.greencity.ui.testrunners.TestRunnerWithUser;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateNewsFormVisibilityTest extends TestRunnerWithUser {

    private CreateNewsPage createNewsPage;

    @BeforeClass
    public void loginUser() {
        loginUser(new CreateNewsPage(driver).open());
    }

    @BeforeMethod
    public void openCreateNewsPage() {
        createNewsPage = new CreateNewsPage(driver).open();
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");
    }

    @Test(description = "Verify that the Create News form contains the particular fields")
    public void verifyCreateNewsFormFieldsVisibility() {

        // 1. Title
        Assert.assertTrue(createNewsPage.isPageOpened(),
                "Title should be visible");
        String titleCounter = createNewsPage.getTitleCounterText();
        Assert.assertEquals(titleCounter, "0/170", "Title counter should be 0/170");
        Assert.assertEquals(createNewsPage.getTitleLength(), 0, "Title length should be 0 by default");

        // 2. Tags
        Assert.assertTrue(createNewsPage.areTagsVisible(),
                "Tags should be visible");
        List<String> expectedUkTags = Arrays.stream(EcoNewsTag.values())
                .map(tag -> tag.getByLocale("Uk"))
                .collect(Collectors.toList());

        Assert.assertEquals(
                createNewsPage.getAllTags(),
                expectedUkTags,
                "Tags should be in Ukrainian"
        );

        String enLocale = "En";
        createNewsPage.getHeader().switchLanguage(enLocale);

        List<String> expectedEnTags = Arrays.stream(EcoNewsTag.values())
                .map(tag -> tag.getByLocale(enLocale))
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
        Assert.assertTrue(fileSize == 0, "Uploaded image size should be 0 bytes by default");

        // 4. Source
        String ukLocale = "Uk";
        createNewsPage.getHeader().switchLanguage(ukLocale);
        Assert.assertTrue(createNewsPage.isSourceVisible(),
                "Source should be visible");
        Assert.assertEquals(createNewsPage.getSourceMessage(),
                "Будь ласка, додайте посилання на оригінальну статтю/новину/публікацію. Посилання повинно починатись з http(s)://");
        Assert.assertEquals(createNewsPage.getSourcePlaceholder(), "Посилання на зовнішнє джерело");
        Assert.assertEquals(createNewsPage.getSource(), "");

        createNewsPage.getHeader().switchLanguage(enLocale);

        Assert.assertEquals(createNewsPage.getSourceMessage(),
                "Please add the link of original article/news/post. Link must start with http(s)://");
        Assert.assertEquals(createNewsPage.getSourcePlaceholder(), "Link to external source");

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

        createNewsPage.getHeader().switchLanguage(ukLocale);
        Assert.assertEquals(content.getContent(), "", "Content should be empty by default");
        Assert.assertEquals(content.getContentCounter(), "", "Content counter should be empty by default");
        Assert.assertEquals(content.getContentMessage(), "Поле повинно містити не менше 20 та не більше 63 206 символів");
        Assert.assertEquals(content.getContentPlaceholder(), "напр. Короткий опис новини, план заходу");

        createNewsPage.getHeader().switchLanguage(enLocale);
        Assert.assertEquals(content.getContentMessage(), "Must be minimum 20 and maximum 63 206 symbols");
        Assert.assertEquals(content.getContentPlaceholder(), "e.g. Short description of news, agenda for event");

        // 6. Author
        Assert.assertTrue(createNewsPage.isAuthorVisible(),
                "Author name should be visible");
        Assert.assertEquals(createNewsPage.getAuthor(), createNewsPage.getHeader().getUser(), "Author should be pre-filled");

        // 7. Date
        Assert.assertTrue(createNewsPage.isPostDateVisible(),
                "Post date should be visible");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String expectedDate = today.format(formatter);
        Assert.assertEquals(createNewsPage.getPostDate(), expectedDate, "Date should be today's date");

        // 8. Publish, Preview, Cancel buttons
        Assert.assertTrue(createNewsPage.isCancelButtonVisible(),
                "Cancel button should be visible");
        Assert.assertTrue(createNewsPage.isPreviewButtonVisible(),
                "Preview button should be visible");
        Assert.assertTrue(createNewsPage.isPublishButtonVisible(),
                "Publish button should be visible");

//        createNewsPage.clickCancel();
//        CancelModalComponent cancelModal = createNewsPage.getCancelModalComponent();
//        createNewsPage.waitUntilVisible(cancelModal);
//        cancelModal.clickClose();
//
//        NewsPreviewPage preview = createNewsPage.clickPreview();
    }
}
