package org.greencity.ui.EditNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.testrunners.EditNews.EditNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

@Tag("Edit News")
@Epic("EcoNews Management")
@Feature("Edit news page")
@Story("Empty title field validation")
@Severity(SeverityLevel.NORMAL)
@Issue("15")
public class EmptyTitleValidationTest extends EditNewsENTestRunner {

    @Description("Verify that the Edit News page disables the Edit button and highlights the title field " +
            "when the title is empty, and enables the button when all required fields are valid")
    @Test
    public void verifyEmptyTitleField() {
        Assert.assertTrue(editNewsPage.isPageOpened());

        // 1. Mandatory field validation (Empty title)
        String title = editNewsPage.getTitleValue();
        editNewsPage.enterTitle("");
        String titleCounter = editNewsPage.getTitleCounterText();
        Assert.assertEquals(titleCounter, "0/170", "Title counter should be 0/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 0, "Title length should be 0 by default");
        Assert.assertEquals(editNewsPage.getTitleValue(), "", "Title should be empty by default");
        Assert.assertTrue(editNewsPage.isTitleInvalid(), "Title border should be red (ng-invalid) when empty.");
        Assert.assertFalse(editNewsPage.isEditButtonEnabled(), "Edit button should be disabled when the title is empty.");

        // 2. Returning to a valid state
        editNewsPage.enterTitle(title);
        Assert.assertEquals(editNewsPage.getTitleCounterText(), "4/170", "Title counter should be 4/170");
        Assert.assertEquals(editNewsPage.getTitleLength(), 4, "Title length should be 4");
        Assert.assertEquals(editNewsPage.getTitleValue(), title, "Title should be the same as the test title");
        Assert.assertFalse(editNewsPage.isTitleInvalid(), "Red highlight should disappear when the title is valid.");
        Assert.assertTrue(editNewsPage.isEditButtonEnabled(), "Edit button should become enabled after all fields are valid.");
    }
}
