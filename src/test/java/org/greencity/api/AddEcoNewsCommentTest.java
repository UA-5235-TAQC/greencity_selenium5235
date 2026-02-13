package org.greencity.api;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsCommentClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.ecoNewsComment.AddCommentResponse;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AddEcoNewsCommentTest extends ApiTestRunner{
    private EcoNewsCommentClient ecoNewsClient;

    @BeforeClass
    public void setUpEcoNewsComment() {
        OwnSecurityClient securityClient = new OwnSecurityClient(testValueProvider.getBaseUIGreenCityUrl());
        Response signInResponseRaw = securityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );
        Assert.assertEquals(signInResponseRaw.getStatusCode(), 200, "Login failed during setup");
        String accessToken = signInResponseRaw.as(SignInResponse.class).getAccessToken();
        this.ecoNewsClient = new EcoNewsCommentClient(testValueProvider.getBaseUIGreenCityUrl(), accessToken);
    }

    @Test
    public void addCommentTest() {
        Response response = ecoNewsClient.addComment(1354, "Test comment from API Automation", 0);

        Assert.assertEquals(response.getStatusCode(), 201); // Зазвичай створення - це 201, але перевір в API (може бути 200)

        AddCommentResponse responseBody = response.as(AddCommentResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getText(), "Test comment from API Automation");
        // Додай інші перевірки
        softAssert.assertAll();
    }
}
