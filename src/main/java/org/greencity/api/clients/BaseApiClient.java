package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

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
//                .log().all()
                .baseUri(baseApiUrl).contentType(contentType);
        if (token != null) {
            request.header("Authorization", "Bearer " + token);
        }
        return request;
    }

    @Step("Attach files to request: {imagePath}")
    protected void attachFilesToRequest(RequestSpecification request, String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return;
        }
        try {
            File file = new File(imagePath);
            if (!file.exists()) {
                throw new RuntimeException("File not found: " + imagePath);
            }
            String fileName = file.getName();
            String mimeType = fileName.endsWith(".png") ? "image/png" : "image/jpeg";
            request.multiPart("image", file, mimeType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to attach file: " + e.getMessage(), e);
        }
    }

    protected Response execute(Response response) {
        return response
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    @Step("DELETE request to {path}")
    protected Response delete(String path) {
        return execute(
                prepareRequest()
                        .delete(path)
        );
    }

}
