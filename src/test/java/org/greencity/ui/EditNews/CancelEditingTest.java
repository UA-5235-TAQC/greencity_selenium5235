package org.greencity.ui.EditNews;

import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.pages.CreateEditNews.EditNewsPage;
import org.greencity.ui.pages.CreateEditNews.NewsPreviewPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CancelEditingTest extends BaseTestRunner {

    private EditNewsPage editNewsPage;

    @BeforeMethod
    public void beforeMethod() {
        LoginUser();
        editNewsPage = new EditNewsPage(driver, 818).open();
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

        
    }
}
