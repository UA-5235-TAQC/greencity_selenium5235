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

    @BeforeClass
    public void setUpFavoritesClient() {
        // creating OwnSecurityClient and signing in
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
        long ecoNewsId = 1375;

        Response addResponse = favoritesClient.addToFavorites(ecoNewsId);
        Assert.assertEquals(addResponse.getStatusCode(), 200);

        Response removeResponse = favoritesClient.removeFromFavorites(ecoNewsId);
        Assert.assertEquals(removeResponse.getStatusCode(), 200);
    }
}
