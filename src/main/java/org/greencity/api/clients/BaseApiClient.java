package org.greencity.api.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;
import org.greencity.api.models.econews.UpdateEcoNewsDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public abstract class BaseApiClient {
    protected final String baseApiUrl;

    @Getter
    @Setter
    protected RequestSpecification requestSpecification;

    @Setter
    protected ContentType contentType;

    @Getter
    @Setter
    protected String token;

    public BaseApiClient(String baseUrl) {
        this.baseApiUrl = baseUrl;
        this.contentType = ContentType.JSON;
    }

    public BaseApiClient(String baseApiUrl, String token) {
        this.baseApiUrl = baseApiUrl;
        this.contentType = ContentType.JSON;
        this.token = token;
    }

    protected RequestSpecification prepareRequest() {
        RequestSpecification request = io.restassured.RestAssured.given()
                .baseUri(baseApiUrl).contentType(contentType);
        if (token != null) {
            request.header("Authorization", "Bearer " + token);
        }
        return request;
    }

    protected RequestSpecification prepareMultipartRequest(UpdateEcoNewsDto updateDto) {
        return prepareRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("updateEcoNewsDto",
                        updateDto,
                        "application/json; charset=UTF-8");
    }

    protected Response execute(Response response) {
        return response
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    @Step("GET request to {path}")
    protected Response get(String path) {
        return execute(
                prepareRequest()
                        .get(path)
        );
    }

    @Step("DELETE request to {path}")
    protected Response delete(String path) {
        return execute(
                prepareRequest()
                        .delete(path)
        );
    }

    @Step("Attach files to request: {imagePath}")
    protected void attachFilesToRequest(RequestSpecification request, String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return;
        }
        try {
            File file = new File(imagePath);
            String fileName = file.getName();
            String mimeType = fileName.toLowerCase().endsWith(".png") ? "image/png" : "image/jpeg";
            request.multiPart("image", fileName, new FileInputStream(file), mimeType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to attach file: " + e.getMessage(), e);
        }
    }

    @Step("Serializing an object: {object}")
    protected String serialize(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object to JSON string", e);
        }
    }

    @Step("Attach several files to request: {imagePaths}")
    protected void attachImagesToMultipart(RequestSpecification request, String controlName, String... imagePaths) {
        if (imagePaths == null || imagePaths.length == 0) {
            request.multiPart(controlName, "");
            return;
        }

        for (String path : imagePaths) {
            if (path != null && !path.isEmpty()) {
                try {
                    File file = new File(path);
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    String fileName = file.getName();
                    String mimeType = fileName.toLowerCase().endsWith(".png") ? "image/png" : "image/jpeg";

                    request.multiPart(controlName, fileName, fileContent, mimeType);
                } catch (IOException e) {
                    throw new RuntimeException("Can't read file " + path, e);
                }
            }
        }
    }
}