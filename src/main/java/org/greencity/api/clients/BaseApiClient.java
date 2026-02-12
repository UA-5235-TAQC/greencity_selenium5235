package org.greencity.api.clients;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

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

}
