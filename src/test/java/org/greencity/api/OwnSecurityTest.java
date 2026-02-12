package org.greencity.api;

import io.restassured.response.Response;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class OwnSecurityTest extends ApiTestRunner {
    private OwnSecurityClient client;

    @BeforeClass
    public void setUpOwnSecurity() {
        client = new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());
    }

    @Test
    public void singInTest(){
        Response response = client.signIn(testValueProvider.getUserEmail(), testValueProvider.getUserPassword());
        Assert.assertEquals(response.getStatusCode(), 200);

        SignInResponse signInResponse = response.as(SignInResponse.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(signInResponse.getAccessToken(), "Token should not be null");
        softAssert.assertEquals(signInResponse.getUserId(), testValueProvider.getUserId(), "User ID should match");
        softAssert.assertEquals(signInResponse.getName(), testValueProvider.getUserName(), "Name should match");
        softAssert.assertTrue(signInResponse.isOwnRegistrations(), "Own registrations should be true");
        softAssert.assertAll();
    }
}
