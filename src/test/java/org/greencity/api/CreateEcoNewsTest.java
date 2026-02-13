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
        // Use the existing OwnSecurityClient
        String userApiUrl = testValueProvider.getBaseGreencityUserAPIUrl();
        OwnSecurityClient ownSecurityClient = new OwnSecurityClient(userApiUrl);

        // Send login request
        Response response = ownSecurityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        Assert.assertEquals(response.getStatusCode(), 200, "Login request failed");

        // Extract access token
        accessToken = response.jsonPath().getString("accessToken");

        // Initialize EcoNews client with authorization token
        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), accessToken);
    }

    @Test
    @Description("This test verifies that an authorized user can successfully create a new EcoNews item " +
            "and receive a valid response with generated ID and correct data.")
    @Step("Create a new EcoNews item and validate response")
    public void createEcoNewsSuccessTest() {
        // Create request body
        EcoNewsRequest requestBody = EcoNewsRequest.builder()
                .title("Eco news test title")
                .text("This is a very important news text that must be longer than 20 characters.")
                .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
                .source("https://example.com")
                .shortInfo("Short description")
                .image(null)
                .build();

        Response response = ecoNewsClient.postEcoNews(requestBody);

        Assert.assertEquals(response.getStatusCode(), 201, "Eco News was not created successfully");

        Integer id = response.jsonPath().get("id");
        String title = response.jsonPath().getString("title");
        String text = response.jsonPath().getString("text");

        Assert.assertNotNull(id, "Response should contain a generated news ID");
    }
}
