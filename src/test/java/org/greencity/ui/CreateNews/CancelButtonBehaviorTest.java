package org.greencity.ui.CreateNews;

import io.qameta.allure.testng.Tag;
import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.testrunners.CreateNews.CreateNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import static org.greencity.utils.NewsTestData.*;

@Tag("Create News")
@Epic("EcoNews Management")
@Feature("Create News")
@Story("Cancel button behavior")
@Severity(SeverityLevel.NORMAL)
@Issue("9")
public class CancelButtonBehaviorTest extends CreateNewsENTestRunner {

    @Description("Verify that clicking the Cancel button triggers a confirmation modal, " +
            "and selecting 'Yes, cancel' closes the form and redirects to the EcoNews page")
    @Test
    public void testCancelButtonBehavior() {

        Assert.assertTrue(createNewsPage.isPageOpened(),
                "Create News page was not opened");

        createNewsPage.enterTitle(TEST_TITLE_EN);
        createNewsPage.getContentComponent().enterContent(TEST_CONTENT_EN);

        createNewsPage.clickCancel();

        // Verify cancel modal is displayed
        Assert.assertTrue(createNewsPage.isCancelModalDisplayed(),
                "Confirmation modal should appear after clicking Cancel");

        CancelModalComponent cancelModal = createNewsPage.getCancelModal();

        // Verify modal texts
        Assert.assertEquals(
                cancelModal.getWarningTitleText(),
                "All created content will be lost.",
                "Warning title text is incorrect");

        Assert.assertEquals(
                cancelModal.getWarningSubtitleText(),
                "Do you still want to cancel news creating?",
                "Warning subtitle text is incorrect");

        // Verify buttons visibility
        Assert.assertTrue(cancelModal.isCancelButtonVisible(),
                "'Yes, cancel' button should be visible");

        Assert.assertTrue(cancelModal.isContinueEditingButtonVisible(),
                "'Continue editing' button should be visible");

        cancelModal.clickYesCancel();

        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        // Verify redirect
        Assert.assertTrue(ecoNewsPage.isPageOpened(),
                "User should be redirected to EcoNewsPage");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertTrue(currentUrl.contains("/news"), "URL should contain /news after cancel");
    }
}
