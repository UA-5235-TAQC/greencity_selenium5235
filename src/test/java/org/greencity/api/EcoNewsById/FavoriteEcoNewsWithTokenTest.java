package org.greencity.api.EcoNewsById;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.greencity.api.ApiTestRunner;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.greencity.utils.NewsTestData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Add News to Favorites with a token")
@Severity(SeverityLevel.CRITICAL)
public class FavoriteEcoNewsWithTokenTest extends ApiTestRunner {

    private EcoNewsClient ecoNewsClientWithToken;

    @BeforeClass
    public void setUpClientWithToken() {

        // Sign in and create authorized client
        OwnSecurityClient securityClient = new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());
        Response loginResponse = securityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        String token = loginResponse.as(SignInResponse.class).getAccessToken();

        ecoNewsClientWithToken = new EcoNewsClient(
                testValueProvider.getGreencityAPIUrl(),
                token
        );
    }

    @Test
    @Description("Add EcoNews to favorites and then remove it (authorized)")
    public void addAndRemoveEcoNewsFavorites_authorized() {
        // cleanup before test
        ecoNewsClientWithToken.removeFromFavorites(NewsTestData.ECO_NEWS_ID);

        Response addResp = ecoNewsClientWithToken.addToFavorites(NewsTestData.ECO_NEWS_ID);
        Assert.assertEquals(addResp.getStatusCode(), 200);

        Response removeResp = ecoNewsClientWithToken.removeFromFavorites(NewsTestData.ECO_NEWS_ID);
        Assert.assertEquals(removeResp.getStatusCode(), 200);
    }

    @Test
    @Description("Add the same EcoNews twice - should return 400 error")
    public void addToFavoritesTwiceShouldReturnError400() {
        Response first = ecoNewsClientWithToken.addToFavorites(NewsTestData.ECO_NEWS_ID);
        if (first.getStatusCode() == 400) {
            ecoNewsClientWithToken.removeFromFavorites(NewsTestData.ECO_NEWS_ID);
            first = ecoNewsClientWithToken.addToFavorites(NewsTestData.ECO_NEWS_ID);
        }
        Assert.assertEquals(first.getStatusCode(), 200);
        Response second = ecoNewsClientWithToken.addToFavorites(NewsTestData.ECO_NEWS_ID);
        Assert.assertEquals(second.getStatusCode(), 400);
        String message = second.jsonPath().getString("message");
        Assert.assertEquals(message,
                "User has already added this eco new to favorites.");
    }
}
