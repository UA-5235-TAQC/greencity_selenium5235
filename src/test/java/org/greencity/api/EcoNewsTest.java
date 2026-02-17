package org.greencity.api;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.Arrays;

@Epic("EcoNews API")
@Feature("Retrieve EcoNews Page")
@Story("Verify EcoNews page data for a specific author and favorite status")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsTest extends EcoNewsWithoutTokenRunner {

    @Description("This test verifies that the EcoNews page API returns correct pagination data " +
            "and validates the first news item, including its ID, title, author details, and tags.")
    @Test
    public void verifyEcoNewsPageDataTest() {
        SoftAssert softAssert = new SoftAssert();

        // Validate pagination data
        softAssert.assertEquals(pageResponse.getCurrentPage(), 0, "Current page number is incorrect");
        softAssert.assertFalse(pageResponse.getPage().isEmpty(), "News list should not be empty");

        // Get the first news item for detailed validation (expected id: 1373)
        softAssert.assertEquals(firstNews.getId(), 1373, "News ID does not match expected value");
        softAssert.assertEquals(firstNews.getTitle(), "Test_2", "News title does not match expected value");
        softAssert.assertEquals(firstNews.getAuthor().getId(), 149, "Author ID does not match expected value");
        softAssert.assertEquals(firstNews.getAuthor().getName(), "NameForTest611", "Author name does not match expected value");

        // Validate tags list
        softAssert.assertEquals(
                firstNews.getTagsEn(),
                Arrays.asList("News", "Events", "Education"),
                "Tags list does not match expected values"
        );

        softAssert.assertAll();
    }
}
