package org.greencity.ui.EditNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.components.CreateEditNewsPage.ContentComponent;
import org.greencity.ui.components.CreateEditNewsPage.ImageComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateEditNews.EditNewsPage;
import org.greencity.ui.pages.CreateEditNews.NewsPreviewPage;
import org.greencity.ui.pages.UbsCourierPage;
import org.greencity.ui.testrunners.EditNews.EditNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.greencity.utils.NewsTestData.*;

@Tag("Edit News")
@Epic("EcoNews Management")
@Feature("Edit news page")
@Story("Verify visibility and behavior of Edit News form in English locale")
@Severity(SeverityLevel.NORMAL)
@Issue("14")
public class EditNewsFormVisibilityTestEN extends EditNewsENTestRunner {

    @Description("Verify that the Edit News form contains the particular fields in English locale")
    @Test
    public void verifyEditNewsFormFieldsVisibilityInEnglishLocale() {

        // 1. Title
        Assert.assertTrue(editNewsPage.isPageOpened());
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "4/170", "Title counter should be 4/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 4, "Title length should be 4");
        Assert.assertEquals(editNewsPage.getTitleValue(), TEST_TITLE_EN, "Title should be the same as the test title");

        String toAppend = " New";
        editNewsPage.appendTitle(toAppend);
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "8/170", "Title counter should be 8/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 8, "Title length should be 8");
        Assert.assertEquals(editNewsPage.getTitleValue(), TEST_TITLE_EN + toAppend, "Title should be appended");

        String toPrepend = "Add ";
        editNewsPage.prependTitle(toPrepend);
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "12/170", "Title counter should be 12/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 12, "Title length should be 12");
        Assert.assertEquals(editNewsPage.getTitleValue(), toPrepend + TEST_TITLE_EN + toAppend, "Title should be prepended");

        editNewsPage.removeLastTitleChars(4);
        editNewsPage.removeFirstTitleChars(4);
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "4/170", "Title counter should be 4/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 4, "Title length should be 4");
        Assert.assertEquals(editNewsPage.getTitleValue(), TEST_TITLE_EN, "Title should be the same as the test title");

        String testTitle = toPrepend + TEST_TITLE_EN;
        editNewsPage.enterTitle(testTitle);
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "8/170", "Title counter should be 8/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 8, "Title length should be 8");
        Assert.assertEquals(editNewsPage.getTitleValue(), testTitle, "Title should be new");

        // 2. Tags
        Assert.assertTrue(editNewsPage.areTagsVisible(),
                "Tags should be visible");
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

        editNewsPage.selectTags(EcoNewsTag.getEn(List.of(EcoNewsTag.EVENTS, EcoNewsTag.EDUCATION)));
        actualTagsEn = editNewsPage.getSelectedTags();
        List<EcoNewsTag> expectedTags = new ArrayList<>(TEST_TAGS);
        expectedTags.add(EcoNewsTag.EVENTS);
        expectedTags.add(EcoNewsTag.EDUCATION);
        List<String> expectedTagsEn = EcoNewsTag.getEn(expectedTags);
        Assert.assertEquals(actualTagsEn.size(), 3, "Selected tags do not match expected tags");
        Assert.assertEquals(expectedTagsEn, actualTagsEn, "Selected tags do not match expected tags");

        List <EcoNewsTag> tagsToSelect = List.of(EcoNewsTag.INITIATIVES, EcoNewsTag.ADS);
        List <String> tagsToSelectEn = EcoNewsTag.getEn(tagsToSelect);
        editNewsPage.clearAllSelectedTags().selectTags(tagsToSelectEn);
        actualTagsEn = editNewsPage.getSelectedTags();
        tagItemsEn = editNewsPage.getTagItems();
        anySelectedEn = tagItemsEn.stream().anyMatch(TagItem::isSelected);
        Assert.assertTrue(anySelectedEn, "2 tags should be selected");
        Assert.assertEquals(actualTagsEn.size(), 2, "Selected tags do not match expected tags");
        Assert.assertEquals(tagsToSelectEn, actualTagsEn, "Selected tags do not match expected tags");

        // 3. Image Upload
        ImageComponent imageComponent = editNewsPage.getImageComponent();
        Assert.assertTrue(imageComponent.isImageVisible(),
                "Image should be visible");
        Assert.assertFalse(imageComponent.getUploadedImageSrc().isEmpty(),
                "Image upload field should not be empty");
        Assert.assertTrue(imageComponent.isUploadedImagePresent(),
                "Loaded image should be present");
        Assert.assertTrue(imageComponent.isCancelCropperButtonVisible(),
                "Cancel button on cropper should be visible");
        Assert.assertTrue(imageComponent.isSubmitCropperButtonVisible(),
                "Submit button on cropper should be visible");
        SoftAssert softAssert = new SoftAssert();
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
        imageComponent = editNewsPage.getImageComponent().changeImage(TEST2_FILEPATH);
        Assert.assertTrue(imageComponent.isPreviewImageVisible(),
                "Image should be visible");
        Assert.assertFalse(imageComponent.getPreviewImageSrc().isEmpty(),
                "Image upload field should not be empty");
        Assert.assertTrue(imageComponent.isPreviewPlaceholderImagePresent(),
                "Loaded image should be present");
        Assert.assertTrue(imageComponent.isCancelCropperButtonVisible(),
                "Cancel button on cropper should be visible");
        Assert.assertTrue(imageComponent.isSubmitCropperButtonVisible(),
                "Submit button on cropper should be visible");

        // 4. Source
        Assert.assertTrue(editNewsPage.isSourceVisible(),
                "Source should be visible");
        String source = editNewsPage.getSource();
        Assert.assertEquals(
                source,
                "",
                "Expected the Source field to be empty due to an application issue"
        );
        Assert.assertNotEquals(
                source,
                TEST_SOURCE,
                "Source field should not retain the previously entered test source due to an application issue"
        );

        softAssert = new SoftAssert();
        softAssert.assertEquals(
                editNewsPage.getSourceMessage(),
                "Source (optional)\n" +
                        "Please add the link of original article/news/post. Link must start with http(s)://",
                "Incorrect source validation message"
        );
        softAssert.assertEquals(
                editNewsPage.getSourcePlaceholder(),
                "Link to external source",
                "Incorrect source placeholder text"
        );
        softAssert.assertAll();

        // 5. Content
        ContentComponent content = editNewsPage.getContentComponent();
        Assert.assertTrue(content.isContentVisible(),
                "Content should be visible");
        Assert.assertTrue(content.isContentToolbarVisible(),
                "Content toolbar should be visible");
        Assert.assertTrue(content.isContentCounterVisible(),
                "Content counter should be visible");
        Assert.assertTrue(content.isContentMessageVisible(),
                "Content message should be visible");
        Assert.assertEquals(content.getContentText(), TEST_CONTENT_EN,
                "Content should be the same as test content");
        Assert.assertEquals(content.getContentCounter(),
                "",
                "Content counter should be empty in the beginning");

        content.enterContentNotClear(toAppend);
        Assert.assertTrue(content.isContentValid(), "Content is invalid");
        Assert.assertEquals(content.getContentCounter(),
                "Number of characters: 30",
                "Content counter is incorrect");
        Assert.assertEquals(content.getContentLengthNumber(), 30, "Content length should be 30");
        Assert.assertEquals(content.getContentText(), TEST_CONTENT_EN + toAppend, "Content should be appended");

        content.prependContent(toPrepend);
        Assert.assertTrue(content.isContentValid(), "Content is invalid");
        Assert.assertEquals(content.getContentCounter(),
                "Number of characters: 34",
                "Content counter is incorrect");
        Assert.assertEquals(content.getContentLengthNumber(), 34, "Content length should be 34");
        Assert.assertEquals(content.getContentText(), toPrepend + TEST_CONTENT_EN + toAppend, "Content should be prepended");

        content.removeLastContentChars(4);
        content.removeFirstContentChars(4);
        Assert.assertEquals(content.getContentCounter(),
                "Number of characters: 26",
                "Content counter is incorrect");
        Assert.assertEquals(content.getContentLengthNumber(), 26, "Content length should be 26");
        Assert.assertEquals(content.getContentText(), TEST_CONTENT_EN, "Content should be the same as the test content");

        content.clearContent();
        Assert.assertTrue(content.isContentVisible(),
                "Content should be visible");
        Assert.assertTrue(content.isContentToolbarVisible(),
                "Content toolbar should be visible");
        Assert.assertTrue(content.isContentCounterVisible(),
                "Content counter should be visible");
        Assert.assertTrue(content.isContentMessageVisible(),
                "Content message should be visible");
        Assert.assertFalse(content.isContentValid(), "Content is valid");
        Assert.assertTrue(content.isContentInvalid(), "Content is valid");
        Assert.assertEquals(content.getContentText(), "",
                "Content should be empty");
        Assert.assertEquals(content.getContentCounter(), "",
                "Content counter should be empty");
        Assert.assertTrue(content.isContentMessageVisible(), "Content message is invisible");
        Assert.assertTrue(content.isContentMessageInvalid(), "Content message is valid");
        Assert.assertTrue(content.isContentWarningDisplayed(), "Content warning is not displayed");

        softAssert = new SoftAssert();
        Assert.assertEquals(
                content.getContentPlaceholder(),
                "e.g. Short description of news, agenda for event",
                "Incorrect content placeholder text"
        );
        softAssert.assertEquals(
                content.getContentMessage(),
                "Must be minimum 20 and maximum 63 206 symbols",
                "Incorrect content validation message"
        );
        softAssert.assertAll();

        String testString = TEST_TITLE_EN + " " + TEST_CONTENT_EN;
        content.enterContent(testString);
        Assert.assertEquals(content.getContentCounter(),
                "Number of characters: 31",
                "Content counter is incorrect");
        Assert.assertEquals(content.getContentLengthNumber(), 31, "Content length should be 31");
        Assert.assertEquals(content.getContentText(), testString, "Content should be the same as the test string");

        // 6. Author
        Assert.assertTrue(editNewsPage.isAuthorVisible(),
                "Author name should be visible");
        Assert.assertEquals(editNewsPage.getAuthor(),
                editNewsPage.getHeader().getUser(),
                "Author should be pre-filled");
        Assert.assertEquals(editNewsPage.getAuthor(),
                testValueProvider.getUserName(),
                "Author should be the test author");

        // 7. Date
        Assert.assertTrue(editNewsPage.isPostDateVisible(),
                "Post date should be visible");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.US);;
        String expectedDate = today.format(formatter);
        Assert.assertEquals(editNewsPage.getPostDate(), expectedDate,
                "Date should be today's date");

