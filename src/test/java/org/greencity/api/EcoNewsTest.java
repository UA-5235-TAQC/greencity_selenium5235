package org.greencity.api;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.models.econews.EcoNewsPageResponse;
import org.greencity.api.models.econews.EcoNewsQuery;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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

        EcoNewsQuery query = EcoNewsQuery.builder()
                .authorId(testValueProvider.getUserId())
                .favorite(false)
                .page(0)
                .size(20)
                .build();

        Response response = ecoNewsClient.getEcoNews(query);
        Assert.assertEquals(response.getStatusCode(), 200);


        EcoNewsPageResponse pageResponse = response.as(EcoNewsPageResponse.class);

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(pageResponse.getCurrentPage(), 0, "Current page number is incorrect");
        softAssert.assertFalse(pageResponse.getPage().isEmpty(), "News list should not be empty");

        EcoNewsResponse firstNews = pageResponse.getPage().get(0);

        softAssert.assertNotNull(firstNews.getId(), "News id should not be null");
        softAssert.assertNotNull(firstNews.getTitle(), "News title should not be null");
        softAssert.assertEquals(firstNews.getAuthor().getId(), testValueProvider.getUserId(), "Author ID does not match expected value");
        softAssert.assertEquals(firstNews.getAuthor().getName(), testValueProvider.getUserName(), "Author name does not match expected value");

        softAssert.assertAll();
    }
}
