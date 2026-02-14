package org.greencity.api;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.ui.enums.EcoNewsTag;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

@Epic("EcoNews API")
@Feature("Create EcoNews")
@Story("Verify that an authorized user can create a new EcoNews item")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class CreateEcoNewsTest extends ApiTestRunner {
    private String accessToken;
    private EcoNewsClient ecoNewsClient;

    @BeforeClass
    public void prepareTokens() {

        String userApiUrl = testValueProvider.getBaseGreencityUserAPIUrl();
        OwnSecurityClient ownSecurityClient = new OwnSecurityClient(userApiUrl);

        Response response = ownSecurityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        Assert.assertEquals(response.getStatusCode(), 200, "Login request failed");

        accessToken = response.jsonPath().getString("accessToken");

        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), accessToken);
    }

    @Test
    @Description("This test verifies that an authorized user can successfully create a new EcoNews item " +
            "and receive a valid response with generated ID and correct data.")
    public void createEcoNewsSuccessTest() {

        String expectedTitle = "Новина про екологію " + System.currentTimeMillis();
        String expectedText = "Це дуже важливий текст новини, який має бути довшим за 20 символів.";

        EcoNewsRequest requestBody = EcoNewsRequest.builder()
                .title(expectedTitle)
                .text(expectedText)
                .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
                .source("https://example.com")
                .shortInfo("Короткий опис")
                .image(null)
                .build();

        Response response = ecoNewsClient.postEcoNews(requestBody);

        Assert.assertEquals(response.getStatusCode(), 201, "Eco News was not created!");

        Integer id = response.jsonPath().get("id");
        String title = response.jsonPath().getString("title");
        String text = response.jsonPath().getString("content");


        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(id, "ID should not be null");
        softAssert.assertEquals(title, expectedTitle, "Title mismatch!");
        softAssert.assertEquals(text, expectedText, "Content mismatch!");
        softAssert.assertAll();
    }
}
