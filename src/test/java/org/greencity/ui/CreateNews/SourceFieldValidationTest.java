package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.testrunners.CreateNews.CreateNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

@Tag("Create News")
@Epic("EcoNews Management")
@Feature("Create News")
@Story("Source field validation")
@Severity(SeverityLevel.NORMAL)
@Issue("8")
public class SourceFieldValidationTest extends CreateNewsENTestRunner {

    @Description("Verify that news can be published with empty Source field")
    @Test
    public void SourceFieldValidation() {
        // Navigate and create news item with valid data and empty Source Field
        createNewsPage
                .createNews(
                        "A".repeat(170),
                        List.of(
                                EcoNewsTag.NEWS.getEn(),
                                EcoNewsTag.EVENTS.getEn()),
                        "",
                        "A".repeat(1000),
                        null
                );


        // Verify that publish button is "Enabled" and data is valid
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createNewsPage.getTitleCounterText(), "170/170", "Title length doesn't match");
        softAssert.assertEquals(createNewsPage.getTitleValue(), "A".repeat(170), "Title text doesn't match");
        softAssert.assertEquals(createNewsPage.getContentComponent().getContentText(), "A".repeat(1000), "The content field text doesn't match");
        softAssert.assertTrue(createNewsPage.isPublishButtonEnabled(), "Publish Button is not enabled");
        softAssert.assertAll();

        // Publish
        createNewsPage.clickPublish();
        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        // Verify that success message is appears and matches the given text
        ecoNewsPage.waitForMessageAppear();
        Assert.assertEquals(ecoNewsPage.getMessageText(), "Your news has been successfully published");
        ecoNewsPage.waitForMessageDisappear();
    }

    @Description("Verify validation logic for invalid Source field and successful publish after correction")
    @Test
    public void SourceFieldNonValidValidation() {
        // Create new news item with non-valid Source Field data
        createNewsPage
                .createNews(
                        "W".repeat(20),
                        List.of(
                                EcoNewsTag.EDUCATION.getEn(),
                                EcoNewsTag.ADS.getEn()),
                        "W".repeat(20),
                        "W".repeat(100),
                        null
                );

       // Verify that publish button is "Disabled"
       Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Button is active, but should be disabled");

       // Clear Source field and enter valid data
       createNewsPage
               .clearSourceField()
               .enterSource("https://example.com")
               .clickPublish();

       // Verify that success message is appears and matches the given text
       EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
       ecoNewsPage.waitForMessageAppear();
       Assert.assertEquals(ecoNewsPage.getMessageText(), "Your news has been successfully published");
    }
}
