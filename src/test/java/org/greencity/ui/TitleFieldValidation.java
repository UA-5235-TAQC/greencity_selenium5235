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

public class TitleFieldValidation extends BaseTestRunner {

    private final String VALID_CONTENT = "This is a valid content with more than 20 characters for the news item.";
    private CreateNewsPage createNewsPage;

    @BeforeMethod
    public void beforeMethod() {
        driver.get(testValueProvider.getBaseUIGreenCityUrl());

        // Log in using credentials from the configuration file
        signIn(testValueProvider.getUserEmail(), testValueProvider.getUserPassword());

        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        driver.get(testValueProvider.getBaseUIGreenCityUrl() + "/news");

        // Wait for the "Create News" button to be displayed and clickable
        createNewsPage = new CreateNewsPage(driver);
        ecoNewsPage.clickCreateNews();
        ecoNewsPage.getHeader().changeToEN();
    }

    public void signIn(String email, String password) {
        HomePage homePage = new HomePage(driver);
        homePage.getHeader().clickSignInLink();

        SignInModal signInModal = new SignInModal(driver);
        signInModal.enterEmail(email);
        signInModal.enterPassword(password);
        signInModal.clickSubmit();

        // Explicit wait for navigation to the profile page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/profile"));
    }

    @Test
    public void verifyTitleFieldAndPublishButtonLogic() {
        // 1. Mandatory field validation (Empty title)
        createNewsPage.enterTitle("");
        createNewsPage.enterContent(""); // Triggering "touched" state for validation
        Assert.assertTrue(createNewsPage.isTitleInvalid(), "Title border should be red (ng-invalid) when empty.");
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publish button should be disabled when the title is empty.");
        Assert.assertEquals(createNewsPage.getTitleCounterText(), "0/170", "Counter should display 0/170.");

        // 2. Character limit validation (Entering 171 characters)
        String longTitle = "A".repeat(171);
        createNewsPage.enterTitle(longTitle);

        // Verify that the field contains 171characters (maxlength not allows it)
        Assert.assertEquals(createNewsPage.getTitleLength(), 171, "The field should not allow inputting 171 characters.");

        // Verify that the counter shows "171/170" and is highlighted in red
        Assert.assertEquals(createNewsPage.getTitleCounterText(), "171/170", "Counter should show 171/170.");
        Assert.assertTrue(createNewsPage.isTitleCounterWarningDisplayed(), "Character counter should be highlighted in red (warning state).");

        // Key point: the button must remain disabled because the limit is 170
        Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Publish button should be disabled when the title exceeds 170 characters.");

        // 3. Returning to a valid state (9 characters)
        createNewsPage.enterTitle("Test News");
        Assert.assertEquals(createNewsPage.getTitleCounterText(), "9/170", "Counter should display 9/170.");
        Assert.assertFalse(createNewsPage.isTitleInvalid(), "Red highlight should disappear when the title is valid.");

        // 4. Filling the remaining mandatory fields
        createNewsPage.clickTagByName(EcoNewsTag.NEWS.getTagName());
        createNewsPage.enterContent(VALID_CONTENT);

        // 5. Final activation check
        Assert.assertTrue(createNewsPage.isPublishButtonEnabled(), "Publish button should become enabled after all fields are valid.");
    }
}