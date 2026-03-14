package org.greencity.ui.testrunners.CreateNews;

import io.qameta.allure.Description;
import org.greencity.ui.components.HeaderComponent;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.annotations.BeforeMethod;

public abstract class BaseCreateNewsTestRunner extends BaseTestRunner {

    protected CreateNewsPage createNewsPage;

    @Description("Precondition: login user, switch language, and open Create News page")
    @BeforeMethod
    public void beforeMethod() {
        HeaderComponent header = LoginUser().getHeader();

        switchLanguage(header);

        createNewsPage = header
                .clickEcoNewsLink()
                .clickCreateNews();
    }

    protected abstract void switchLanguage(HeaderComponent header);
}
