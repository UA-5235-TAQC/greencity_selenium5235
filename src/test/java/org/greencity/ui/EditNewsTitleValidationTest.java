package org.greencity.ui;

import org.greencity.ui.components.AuthModal.SignInModal;
import org.greencity.ui.pages.*;
import org.greencity.ui.pages.MySpace.MySpaceNewsTabPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class EditNewsTitleValidationTest extends BaseTestRunner {

    private static final String NEWS_TITLE = "Temp news for edit test";

    @BeforeMethod
    public void beforeMethod() {
        driver.get(testValueProvider.getBaseUIGreenCityUrl());

        signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        createValidNews();
    }

    @Test
    public void verifyNewsCannotBeEditedWithoutTitle() {

        // 1️⃣ My Space → News
        MySpaceNewsTabPage newsTabPage = new HomePage(driver)
                .getHeader()
                .clickMySpace()
                .switchToNews();

        // 2️⃣ відкриваємо першу новину (клік по li)
        NewsDetailsPage newsDetailsPage =
                newsTabPage.getNewsList().get(0).open(); // 👉 клік по новині

        Assert.assertTrue(
                newsDetailsPage.isPageOpened(),
                "News details page should be opened"
        );

        // 3️⃣ Edit → CreateNewsPage
        CreateNewsPage editNewsPage = newsDetailsPage.clickEditButton();

        // 4️⃣ очищаємо Title
        editNewsPage.enterTitle("");

        // ❌ НЕ натискаємо Edit, бо кнопка має бути disabled

        // 🔴 Title підсвічений червоним
        Assert.assertTrue(
                editNewsPage.isTitleInvalid(),
                "Title field should be highlighted in red"
        );

        // 🚫 Кнопка Edit disabled
        Assert.assertFalse(
                editNewsPage.isPublishButtonEnabled(),
                "Edit button should be disabled"
        );

        // ❌ форма не сабмітнулась
        Assert.assertTrue(
                editNewsPage.isOnEditPage(),
                "User should stay on edit page, news must not be updated"
        );
    }

    // =====================
    // HELPERS
    // =====================

    private void signIn(String email, String password) {
        HomePage homePage = new HomePage(driver);
        homePage.getHeader().clickSignInLink();

        SignInModal signInModal = new SignInModal(driver);
        signInModal.enterEmail(email);
        signInModal.enterPassword(password);
        signInModal.clickSubmit();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/profile"));
    }

    private void createValidNews() {
        driver.get(testValueProvider.getBaseUIGreenCityUrl() + "/news");

        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.clickCreateNews();

        CreateNewsPage createNewsPage = new CreateNewsPage(driver);
        createNewsPage.enterTitle(NEWS_TITLE);
        createNewsPage.clickTagByName("Новини");
        createNewsPage.enterContent(
                "This content is valid and longer than 20 characters."
        );
        createNewsPage.clickPublish();
    }
}