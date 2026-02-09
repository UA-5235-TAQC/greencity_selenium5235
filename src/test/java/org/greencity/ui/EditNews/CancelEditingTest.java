package org.greencity.ui.EditNews;

import io.qameta.allure.testng.Tag;
import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.testrunners.EditNews.EditNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

@Tag("Edit News")
@Epic("EcoNews Management")
@Feature("Edit news page")
@Story("Cancel editing behavior")
@Severity(SeverityLevel.NORMAL)
@Issue("18")
public class CancelEditingTest extends EditNewsENTestRunner {

    @Description("Verify that clicking the Cancel button during editing triggers a confirmation modal, " +
            "and selecting 'Yes, cancel' closes the editor")
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
