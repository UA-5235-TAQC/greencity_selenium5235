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
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.UbsCourierPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.greencity.utils.NewsTestData.*;

public class EditNewsFormVisibilityTestUA extends BaseTestRunner {

    private EditNewsPage editNewsPage;
    private long newsId = 888;

    @Description("A test that allows a given user to log in to the system")
    @Severity(SeverityLevel.CRITICAL)
    @BeforeClass
    public void LoginUser() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);
        editNewsPage = new EditNewsPage(driver, newsId);
    }

    @BeforeMethod
    public void beforeMethod() {
        editNewsPage = editNewsPage.open();
        editNewsPage.getHeader().changeToUK();
    }

    @Tag("Edit")
    @Feature("Edit news page")
    @Issue("14")
    @TmsLink("https://github.com/UA-5235-TAQC/greencity_selenium5235/issues/14")
    @Description("Verify that the Edit News form contains the particular fields in Ukrainian locale")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void verifyEditNewsFormFieldsVisibilityInUkrainianLocale() {

        // 1. Title
        Assert.assertTrue(editNewsPage.isPageOpened());
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "4/170", "Title counter should be 4/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 4, "Title length should be 4");
        Assert.assertEquals(editNewsPage.getTitleValue(), TEST_TITLE_UA, "Title should be the same as the test title");

        String toAppend = " Новий";
        editNewsPage.appendTitle(toAppend);
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "10/170", "Title counter should be 10/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 10, "Title length should be 10");
        Assert.assertEquals(editNewsPage.getTitleValue(), TEST_TITLE_UA + toAppend, "Title should be appended");

        String toPrepend = "Додати ";
        editNewsPage.prependTitle(toPrepend);
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "17/170", "Title counter should be 17/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 17, "Title length should be 17");
        Assert.assertEquals(editNewsPage.getTitleValue(), toPrepend + TEST_TITLE_UA + toAppend, "Title should be prepended");

        editNewsPage.removeLastTitleChars(6);
        editNewsPage.removeFirstTitleChars(7);
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "4/170", "Title counter should be 4/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 4, "Title length should be 4");
        Assert.assertEquals(editNewsPage.getTitleValue(), TEST_TITLE_UA, "Title should be the same as the test title");

        String testTitle = toPrepend + TEST_TITLE_UA;
        editNewsPage.enterTitle(testTitle);
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "11/170", "Title counter should be 11/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 11, "Title length should be 11");
        Assert.assertEquals(editNewsPage.getTitleValue(), testTitle, "Title should be new");

        // 2. Tags
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

        editNewsPage.selectTags(EcoNewsTag.getUa(List.of(EcoNewsTag.EVENTS, EcoNewsTag.EDUCATION)));
        actualTagsUa = editNewsPage.getSelectedTags();
        List<EcoNewsTag> expectedTags = new ArrayList<>(TEST_TAGS);
        expectedTags.add(EcoNewsTag.EVENTS);
        expectedTags.add(EcoNewsTag.EDUCATION);
        List<String> expectedTagsUa = EcoNewsTag.getUa(expectedTags);
        Assert.assertEquals(actualTagsUa.size(), 3, "Selected tags do not match expected tags");
        Assert.assertEquals(expectedTagsUa, actualTagsUa, "Selected tags do not match expected tags");

        List <EcoNewsTag> tagsToSelect = List.of(EcoNewsTag.INITIATIVES, EcoNewsTag.ADS);
        List <String> tagsToSelectUa = EcoNewsTag.getUa(tagsToSelect);
        editNewsPage.clearAllSelectedTags().selectTags(tagsToSelectUa);
        actualTagsUa = editNewsPage.getSelectedTags();
        tagItemsUa = editNewsPage.getTagItems();
        anySelectedUa = tagItemsUa.stream().anyMatch(TagItem::isSelected);
        Assert.assertTrue(anySelectedUa, "2 tags should be selected");
        Assert.assertEquals(actualTagsUa.size(), 2, "Selected tags do not match expected tags");
        Assert.assertEquals(tagsToSelectUa, actualTagsUa, "Selected tags do not match expected tags");

        // 3. Image Upload
        ImageComponent imageComponent = editNewsPage.getImageComponent();
        SoftAssert softAssert = new SoftAssert();
        Assert.assertEquals(
                imageComponent.getImageError(),
                "Завантажуйте лише PNG або JPEG. Розмір файлу не повинен перевищувати 10Mb",
                "Error message should match"
        );
        softAssert.assertEquals(
                imageComponent.getCancelCropperText(),
                "Скасувати",
                "Cancel cropper button text should match"
        );
        softAssert.assertEquals(
                imageComponent.getSubmitCropperText(),
                "Застосувати",
                "Submit cropper button text should match"
        );
        softAssert.assertAll();

        // 4. Source
        softAssert = new SoftAssert();
        softAssert.assertEquals(
                editNewsPage.getSourceMessage(),
                "Джерело (не обов'язково)\n" +
                        "Будь ласка, додайте посилання на оригінальну статтю/новину/публікацію. Посилання повинно починатись з http(s)://",
                "Incorrect source validation message"
        );
        softAssert.assertEquals(
                editNewsPage.getSourcePlaceholder(),
                "Посилання на зовнішнє джерело",
                "Incorrect source placeholder text"
        );
        softAssert.assertAll();

        // 5. Content
        ContentComponent content = editNewsPage.getContentComponent();
        Assert.assertEquals(content.getContentText(), TEST_CONTENT_UA,
                "Content should be the same as test content");

        content.enterContentNotClear(toAppend);
        Assert.assertTrue(content.isContentValid(), "Content is invalid");
        Assert.assertEquals(content.getContentCounter(),
                "Кількість символів: 36",
                "Content counter is incorrect");
        Assert.assertEquals(content.getContentLengthNumber(), 36, "Content length should be 36");
        Assert.assertEquals(content.getContentText(), TEST_CONTENT_UA + toAppend, "Content should be appended");

        content.prependContent(toPrepend);
        Assert.assertTrue(content.isContentValid(), "Content is invalid");
        Assert.assertEquals(content.getContentCounter(),
                "Кількість символів: 43",
                "Content counter is incorrect");
        Assert.assertEquals(content.getContentLengthNumber(), 43, "Content length should be 43");
        Assert.assertEquals(content.getContentText(), toPrepend + TEST_CONTENT_UA + toAppend, "Content should be prepended");

        content.removeLastContentChars(6);
        content.removeFirstContentChars(7);
        Assert.assertEquals(content.getContentCounter(),
                "Кількість символів: 30",
                "Content counter is incorrect");
        Assert.assertEquals(content.getContentLengthNumber(), 30, "Content length should be 30");
        Assert.assertEquals(content.getContentText(), TEST_CONTENT_UA, "Content should be the same as the test content");

        content.clearContent();
        softAssert = new SoftAssert();
        softAssert.assertEquals(
                content.getContentMessage(),
                "Поле повинно містити не менше 20 та не більше 63 206 символів",
                "Content message is incorrect "
        );
        softAssert.assertEquals(
                content.getContentPlaceholder(),
                "напр. Короткий опис новини, план заходу",
                "Incorrect content placeholder text"
        );
        softAssert.assertAll();

        String testString = TEST_TITLE_UA + " " + TEST_CONTENT_UA;
        content.enterContent(testString);
        Assert.assertEquals(content.getContentCounter(),
                "Кількість символів: 35",
                "Content counter is incorrect");
        Assert.assertEquals(content.getContentLengthNumber(), 35, "Content length should be 35");
        Assert.assertEquals(content.getContentText(), testString, "Content should be the same as the test string");

        // 7. Date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatterUa =
                DateTimeFormatter.ofPattern("MMM d, yyyy 'р.'")
                        .withLocale(Locale.forLanguageTag("uk-UA"));
        String expectedDateUa = today.format(formatterUa);
        Assert.assertEquals(
                editNewsPage.getPostDate(),
                expectedDateUa,
                "Date should be today's date"
        );

        // 8. Publish, Preview, Cancel buttons
        softAssert = new SoftAssert();
        softAssert.assertEquals(
                editNewsPage.getCancelButtonText(),
                "Вийти",
                "Cancel button text is incorrect"
        );
        softAssert.assertEquals(
                editNewsPage.getPreviewButtonText(),
                "Переглянути",
                "Preview button text is incorrect"
        );
        softAssert.assertEquals(
                editNewsPage.getEditButtonText(),
                "Редагувати",
                "Edit button text is incorrect"
        );
        softAssert.assertAll();

        editNewsPage.clickCancel();
        CancelModalComponent cancelModal = editNewsPage.getCancelModal();
        softAssert = new SoftAssert();
        softAssert.assertEquals(
                cancelModal.getWarningTitleText(),
                "Внесені зміни будуть втрачені.",
                "Warning title text is incorrect");
        softAssert.assertEquals(
                cancelModal.getWarningSubtitleText(),
                "Ви впевнені, що хочете видалити новину?",
                "Warning subtitle text is incorrect");
        softAssert.assertEquals(cancelModal.getYesCancelButtonText(), "Скасувати");
        softAssert.assertEquals(cancelModal.getContinueEditingButtonText(), "Продовжити");
        softAssert.assertAll();
        cancelModal.clickYesCancel();
        cancelModal.waitUntilClosed();

        editNewsPage = new EditNewsPage(driver, newsId).open();
        editNewsPage.getHeader().changeToUK();
        testTitle = "Макс";
        List<String> testTags = EcoNewsTag.getUa(List.of(EcoNewsTag.INITIATIVES, EcoNewsTag.EVENTS));
        String testSource = "https://en.wikipedia.org/wiki/Main_Page";
        String testContent = "Гора Едзіза — вулканічна гора в окрузі Кассіар-Ленд на північному заході Британської Колумбії , Канада.";
        editNewsPage.editNews(testTitle, testTags, testSource, testContent, TEST2_FILEPATH);
        NewsPreviewPage preview = editNewsPage.clickPreview();
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

        Assert.assertEquals(preview.getNewsSource(), testSource,
                "News source on Preview page should match entered source");
        Assert.assertEquals(preview.getNewsText(), testContent,
                "News content on Preview page should match entered content");
        Assert.assertTrue(preview.isImageUploadInputVisible(),
                "News image input should be displayed on Preview page");
        String src = preview.getPreviewImageSrc();
        Assert.assertNotNull(src, "Preview image src should not be null");
        Assert.assertFalse(src.isEmpty(), "Preview image src should not be empty");

        editNewsPage = preview.backToEditing(newsId);
        editNewsPage.reload();
        editNewsPage.getHeader().changeToUK();
        editNewsPage.editNews(TEST_TITLE_UA, EcoNewsTag.getUa(TEST_TAGS), TEST_SOURCE, TEST_CONTENT_UA, TEST_FILEPATH);
        editNewsPage.clickEdit();
        UbsCourierPage ubsCourierPage = new UbsCourierPage(driver);
        ubsCourierPage.getHeader().changeToEN();
        Assert.assertTrue(ubsCourierPage.isPageOpened(),
                "User should be directed to UbsCourierPage");
        String message = ubsCourierPage.getMessageText();
        Assert.assertEquals(
                message,
                "Ваша новина успішно опублікована",
                "Success message text should be correct"
        );
    }
}
