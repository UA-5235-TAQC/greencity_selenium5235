package org.greencity.api.clients;

import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsRequest;

import java.util.Map;

public class EcoNewsClient extends BaseApiClient {
    protected final String resourcePath = "/eco-news";

    public EcoNewsClient(String baseUrl) {
        super(baseUrl);
    }

    public EcoNewsClient(String baseApiUrl, String token) {
        super(baseApiUrl, token);
    }

    public Response getEcoNews(Map<String, ?> queryParams) {
        return prepareRequest()
                .queryParams(queryParams)
                .log()
                .all()
                .get(resourcePath)
                .then()
                .extract()
                .response();
    }

    public Response postEcoNews(EcoNewsRequest body) {
        return prepareRequest()
                .contentType("multipart/form-data")
                .multiPart("addEcoNewsDtoRequest", body, "application/json")
                .log().all()
                .post(resourcePath)
                .then()
                .extract()
                .response();
    }

    public Response getEcoNewsById(Integer id) {
        return prepareRequest()
                .get(resourcePath + "/" + id)
                .then()
                .extract()
                .response();
    }
}
