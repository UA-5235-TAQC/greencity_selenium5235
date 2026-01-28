package org.greencity.ui;

import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class SourceFieldValidationTest extends BaseTestRunner {

    private CreateNewsPage createNewsPage;
    private EcoNewsPage ecoNewsPage;

    @BeforeClass
    public void LoginUser() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);
        createNewsPage = new CreateNewsPage(driver);
        ecoNewsPage = new EcoNewsPage(driver);
    }

    @BeforeMethod
    public void beforeMethod() {
        waitUntilAuthModalDisappear();
    }

    @Test
    public void SourceFieldValidation() {
        // Navigate and create news item with valid data and empty Source Field
        createNewsPage
                .getHeader()
                .clickEcoNewsLink()
                .clickCreateNews()
                .enterTitle("A".repeat(170))
                .enterContent("A".repeat(1000))
                .selectTags(List.of(
                        EcoNewsTag.NEWS.getTagName(),
                        EcoNewsTag.EVENTS.getTagName()));

        // Verify that publish button is "Enabled" and data is valid
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createNewsPage.getTitleCounterText(), "170/170", "Title length doesn't match");
        softAssert.assertEquals(createNewsPage.getTitleValue(), "A".repeat(170), "Title text doesn't match");
        softAssert.assertEquals(createNewsPage.getContent(), "A".repeat(1000), "The content field text doesn't match");
        softAssert.assertTrue(createNewsPage.isPublishButtonEnabled(), "Publish Button is not enabled");
        softAssert.assertAll();

        // Publish
        createNewsPage.clickPublish();

        // Verify that success message is appears and matches the given text
        ecoNewsPage.waitForMessageAppear();
        Assert.assertEquals(ecoNewsPage.getMessageText(), "Your news has been successfully published");
        ecoNewsPage.waitForMessageDisappear();

        // Create new news item with non-valid Source Field data
        ecoNewsPage.clickCreateNews()
                .enterTitle("W".repeat(20))
                .enterContent("W".repeat(100))
                .enterSource("W".repeat(20))
                .selectTags(List.of(
                        EcoNewsTag.EDUCATION.getTagName(),
                        EcoNewsTag.ADS.getTagName()));

       // Verify that publish button is "Disabled"
       Assert.assertFalse(createNewsPage.isPublishButtonEnabled(), "Button is active, but should be disabled");

       // Clear Source field and enter valid data
       createNewsPage
               .clearSourceField()
               .enterSource("https://example.com")
               .clickPublish();

       // Verify that success message is appears and matches the given text
       ecoNewsPage.waitForMessageAppear();
       Assert.assertEquals(ecoNewsPage.getMessageText(), "Your news has been successfully published");
    }

    public void waitUntilAuthModalDisappear() {
        createNewsPage.wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("app-auth-modal"))));
    }
}