        // 8. Publish, Preview, Cancel buttons
        Assert.assertTrue(editNewsPage.isCancelButtonVisible(),
                "Cancel button should be visible");
        Assert.assertTrue(editNewsPage.isPreviewButtonVisible(),
                "Preview button should be visible");
        Assert.assertTrue(editNewsPage.isEditButtonVisible(),
                "Publish button should be visible");

        softAssert = new SoftAssert();
        softAssert.assertEquals(
                editNewsPage.getCancelButtonText(),
                "Cancel",
                "Cancel button text is incorrect"
        );
        softAssert.assertEquals(
                editNewsPage.getPreviewButtonText(),
                "Preview",
                "Preview button text is incorrect"
        );
        softAssert.assertEquals(
                editNewsPage.getEditButtonText(),
                "Edit",
                "Edit button text is incorrect"
        );
        softAssert.assertAll();

        editNewsPage.clickCancel();
        Assert.assertTrue(editNewsPage.isCancelModalDisplayed(),
                "Confirmation modal should appear after clicking Cancel");

        CancelModalComponent cancelModal = editNewsPage.getCancelModal();
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

        editNewsPage = new EditNewsPage(driver, getNewsId()).open();
        editNewsPage.getHeader().changeToEN();
        Assert.assertTrue(editNewsPage.isPageOpened(),
                "User should be redirected to CreateNewsPage");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertTrue(currentUrl.contains("/create-news"), "URL should contain /create-news after cancel");

