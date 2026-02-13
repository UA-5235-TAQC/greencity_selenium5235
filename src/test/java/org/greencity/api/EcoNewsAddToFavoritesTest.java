package org.greencity.api;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsFavoritesClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EcoNewsAddToFavoritesTest extends ApiTestRunner {

    private EcoNewsFavoritesClient favoritesClient;
    private static final int SELECTED_NEWS_ID = 1375;

    @BeforeClass
    public void setUpFavoritesClient() {
        // Signing in
        OwnSecurityClient securityClient = new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());
        Response loginResponse = securityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        String token = loginResponse.as(SignInResponse.class).getAccessToken();

        // creating EcoNewsFavoritesClient with a token
        favoritesClient = new EcoNewsFavoritesClient(
                testValueProvider.getGreencityAPIUrl(),
                token
        );
    }

    @Test
    public void addAndRemoveEcoNewsFavoritesTest() {

        Response addNewsResponse = favoritesClient.addToFavorites(SELECTED_NEWS_ID);
        Assert.assertEquals(addNewsResponse.getStatusCode(), 200);

        Response removeNewsResponse = favoritesClient.removeFromFavorites(SELECTED_NEWS_ID);
        Assert.assertEquals(removeNewsResponse.getStatusCode(), 200);

    }
}
