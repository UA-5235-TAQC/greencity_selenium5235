package org.greencity.api;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.api.models.econews.EcoNewsPageResponse;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.greencity.utils.api.EcoNewsAssertions;
import org.greencity.utils.api.EcoNewsService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("EcoNews API")
@Feature("Retrieve EcoNews Page")
@Story("Verify EcoNews page data for a specific author and favorite status")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsTest extends CreateNewsRunner {

    private EcoNewsService ecoNewsService;

    @BeforeClass
    public void setupService() {
        ecoNewsService = new EcoNewsService(ecoNewsClient);
    }

    @Description("This test verifies that the EcoNews page API returns correct pagination data " +
            "and validates the first news item, including its ID, title, author details, and tags.")
    @Test
    public void verifyEcoNewsPageDataTest() {
        EcoNewsPageResponse pageResponse =
                ecoNewsService.getFirstPageForAuthor(testValueProvider.getUserId());

        EcoNewsResponse firstNews =
                pageResponse.getPage().getFirst();

        SoftAssert softAssert = new SoftAssert();

        // Validate pagination data
        softAssert.assertEquals(
                pageResponse.getCurrentPage(),
                0,
                "Current page number is incorrect"
        );

        softAssert.assertFalse(
                pageResponse.getPage().isEmpty(),
                "News list should not be empty"
        );

        softAssert.assertAll();

        // Detailed validation
        EcoNewsAssertions.assertEcoNewsResponse(
                firstNews,
                createdNews.getId(),
                createdNews.getTitle(),
                createdNews.getContent(),
                createdNews.getShortInfo(),
                createdNews.getCreationDate(),
                createdNews.getTagsEn(),
                createdNews.getTagsUk(),
                createdNews.getAuthor().getId(),
                createdNews.getAuthor().getName(),
                false,
                true
        );
    }
}
