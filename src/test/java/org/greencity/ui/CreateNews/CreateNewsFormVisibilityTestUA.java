package org.greencity.ui.CreateNews;

import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.components.CreateEditNewsPage.ContentComponent;
import org.greencity.ui.components.CreateEditNewsPage.ImageComponent;
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

public class CreateNewsFormVisibilityTestUA extends BaseTestRunner {

    private CreateNewsPage createNewsPage;

    @BeforeClass
    public void LoginUser() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);
        createNewsPage = homePage.open().getHeader().clickEcoNewsLink().clickCreateNews();
    }

    @BeforeMethod
    public void beforeMethod() {
        createNewsPage.getHeader().changeToUK();
    }

    @Test(description = "Verify that the Create News form contains the particular fields in Ukrainian locale")
    public void verifyCreateNewsFormFieldsVisibilityInUkrainianLocale() {

        // 2. Tags
        List<String> expectedUkTags = EcoNewsTag.getAllUa();

        Assert.assertEquals(
                createNewsPage.getAllTags(),
                expectedUkTags,
                "Tags should be in Ukrainian"
        );

        // 3. Image Upload
        ImageComponent imageComponent = createNewsPage.getImageComponent();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                imageComponent.getDropZoneText(),
                "Перетягніть зображення сюди або",
                "Drop zone text should match"
        );
        softAssert.assertEquals(
                imageComponent.getBrowseText(),
                "огляд",
                "Browse link text should match"
        );
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

        // 5. Content
        ContentComponent content = createNewsPage.getContentComponent();
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

        // 7. Date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatterUa =
                DateTimeFormatter.ofPattern("MMM d, yyyy 'р.'")
                        .withLocale(Locale.forLanguageTag("uk-UA"));
        String expectedDateUa = today.format(formatterUa);
        Assert.assertEquals(
                createNewsPage.getPostDate(),
                expectedDateUa,
                "Date should be today's date"
        );

        // 8. Publish, Preview, Cancel buttons
        softAssert = new SoftAssert();
        softAssert.assertEquals(
                createNewsPage.getCancelButtonText(),
                "Вийти",
                "Cancel button text is incorrect"
        );
        softAssert.assertEquals(
                createNewsPage.getPreviewButtonText(),
                "Переглянути",
                "Preview button text is incorrect"
        );
        softAssert.assertEquals(
                createNewsPage.getPublishButtonText(),
                "Опублікувати",
                "Publish button text is incorrect"
        );
        softAssert.assertAll();

        createNewsPage.clickCancel();
        CancelModalComponent cancelModal = createNewsPage.getCancelModal();
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

        createNewsPage = new CreateNewsPage(driver).open();
        createNewsPage.getHeader().changeToUK();
        new NewsTestData().applyToUa(createNewsPage);
        NewsPreviewPage preview = createNewsPage.clickPreview();
        Assert.assertEquals(preview.getNewsTitle(), TEST_TITLE_UA,
                "News title on Preview page should match entered title");
        String expectedTag = EcoNewsTag.getUa(TEST_TAGS).getFirst();
        List<String> previewTags = preview.getTagTexts();
        Assert.assertTrue(previewTags.contains(expectedTag),
                "Preview page should contain tag: " + expectedTag);
        Assert.assertEquals(preview.getNewsText(), TEST_CONTENT_UA,
                "News content on Preview page should match entered content");

        createNewsPage = preview.clickBackToCreateNewsBtn();
        createNewsPage.reload();
        createNewsPage.getHeader().changeToUK();
        new NewsTestData().applyToUa(createNewsPage);
        createNewsPage.clickPublish();
        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.getHeader().changeToUK();
        String message = ecoNewsPage.getMessageText();
        Assert.assertEquals(
                message,
                "Ваша новина успішно опублікована",
                "Success message text should be correct"
        );
    }
}
