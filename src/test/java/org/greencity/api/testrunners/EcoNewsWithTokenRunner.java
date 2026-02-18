package org.greencity.api.testrunners;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.testng.annotations.BeforeClass;

import static org.greencity.utils.api.ApiTestAssertions.assertOk;

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

        assertOk(response);
        this.accessToken = response.jsonPath().getString("accessToken");

        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), accessToken);
    }
}
