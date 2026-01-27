package org.greencity.ui;

import org.greencity.ui.components.AuthModal.SignInModal;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class MainTextFieldValidationTest extends BaseTestRunner {

    private CreateNewsPage createNewsPage;

    @BeforeMethod
    public void beforeMethod() {
        driver.get(testValueProvider.getBaseUIGreenCityUrl());

        signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        driver.get(testValueProvider.getBaseUIGreenCityUrl() + "/news");

        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.getHeader().changeToEN();
        ecoNewsPage.clickCreateNews();

        createNewsPage = new CreateNewsPage(driver);
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page was not opened");
    }

    @Test
public void verifyMainTextValidationAndPublishButtonLogic() {

    // ---------- 1️⃣ < 20 symbols ----------
    createNewsPage.enterTitle("Test");
    createNewsPage.enterContent("Short text");
    createNewsPage.clickTagByName(EcoNewsTag.NEWS.getTagName());

    createNewsPage.clickPublish();

    Assert.assertTrue(
            createNewsPage.isContentWarningDisplayed(),
            "Warning should be displayed for content shorter than 20 symbols"
    );

    Assert.assertFalse(
            createNewsPage.isPublishButtonEnabled(),
            "Publish button must be disabled when content is invalid"
    );

    // ---------- 2️⃣ > 63 206 symbols ----------
    String tooLongText = "A".repeat(63207);
    createNewsPage.enterContent(tooLongText);

    Assert.assertEquals(
            createNewsPage.getContent().length(),
            63206,
            "Content should be truncated to 63 206 symbols"
    );

    Assert.assertFalse(
            createNewsPage.isContentWarningDisplayed(),
            "Warning should not be displayed after truncation"
    );

    // ---------- 3️⃣ valid content ----------
    String validContent = "This is a valid test content with more than twenty symbols.";
    createNewsPage.enterContent(validContent);

    Assert.assertFalse(
            createNewsPage.isContentWarningDisplayed(),
            "Warning should disappear for valid content"
    );

    Assert.assertTrue(
            createNewsPage.isPublishButtonEnabled(),
            "Publish button should be enabled for valid content"
    );

    // ---------- 4️⃣ Publish ----------
    createNewsPage.clickPublish();
}

    private void signIn(String email, String password) {
        HomePage homePage = new HomePage(driver);
        homePage.getHeader().clickSignInLink();

        SignInModal signInModal = new SignInModal(driver);
        signInModal.enterEmail(email);
        signInModal.enterPassword(password);
        signInModal.clickSubmit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/profile"));
    }
}