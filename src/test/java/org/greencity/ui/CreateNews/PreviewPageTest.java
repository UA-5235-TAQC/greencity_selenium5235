package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.pages.CreateEditNews.NewsPreviewPage;
import org.greencity.ui.testrunners.CreateNews.CreateNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Tag("Create News")
@Epic("EcoNews Management")
@Feature("Create News Preview")
@Story("Verify preview page displays correct title, content, author and date")
@Severity(SeverityLevel.NORMAL)
@Issue("10")
public class PreviewPageTest extends CreateNewsENTestRunner {
    NewsPreviewPage previewPage;
    String newsTitle = "Super news title";
    String newsText = "This is a test preview content";

    @Description("Verify that the Preview page displays the correct news title, content, author name, and creation date, " +
            "and that the 'Back to Create News' button redirects correctly")
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
