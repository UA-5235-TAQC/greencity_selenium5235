package org.greencity.ui.testrunners.NewsDetails;

import org.greencity.ui.components.HeaderComponent;
import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.NewsDetailsPage;
import org.greencity.ui.testrunners.CreateNews.BaseCreateNewsTestRunner;
import org.testng.annotations.BeforeMethod;

public abstract class BaseNewsDetailsTestRunner extends BaseCreateNewsTestRunner {

    protected NewsDetailsPage newsDetailsPage;

    @BeforeMethod
    public void beforeMethod() {
        super.beforeMethod();

        applyNewsTestData();

        createNewsPage.clickPublish();

        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        switchLanguage(ecoNewsPage.getHeader());

        NewsListItemComponent newsListItem = ecoNewsPage.getNewsCardByIndex(0);
        newsDetailsPage = newsListItem.click();

        newsDetailsPage.waitUntilOpened();
    }

    protected abstract void switchLanguage(HeaderComponent header);

    protected abstract void applyNewsTestData();
}
