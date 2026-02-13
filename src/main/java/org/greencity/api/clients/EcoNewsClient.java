package org.greencity.api.clients;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.greencity.api.models.econews.UpdateEcoNewsDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class EcoNewsClient extends BaseApiClient {

    public EcoNewsClient(String baseUrl) {
        super(baseUrl);
    }

    public EcoNewsClient(String baseUrl, String token) {
        super(baseUrl, token);
    }

    public String getPath(long ecoNewsId) {
        String resourcePath = "/eco-news/";
        return resourcePath + ecoNewsId;
    }

    @Step("GET EcoNews by ID: {ecoNewsId}")
    public Response getEcoNewsById(long ecoNewsId) {
        return get(getPath(ecoNewsId));
    }

    @Step("GET EcoNews by ID: {ecoNewsId} with language: {lang}")
    public Response getEcoNewsByIdWithLang(long ecoNewsId, String lang) {
        return execute(
                prepareRequest()
                        .queryParam("lang", lang)
                        .get(getPath(ecoNewsId))
        );
    }

    @Step("UPDATE EcoNews by ID: {ecoNewsId} with DTO and image: {image}")
    public Response updateEcoNewsById(long ecoNewsId, UpdateEcoNewsDto updateDto, File image) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        RequestSpecification request = RestAssured.given()
                .baseUri(baseApiUrl)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.MULTIPART);

        if (image != null) {
            String mimeType;
            if (image.getName().toLowerCase().endsWith(".png")) {
                mimeType = "image/png";
            } else if (image.getName().toLowerCase().endsWith(".jpg") || image.getName().toLowerCase().endsWith(".jpeg")) {
                mimeType = "image/jpeg";
            } else {
                throw new IllegalArgumentException("Only PNG and JPEG are allowed");
            }
            request.multiPart("image", image, mimeType);
        }

        try {
            File tempJsonFile = File.createTempFile("updateEcoNewsDto", ".json");
            tempJsonFile.deleteOnExit();
            Files.writeString(tempJsonFile.toPath(), mapper.writeValueAsString(updateDto));
            request.multiPart("updateEcoNewsDto", tempJsonFile, "application/json");
        } catch (IOException e) {
            throw new RuntimeException("Failed to prepare JSON file for multipart", e);
        }

        if (image != null) {
            request.multiPart("image", image);
        }

        return request.put(getPath(ecoNewsId));
    }

    @Step("DELETE EcoNews by ID: {ecoNewsId}")
    public Response deleteEcoNewsById(long ecoNewsId) {
        return delete(getPath(ecoNewsId));
    }
}
