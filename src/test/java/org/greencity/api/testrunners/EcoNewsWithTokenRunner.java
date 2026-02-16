package org.greencity.api.testrunners;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class EcoNewsWithTokenRunner extends ApiTestRunner {
    protected EcoNewsClient ecoNewsClient;
    protected String accessToken;

    @BeforeClass
    public void prepareTokens() {

        String userApiUrl = testValueProvider.getBaseGreencityUserAPIUrl();
        OwnSecurityClient ownSecurityClient = new OwnSecurityClient(userApiUrl);

        Response response = ownSecurityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        Assert.assertEquals(response.getStatusCode(), 200, "Login request failed");

        this.accessToken = response.jsonPath().getString("accessToken");

        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), accessToken);
    }
}
