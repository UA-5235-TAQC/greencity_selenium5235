package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import org.greencity.ui.CreateNews.steps.CreateNewsStepsEN;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateNewsTestEN extends BaseTestRunner {

    private CreateNewsPage createNewsPage;

    @BeforeClass
    @Step("Login as test user")
    public void loginUser() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);
        createNewsPage = homePage.open().getHeader().clickEcoNewsLink().clickCreateNews();
    }

    @BeforeMethod
    @Step("Set English locale for Create News page")
    public void beforeMethod() {
        createNewsPage.getHeader().changeToEN();
    }

    @Test
    @Description("This test verifies all fields, buttons, and interactions on the Create News page in English locale")
    @Issue("3")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCreateNewsFormFieldsInEnglishWithAllure() {
        Allure.label("priority", "high");
        CreateNewsStepsEN steps = new CreateNewsStepsEN(createNewsPage, driver);
        steps.verifyPageOpened();
        steps.verifyTitleEmpty();
        steps.verifyTags();
        steps.verifyImageUpload();
        steps.verifySourceField();
        steps.verifyContentField();
        steps.verifyAuthorField(testValueProvider.getUserName());
        steps.verifyPostDate();
        steps.verifyActionButtons();
        steps.clickCancelAndVerifyModal();
        steps.clickPreviewAndVerifyNewsPreviewPage();
        steps.clickPublishAndVerifySnackbarMessage();
    }
}
