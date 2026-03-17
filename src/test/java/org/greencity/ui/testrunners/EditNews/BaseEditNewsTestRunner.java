package org.greencity.ui.testrunners.EditNews;

import org.greencity.ui.pages.CreateEditNews.EditNewsPage;
import org.greencity.ui.testrunners.NewsDetails.BaseNewsDetailsTestRunner;
import org.testng.annotations.BeforeMethod;

public abstract class BaseEditNewsTestRunner extends BaseNewsDetailsTestRunner {

    protected EditNewsPage editNewsPage;
    protected Long ecoNewsId;

    @BeforeMethod
    public void openEditPage() {
        newsDetailsPage.clickEditButton();
        ecoNewsId = newsDetailsPage.getNewsId();
        editNewsPage = new EditNewsPage(driver, ecoNewsId);
        switchLanguage(editNewsPage.getHeader());
    }
}
