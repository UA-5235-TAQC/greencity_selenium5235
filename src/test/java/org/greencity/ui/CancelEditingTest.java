package org.greencity.ui;

import org.greencity.ui.components.CancelModalComponent;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.CreateEditNews.EditNewsPage;
import org.greencity.ui.pages.CreateEditNews.NewsPreviewPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CancelEditingTest extends BaseTestRunner {

    private EditNewsPage editNewsPage;

    @BeforeClass
    public void LoginUser() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);
        editNewsPage = new EditNewsPage(driver, 818);
    }

    @BeforeMethod
    public void beforeMethod() {
        editNewsPage = editNewsPage.open();
    }

    @Test
    public void CancelEditing() {
        editNewsPage.getContentComponent().enterContent("Short text");
        Assert.assertTrue(editNewsPage.isCancelButtonVisible());
        editNewsPage.clickCancel();
        // Verify cancel modal is displayed
        Assert.assertTrue(editNewsPage.isCancelModalDisplayed(),
                "Confirmation modal should appear after clicking Cancel");

        CancelModalComponent cancelModal = editNewsPage.getCancelModal();

        // Verify buttons visibility
        Assert.assertTrue(cancelModal.isCancelButtonVisible(),
                "'Yes, cancel' button should be visible");

        Assert.assertTrue(cancelModal.isContinueEditingButtonVisible(),
                "'Continue editing' button should be visible");

        cancelModal.clickYesCancel();

        NewsPreviewPage newsPreviewPage = new NewsPreviewPage(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(newsPreviewPage.isPageOpened(),
                "User should be redirected to NewsPreviewPage");
        softAssert.assertAll();

        String currentUrl = driver.getCurrentUrl();
        softAssert = new SoftAssert();
        softAssert.assertNotNull(currentUrl, "Current URL should not be null");
        softAssert.assertTrue(currentUrl.contains("/news"), "URL should contain /news after cancel");
        softAssert.assertAll();
    }
}
