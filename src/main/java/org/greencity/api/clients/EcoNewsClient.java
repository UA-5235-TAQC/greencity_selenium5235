package org.greencity.api.clients;

import io.restassured.response.Response;

public class EcoNewsClient extends BaseApiClient {

    private final String resourcePath = "/eco-news";

    public EcoNewsClient(String baseUrl) {
        super(baseUrl);
    }

    public EcoNewsClient(String baseUrl, String token) {
        super(baseUrl, token);
    }

    public Response getTags(String lang) {
        return prepareRequest()
                .queryParam("lang", lang)
                .get(resourcePath + "/tags")
                .then()
                .extract()
                .response();
    }
}
