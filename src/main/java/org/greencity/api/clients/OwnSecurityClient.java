package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.greencity.api.models.ownsecurity.SignInRequest;

import java.util.Map;

public class OwnSecurityClient extends BaseApiClient{
    protected final String resourcePath = "/ownSecurity";

    public OwnSecurityClient(String baseUrl) {
        super(baseUrl);
    }

    public  OwnSecurityClient(String baseUrl, String token) {
        super(baseUrl, token);
    }

    @Step("Sign in to the own security service with email: {username}")
    public Response signIn(String username, String password){
        return execute(prepareRequest()
                .body(new SignInRequest(username, password))
                .post(this.resourcePath + "/signIn"));
    }

    @Step("Refresh access token using refresh token")
    public Response refreshToken(String refreshToken) {
        return post(
                resourcePath + "/updateAccessToken",
                Map.of("refreshToken", refreshToken)
        );
    }
}
