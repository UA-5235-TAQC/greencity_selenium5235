package org.greencity.ui;

import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.greencity.ui.pages.NewsPreviewPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.greencity.utils.TestValueProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PreviewPageTest extends BaseTestRunner {
    CreateNewsPage createNewsPage;
    HomePage homePage;
    MySpaceHabitsTabPage mySpaceHabitsTabPage;
    NewsPreviewPage previewPage;
    String newsTitle = "Super news title";
    String newsText = "This is a test preview content";

    @BeforeClass
    public void LoginUser() {
        createNewsPage = new CreateNewsPage(driver);
        homePage = new HomePage(driver);
        previewPage = new NewsPreviewPage(driver);
        loginUser(homePage);
        mySpaceHabitsTabPage = new MySpaceHabitsTabPage(driver);
        mySpaceHabitsTabPage.waitUntilVisible(mySpaceHabitsTabPage.getAddHabitButton());
    }

    @BeforeMethod
    public void BeforeMethod() { createNewsPage.open(); }

    @Test
    public void checkPreviewPage() {
        createNewsPage
                .getHeader()
                .changeToEN();
        createNewsPage
                .enterTitle(newsTitle)
                .enterContent(newsText)
                .clickPreview();

        Assert.assertEquals(driver.getCurrentUrl(), testValueProvider.getBaseUIGreenCityUrl() + "/news/preview");
        Assert.assertEquals(previewPage.getNewsTitle(), newsTitle);
        Assert.assertEquals(previewPage.getNewsText(), newsText);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.US);;
        String expectedDate = today.format(formatter);
        Assert.assertEquals(previewPage.getNewsCreatingDate(), expectedDate);
        Assert.assertEquals(previewPage.getAuthorName(), testValueProvider.getUserName());

        previewPage.clickBackToCreateNewsBtn();
        Assert.assertEquals(driver.getCurrentUrl(), testValueProvider.getBaseUIGreenCityUrl() + "/news/create-news");
        //There will be an error because of page components loading bug
        Assert.assertTrue(createNewsPage.isPageOpened());
    }
}
