package org.greencity.api;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.EcoNewsCommentClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.ecoNewsComment.AddCommentResponse;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.greencity.ui.enums.EcoNewsTag;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class AddEcoNewsCommentTest extends ApiTestRunner{
    private EcoNewsCommentClient ecoNewsCommentClient;
    private EcoNewsClient ecoNewsClient;
    EcoNewsRequest newsRequestBody = EcoNewsRequest.builder()
            .title("News title for testing api comment controller")
            .text("News text for testing api comment controller. Should be more than 20 character, or no, I don't remember.")
            .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
            .source("https://example.com")
            .shortInfo("Short info")
            .image(null)
            .build();
    private int newsId;

    @BeforeClass
    public void setUpEcoNewsComment() {
        OwnSecurityClient securityClient = new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());
        Response signInResponseRaw = securityClient.signIn(testValueProvider.getUserEmail(), testValueProvider.getUserPassword());
        Assert.assertEquals(signInResponseRaw.getStatusCode(), 200, "Login failed during setup");
        String accessToken = signInResponseRaw.as(SignInResponse.class).getAccessToken();
        this.ecoNewsCommentClient = new EcoNewsCommentClient(testValueProvider.getGreencityAPIUrl(), accessToken);
        this.ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), accessToken);
        Response response = ecoNewsClient.postEcoNews(newsRequestBody);
        Assert.assertEquals(response.getStatusCode(), 201, "Eco News was not created!");
        this.newsId = response.as(EcoNewsResponse.class).getId();
    }

    @Test
    public void addCommentTest() {
        Response response = ecoNewsCommentClient.addComment(newsId, "Test comment from API Automation", 0);
        Assert.assertEquals(response.getStatusCode(), 201);

        AddCommentResponse responseBody = response.as(AddCommentResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getText(), "Test comment from API Automation");
        // Додай інші перевірки
        softAssert.assertAll();
    }
}
