package org.greencity.api.clients;

import io.restassured.response.Response;

public class EcoNewsFavoritesClient extends BaseApiClient {

    private final String resourcePath = "/eco-news";

    public EcoNewsFavoritesClient(String baseUrl, String token) {
        super(baseUrl, token);
    }

    // add to favorites
    public Response addToFavorites(int ecoNewsId) {
        return prepareRequest()
                .post(resourcePath + "/" + ecoNewsId + "/favorites")
                .then()
                .extract()
                .response();
    }

    // delete from favorites
    public Response removeFromFavorites(int ecoNewsId) {
        return prepareRequest()
                .delete(resourcePath + "/" + ecoNewsId + "/favorites")
                .then()
                .extract()
                .response();
    }
}
