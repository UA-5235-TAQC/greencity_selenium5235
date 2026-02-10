package org.greencity.ui.testrunners.EditNews;

import io.qameta.allure.Description;
import org.greencity.ui.components.HeaderComponent;
import org.greencity.ui.pages.CreateEditNews.EditNewsPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.annotations.BeforeMethod;

public abstract class BaseEditNewsTestRunner extends BaseTestRunner {

    protected EditNewsPage editNewsPage;

    @Description("Precondition: login user, open Edit News page by provided newsId, and switch to the required language")
    @BeforeMethod
    public void beforeMethod() {
        LoginUser();

        editNewsPage = new EditNewsPage(driver, getNewsId()).open();

        switchLanguage(editNewsPage.getHeader());
    }

    protected abstract long getNewsId();

    protected abstract void switchLanguage(HeaderComponent header);
}
