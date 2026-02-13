package org.greencity.api.testrunners;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.testng.annotations.BeforeClass;

public class EcoNewsWithTokenRunner extends ApiTestRunner {
    protected EcoNewsClient ecoNewsClient;

    @BeforeClass
    public void setUpEcoNewsClient() {
        OwnSecurityClient ownSecurityClient = new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());
        Response signInResponse = ownSecurityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );
        SignInResponse parsedResponse = signInResponse.as(SignInResponse.class);
        String token = parsedResponse.getAccessToken();
        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), token);
    }
}