        testTitle = "Max";
        List<String> testTags = EcoNewsTag.getEn(List.of(EcoNewsTag.INITIATIVES, EcoNewsTag.EVENTS));
        String testSource = "https://en.wikipedia.org/wiki/Main_Page";
        String testContent = "Mount Edziza is a volcanic mountain in Cassiar Land District in northwestern British Columbia, Canada.";
        editNewsPage.editNews(testTitle, testTags, testSource, testContent, TEST2_FILEPATH);

        NewsPreviewPage preview = editNewsPage.clickPreview();
        Assert.assertTrue(preview.isPageOpened(),
                "User should be directed to NewsPreviewPage");
        Assert.assertTrue(preview.getBackToCreateNewsBtnElement().isDisplayed(),
                "Back to Create News button should be displayed");
        Assert.assertTrue(preview.getPublicNewsBtnElement().isDisplayed(),
                "Publish News button should be displayed");
        Assert.assertEquals(preview.getNewsTitle(), testTitle,
                "News title on Preview page should match entered title");

        Assert.assertFalse(preview.getTagItems().isEmpty(),
                "Tags list should not be empty on Preview page");
        List<String> previewTags = preview.getTagTexts();
        List<String> actual = new ArrayList<>(previewTags);
        List<String> expected = new ArrayList<>(testTags);
        Collections.sort(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected,
                "Tags on Preview page should match entered tags");

