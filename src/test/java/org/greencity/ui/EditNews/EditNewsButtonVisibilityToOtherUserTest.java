package org.greencity.ui.EditNews;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import org.greencity.ui.pages.NewsDetailsPage;
import org.greencity.ui.testrunners.NewsDetails.NewsDetailsENSecondUserTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditNewsButtonVisibilityToOtherUserTest extends NewsDetailsENSecondUserTestRunner {

    @Issue("12")
    @Description("Verify that the 'Edit news' button is not visible for news created by other users")
    @Test
    public void verifyEditButtonNotVisibleToOtherUsers() {
        LoginUser().getHeader().changeToEN();
        NewsDetailsPage detailsPage = new NewsDetailsPage(driver, newsId).open();
        Assert.assertTrue(detailsPage.isPageOpened(), "News Details page should be opened");
        Assert.assertFalse(
                detailsPage.isEditButtonVisible(),
                "Edit news button should NOT be visible to other users"
        );
    }
}
