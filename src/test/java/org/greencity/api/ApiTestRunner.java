package org.greencity.api;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.utils.TestValueProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.util.List;

public class ApiTestRunner {

    protected static TestValueProvider testValueProvider;


    @BeforeSuite
    public void setUp() {
        testValueProvider = new TestValueProvider();
        RestAssured.registerParser("application/json", Parser.JSON);
        RestAssured.config = RestAssuredConfig.config()
                .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
    }

    // Log in and return an authorized EcoNews client
    protected EcoNewsClient getAuthorizedEcoNewsClient() {
        OwnSecurityClient securityClient = new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());

        Response loginResponse = securityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        Assert.assertEquals(loginResponse.getStatusCode(), 200);

        String token = loginResponse.as(SignInResponse.class).getAccessToken();
        Assert.assertNotNull(token);

        return new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), token);
    }

    // Create a test EcoNews and return its ID
    protected long createTestEcoNews(EcoNewsClient client) {
        EcoNewsRequest request = EcoNewsRequest.builder()
                .title("Test " + System.currentTimeMillis())
                .text("This is test content longer than twenty characters.")
                .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
                .source("https://example.com")
                .shortInfo("Short info")
                .image(null)
                .build();

        Response response = client.postEcoNews(request);
        Assert.assertEquals(response.getStatusCode(), 201);

        return response.as(EcoNewsResponse.class).getId();
    }

    // Delete EcoNews by ID and check the response status
    protected void deleteEcoNewsAndAssert(EcoNewsClient client, long ecoNewsId) {
        Response response = client.deleteEcoNewsById(ecoNewsId);
        int status = response.getStatusCode();
        Assert.assertTrue(
                status == 200 || status == 204,
                "Delete eco-news failed. Status: " + status
        );
    }

}