        Assert.assertTrue(preview.getNewsCreatingDateElement().isDisplayed(),
                "News creating date should be displayed");
        Assert.assertFalse(preview.getAuthorName().isEmpty(),
                "Author name should be displayed on Preview page");
        Assert.assertEquals(preview.getNewsSource(), testSource,
                "News source on Preview page should match entered source");
        Assert.assertEquals(preview.getNewsText(), testContent,
                "News content on Preview page should match entered content");
        Assert.assertTrue(preview.isImageUploadInputVisible(),
                "News image input should be displayed on Preview page");
        String src = preview.getPreviewImageSrc();
        Assert.assertNotNull(src, "Preview image src should not be null");
        Assert.assertFalse(src.isEmpty(), "Preview image src should not be empty");

        editNewsPage = preview.backToEditing(getNewsId());
        Assert.assertTrue(editNewsPage.isPageOpenedAfterPreviewClickBack(),
                "User should be redirected to CreateNewsPage after clicking Back button");
        editNewsPage.reload();
        editNewsPage.getHeader().changeToEN();
        Assert.assertTrue(editNewsPage.isPageOpened(),
                "Create News page should be opened before creating news");
        editNewsPage.editNews(TEST_TITLE_EN, EcoNewsTag.getEn(TEST_TAGS), TEST_SOURCE, TEST_CONTENT_EN, TEST_FILEPATH);
        Assert.assertTrue(editNewsPage.isEditButtonEnabled(),
                "Edit button should become enabled after all fields are valid.");
        editNewsPage.clickEdit();
        UbsCourierPage ubsCourierPage = new UbsCourierPage(driver);
        ubsCourierPage.getHeader().changeToEN();
        Assert.assertTrue(ubsCourierPage.isPageOpened(),
                "User should be directed to UbsCourierPage");
        String message = ubsCourierPage.getMessageText();
        Assert.assertEquals(
                message,
                "Your news has been successfully published",
                "Success message text should be correct"
        );
    }
}
