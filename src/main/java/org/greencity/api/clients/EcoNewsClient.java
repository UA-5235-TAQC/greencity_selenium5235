package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.greencity.api.models.econews.EcoNewsQuery;
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

    public String getPath(long ecoNewsId) {
        String resourcePath = "/eco-news/";
        return resourcePath + ecoNewsId;
    }

    @Deprecated
    @Step("Get EcoNews with query parameters: {queryParams}")
    public Response getEcoNews(Map<String, ?> queryParams) {
        return prepareRequest().queryParams(queryParams).get(resourcePath).then().extract().response();
    }

    @Step("Post new EcoNews without image")
    public Response postEcoNews(EcoNewsRequest body) {
        return prepareRequest().contentType(ContentType.MULTIPART).multiPart("addEcoNewsDtoRequest", body, "application/json; charset=UTF-8").post(resourcePath).then().extract().response();
    }

    @Step("Post new EcoNews {body} with image: {imagePath}")
    public Response postEcoNews(EcoNewsRequest body, String imagePath) {
        RequestSpecification request = prepareRequest().contentType(ContentType.MULTIPART).multiPart("addEcoNewsDtoRequest", body, "application/json; charset=UTF-8");
        attachFilesToRequest(request, imagePath);
        return request.post(resourcePath).then().extract().response();
    }

    @Step("Get EcoNews by ID: {id}")
    public Response getEcoNewsById(Integer id) {
        return prepareRequest().get(resourcePath + "/" + id).then().extract().response();
    }

    @Step("Delete EcoNews by ID: {ecoNewsId}")
    public Response deleteEcoNewsById(long ecoNewsId) {
        return delete(getPath(ecoNewsId));
    }


    @Step("Get EcoNews with typed query parameters: {query}")
    public Response getEcoNews(EcoNewsQuery query) {
        RequestSpecification request = prepareRequest();

        if (query.getAuthorId() != null) request.queryParam("author-id", query.getAuthorId());
        if (query.getFavorite() != null) request.queryParam("favorite", query.getFavorite());
        if (query.getPage() != null) request.queryParam("page", query.getPage());
        if (query.getSize() != null) request.queryParam("size", query.getSize());

        return request.get(resourcePath).then().extract().response();
    }
}
