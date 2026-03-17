package org.greencity.ui.testrunners.NewsDetails;

import io.qameta.allure.Description;
import org.greencity.ui.components.HeaderComponent;
import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.NewsDetailsPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.greencity.utils.ui.NewsTestData;
import org.testng.annotations.BeforeMethod;

public class NewsDetailsENSecondUserTestRunner extends BaseTestRunner {

    protected Long newsId;

    @Description("Precondition: login second user, switch language, and open Create News page")
    @BeforeMethod
    public void beforeMethod() {
        HeaderComponent header = LoginSecondUser().getHeader().changeToEN();
        CreateNewsPage createNewsPage = header
                .clickEcoNewsLink()
                .clickCreateNews();
        new NewsTestData().applyToEn(createNewsPage);

        createNewsPage.clickPublish();

        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.getHeader().changeToEN();

        NewsListItemComponent newsListItem = ecoNewsPage.getNewsCardByIndex(0);
        NewsDetailsPage newsDetailsPage = newsListItem.click();
        newsDetailsPage.waitUntilOpened();
        newsId = newsDetailsPage.getNewsIdFromUrl();
        newsDetailsPage.getHeader().clickProfileDropdown().signOut();
    }
}
