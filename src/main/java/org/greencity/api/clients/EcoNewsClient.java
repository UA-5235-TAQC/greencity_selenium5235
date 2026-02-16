package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
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

    @Step("Get EcoNews by ID: {ecoNewsId}")
    public Response getEcoNewsById(long ecoNewsId) {
        return get(getPath(ecoNewsId));
    }

    @Step("Get EcoNews by ID: {ecoNewsId} with language: {lang}")
    public Response getEcoNewsByIdWithLang(long ecoNewsId, String lang) {
        return execute(
                prepareRequest()
                        .queryParam("lang", lang)
                        .get(getPath(ecoNewsId))
        );
    }

    @Step("Update EcoNews by ID: {ecoNewsId} without image")
    public Response updateEcoNewsById(long ecoNewsId,
                                      UpdateEcoNewsDto updateDto) {
        return updateEcoNewsById(ecoNewsId, updateDto, null);
    }

    @Step("Update EcoNews by ID: {ecoNewsId} with image")
    public Response updateEcoNewsById(long ecoNewsId,
                                      UpdateEcoNewsDto updateDto,
                                      String imagePath) {

        RequestSpecification request = prepareMultipartRequest(updateDto);

        if (imagePath != null) {
            attachFilesToRequest(request, imagePath);
        }

        return execute(request.put(getPath(ecoNewsId)));
    }

    @Step("Delete EcoNews by ID: {ecoNewsId}")
    public Response deleteEcoNewsById(long ecoNewsId) {
        return delete(getPath(ecoNewsId));
    }

    @Step("Get EcoNews with query parameters: {queryParams}")
    public Response getEcoNews(Map<String, ?> queryParams) {
        return execute(prepareRequest()
                .queryParams(queryParams)
                .get(resourcePath));
    }

    @Step("Post new EcoNews without image")
    public Response postEcoNews(EcoNewsRequest body) {
        return execute(prepareRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("addEcoNewsDtoRequest", body, "application/json; charset=UTF-8")
                .post(resourcePath));
    }

    @Step("Post new EcoNews {body} with image: {imagePath}")
    public Response postEcoNews(EcoNewsRequest body, String imagePath) {
        RequestSpecification request = prepareRequest().contentType(ContentType.MULTIPART).multiPart("addEcoNewsDtoRequest", body, "application/json; charset=UTF-8");
        attachFilesToRequest(request, imagePath);
        return execute(request.post(resourcePath));
    }

    @Step("Like or remove like from EcoNews by ID: {ecoNewsId}")
    public Response likeEcoNewsById(long ecoNewsId) {
        return prepareRequest().log().ifValidationFails().post(getPath(ecoNewsId) + "/likes").then().extract().response();
    }

    @Step("Count likes on EcoNews by ID: {ecoNewsId}")
    public Response countEcoNewsLikes(long ecoNewsId) {
        return prepareRequest().log().ifValidationFails().get(getPath(ecoNewsId) + "/likes/count").then().extract().response();
    }
}
