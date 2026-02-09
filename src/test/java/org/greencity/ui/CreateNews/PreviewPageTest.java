package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import org.greencity.ui.pages.CreateEditNews.NewsPreviewPage;
import org.greencity.ui.testrunners.CreateNews.CreateNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PreviewPageTest extends CreateNewsENTestRunner {
    NewsPreviewPage previewPage;
    String newsTitle = "Super news title";
    String newsText = "This is a test preview content";

    @Epic("Smoke test")
    @Feature("Preview page")
    @Description("The test checks whether the entered title and content match the actual")
    @Severity(SeverityLevel.NORMAL)
    @Issue("10")
    @Test
    public void checkPreviewPage() {
        createNewsPage
                .enterTitle(newsTitle)
                .getContentComponent().enterContent(newsText);
        previewPage = createNewsPage.clickPreview();

        Assert.assertEquals(getDriver().getCurrentUrl(), testValueProvider.getBaseUIGreenCityUrl() + "#/greenCity/news/preview");
        Assert.assertEquals(previewPage.getNewsTitle(), newsTitle);
        Assert.assertEquals(previewPage.getNewsText(), newsText);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy").withLocale(Locale.US);
        String expectedDate = today.format(formatter);
        Assert.assertEquals(previewPage.getNewsCreatingDate(), expectedDate);
        Assert.assertEquals(previewPage.getAuthorName(), testValueProvider.getUserName());

        previewPage.clickBackToCreateNewsBtn();
        Assert.assertEquals(getDriver().getCurrentUrl(), testValueProvider.getBaseUIGreenCityUrl() + "#/greenCity/news/create-news");
        Assert.assertTrue(createNewsPage.isPageOpenedAfterPreviewClickBack());
    }
}
