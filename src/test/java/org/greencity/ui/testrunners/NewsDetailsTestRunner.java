package org.greencity.ui.testrunners;

import io.qameta.allure.Description;
import org.greencity.ui.pages.NewsDetailsPage;
import org.testng.annotations.BeforeMethod;

public class NewsDetailsTestRunner extends BaseTestRunner {

    protected NewsDetailsPage newsDetailsPage;

    @Description("Precondition: login user, open News Details page by provided newsId")
    @BeforeMethod
    public void beforeMethod() {
        LoginUser();
        newsDetailsPage = new NewsDetailsPage(driver, 564).open();
        newsDetailsPage.getHeader().changeToEN();
    }
}
