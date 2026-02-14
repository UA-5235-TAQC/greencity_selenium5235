package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.models.econews.EcoNewsRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        return prepareRequest().queryParams(queryParams).log().all().get(resourcePath).then().extract().response();
    }

    @Step("Post new EcoNews without image")
    public Response postEcoNews(EcoNewsRequest body) {
        return prepareRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("addEcoNewsDtoRequest", body, "application/json; charset=UTF-8")
                .log().all()
                .post(resourcePath)
                .then()
                .extract()
                .response();
    }

    @Step("Post new EcoNews {body} with image: {imagePath}")
    public Response postEcoNews(EcoNewsRequest body, String imagePath) {
        RequestSpecification request = prepareRequest().contentType(ContentType.MULTIPART).multiPart("addEcoNewsDtoRequest", body, "application/json; charset=UTF-8");
        attachFilesToRequest(request, imagePath);
        return request.log().all().post(resourcePath).then().extract().response();
    }

    @Step("Get EcoNews by ID: {id}")
    public Response getEcoNewsById(Integer id) {
        return prepareRequest().get(resourcePath + "/" + id).then().extract().response();
    }

    @Step("Attach files to request: {imagePath}")
    public void attachFilesToRequest(RequestSpecification request, String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            request.multiPart("image", "", "");
            return;
        }
        try {
            File file = new File(imagePath);
            String fileName = file.getName().toLowerCase();
            String mineType = fileName.endsWith(".png") ? "image/png" : "image/jpeg";
            request.multiPart("type", fileName, new FileInputStream(file), mineType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to attach file: " + e.getMessage(), e);
        }
    }

    @Step("Get EcoNews count by author id: {authorId}")
    public Response getEcoNewsCountByAuthorId(int authorId) {
        return prepareRequest()
                .queryParam("author-id", authorId)
                .get(resourcePath + "/count")
                .then()
                .extract()
                .response();
    }
}
