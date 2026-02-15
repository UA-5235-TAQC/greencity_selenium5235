package org.greencity.api.EcoNewsById;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.greencity.api.ApiTestRunner;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.utils.NewsTestData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Add News to Favorites without token")
@Severity(SeverityLevel.CRITICAL)
public class FavoriteEcoNewsWithoutTokenTest extends ApiTestRunner {

    private EcoNewsClient ecoNewsClientWithoutToken;

    @BeforeClass
    public void setUpClientWithoutToken() {
        ecoNewsClientWithoutToken =
                new EcoNewsClient(testValueProvider.getGreencityAPIUrl());
    }

    @Test
    @Description("Attempt to add EcoNews to favorites without token - should return 401")
    public void addToFavorites_unauthorized_shouldReturn401() {
        Response resp =
                ecoNewsClientWithoutToken.addToFavorites(NewsTestData.ECO_NEWS_ID);
        Assert.assertEquals(resp.getStatusCode(), 401);
    }

    @Test
    @Description("Attempt to remove EcoNews from favorites without token - should return 401")
    public void removeFromFavorites_unauthorized_shouldReturn401() {
        Response resp =
                ecoNewsClientWithoutToken.removeFromFavorites(NewsTestData.ECO_NEWS_ID);
        Assert.assertEquals(resp.getStatusCode(), 401);
    }
}
