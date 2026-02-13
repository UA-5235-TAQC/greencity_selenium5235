package org.greencity.api.clients;

import io.restassured.response.Response;

public class EcoNewsFavoritesClient extends BaseApiClient {

    private final String resourcePath = "/eco-news";

    public EcoNewsFavoritesClient(String baseUrl, String token) {
        super(baseUrl, token);
    }

    // add to favorites
    public Response addToFavorites(long ecoNewsId) {
        return prepareRequest()
                .post(resourcePath + "/" + ecoNewsId + "/favorites")
                .then()
                .extract()
                .response();
    }

    // delete from favorites
    public Response removeFromFavorites(long ecoNewsId) {
        return prepareRequest()
                .delete(resourcePath + "/" + ecoNewsId + "/favorites")
                .then()
                .extract()
                .response();
    }
}
