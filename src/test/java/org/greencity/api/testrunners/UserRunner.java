package org.greencity.api.testrunners;

import io.restassured.response.Response;
import lombok.Getter;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.EcoNewsCommentClient;
import org.greencity.api.clients.OwnSecurityClient;

import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.greencity.utils.api.ApiTestAssertions.assertOk;

public class UserRunner extends ApiTestRunner {
    @Getter
    protected String accessToken;

    @Getter
    protected String refreshToken;

    @Getter
    protected EcoNewsClient ecoNewsClient;

    @Getter
    protected EcoNewsCommentClient ecoNewsCommentClient;

    protected void loginUser(String email, String password) {
        String userApiUrl = testValueProvider.getBaseGreencityUserAPIUrl();
        OwnSecurityClient authClient = new OwnSecurityClient(userApiUrl);

        Response response = authClient.signIn(email, password);
        assertOk(response);

        this.accessToken = response.jsonPath().getString("accessToken");
        this.refreshToken = response.jsonPath().getString("refreshToken");
        this.ecoNewsClient = new EcoNewsClient(
                testValueProvider.getGreencityAPIUrl(), accessToken
        );
        this.ecoNewsCommentClient = new EcoNewsCommentClient(
                testValueProvider.getGreencityAPIUrl(), accessToken
        );
    }

    protected String refreshAccessToken() {
        OwnSecurityClient authClient = new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());
        Response refreshResponse = authClient.refreshToken(this.refreshToken);

        if (refreshResponse.statusCode() == SC_OK) {
            this.accessToken = refreshResponse.jsonPath().getString("accessToken");
            this.refreshToken = refreshResponse.jsonPath().getString("refreshToken");
        } else {
            loginUser(testValueProvider.getUserEmail(), testValueProvider.getUserPassword());
        }

        this.ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), accessToken);
        return this.accessToken;
    }
}
