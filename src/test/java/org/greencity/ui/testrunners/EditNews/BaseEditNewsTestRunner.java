package org.greencity.ui.testrunners.EditNews;

import io.qameta.allure.Description;
import org.greencity.ui.components.HeaderComponent;
import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.pages.CreateEditNews.EditNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.NewsDetailsPage;
import org.greencity.ui.testrunners.CreateNews.BaseCreateNewsTestRunner;
import org.testng.annotations.BeforeMethod;

public abstract class BaseEditNewsTestRunner extends BaseCreateNewsTestRunner {

    protected EditNewsPage editNewsPage;
    protected Long ecoNewsId;

    @BeforeMethod
    @Description("Precondition: login user, create EcoNews, open Edit News page by provided newsId, and switch to the required language")
    public void beforeMethod() {
        super.beforeMethod();
        applyNewsTestData();
        createNewsPage.clickPublish();
        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        switchLanguage(ecoNewsPage.getHeader());
        NewsListItemComponent newsListItem = ecoNewsPage.getNewsCardByIndex(0);
        NewsDetailsPage newsDetailsPage = newsListItem.click();
        newsDetailsPage.waitUntilOpened();
        newsDetailsPage.clickEditButton();
        ecoNewsId = newsDetailsPage.getId();
        editNewsPage = new EditNewsPage(driver, ecoNewsId);
        switchLanguage(editNewsPage.getHeader());
    }

    protected abstract void switchLanguage(HeaderComponent header);

    protected abstract void applyNewsTestData();
}
