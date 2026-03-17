package org.greencity.ui.EditNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.components.HeaderComponent;
import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.NewsDetailsPage;
import org.greencity.ui.testrunners.NewsDetails.NewsDetailsENTestRunner;
import org.greencity.utils.ui.NewsTestData;
import org.testng.Assert;
import org.testng.annotations.Test;

@Tag("Edit News")
@Epic("EcoNews Management")
@Feature("Edit existing news")
@Story("Verify that only the author can see the 'Edit news' button")
@Severity(SeverityLevel.CRITICAL)
public class EditNewsButtonVisibilityTest extends NewsDetailsENTestRunner {

    @Issue("11")
    @Description("Verify that the 'Edit news' button is visible only to the author of the news")
    @Test
    public void verifyEditButtonVisibleToAuthor() {
        Assert.assertTrue(newsDetailsPage.isPageOpened(), "News Details page should be opened");
        Assert.assertTrue(
                newsDetailsPage.isEditButtonVisible(),
                "Edit news button should be visible to the author"
        );
        Assert.assertTrue(
                newsDetailsPage.isEditButtonEnabled(),
                "Edit news button should be enabled for the author"
        );
        Assert.assertEquals(
                newsDetailsPage.getEditButtonText(),
                "Edit news",
                "Edit button text is incorrect"
        );
    }
}
