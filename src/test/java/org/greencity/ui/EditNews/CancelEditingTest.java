package org.greencity.ui.EditNews;

import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.testrunners.EditNews.EditNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CancelEditingTest extends EditNewsENTestRunner {

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
