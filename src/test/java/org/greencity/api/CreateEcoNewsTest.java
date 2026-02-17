package org.greencity.api;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.ui.enums.EcoNewsTag;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Epic("EcoNews API")
@Feature("Create EcoNews")
@Story("Verify that an authorized user can create a new EcoNews item")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class CreateEcoNewsTest extends ApiTestRunner {
    private String accessToken;
    private EcoNewsClient ecoNewsClient;
    private final List<Integer> newsToDelete = new ArrayList<>();

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

    @AfterClass
    public void deleteCreatedNews() {
        for (Integer id : newsToDelete) {
            ecoNewsClient.deleteEcoNewsById(id);
        }
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
                .build();

        Response response = ecoNewsClient.postEcoNews(requestBody);

        EcoNewsResponse responseBody = response.as(EcoNewsResponse.class);

        if (responseBody.getId() != null) {
            newsToDelete.add(responseBody.getId());
        }

        Assert.assertEquals(response.getStatusCode(), 201, "Eco News was not created!");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(responseBody.getId());
        softAssert.assertEquals(responseBody.getTitle(), expectedTitle);
        softAssert.assertEquals(responseBody.getContent(), expectedText);
        softAssert.assertEquals(responseBody.getAuthor().getId(), testValueProvider.getUserId(),
                "Author ID does not match expected value");
        softAssert.assertEquals(responseBody.getAuthor().getName(), testValueProvider.getUserName(),
                "Author name does not match expected value");

        softAssert.assertAll();
    }

    @Test
    @Description("This test verifies that an authorized user can successfully create a new EcoNews item with image " +
            "and receive a valid response with generated ID and correct data.")
    public void createEcoNewsWithImageTest() {
        String expectedTitle = "Новина про екологію " + System.currentTimeMillis();
        String expectedText = "Це дуже важливий текст новини, який має бути довшим за 20 символів.";
        String imagePath = "src/test/resources/images/test2.png";
        String expectedFileName = new File(imagePath).getName();

        EcoNewsRequest requestBody = EcoNewsRequest.builder()
                .title(expectedTitle)
                .text(expectedText)
                .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
                .source("https://example.com")
                .shortInfo("Короткий опис")
                .image(null)
                .build();

        Response response = ecoNewsClient.postEcoNews(requestBody, imagePath);

        Assert.assertEquals(response.getStatusCode(), 201, "Eco News was not created!");

        EcoNewsResponse responseBody = response.as(EcoNewsResponse.class);

        newsToDelete.add(responseBody.getId());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(responseBody.getId());
        softAssert.assertEquals(responseBody.getTitle(), expectedTitle, "Title are not equal!");
        softAssert.assertEquals(responseBody.getContent(), expectedText,  "Content are not equal!");
        softAssert.assertNotNull(responseBody.getImagePath(), "Image are not posted in response!");
        softAssert.assertTrue(responseBody.getImagePath().endsWith(expectedFileName),
                "Image name mismatch!");
        softAssert.assertEquals(responseBody.getAuthor().getId(), testValueProvider.getUserId(),
                "Author ID does not match expected value");
        softAssert.assertEquals(responseBody.getAuthor().getName(), testValueProvider.getUserName(),
                "Author name does not match expected value");

        softAssert.assertAll();
    }
}
