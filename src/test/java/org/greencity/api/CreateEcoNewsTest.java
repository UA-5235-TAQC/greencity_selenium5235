package org.greencity.api;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.testrunners.FirstUserRunner;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.utils.ui.NewsTestData;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.greencity.utils.api.ApiTestAssertions.assertCreated;

@Epic("EcoNews API")
@Feature("Create EcoNews")
@Story("Verify that an authorized user can create a new EcoNews item")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class CreateEcoNewsTest extends FirstUserRunner {

    private final List<Long> newsToDelete = new ArrayList<>();
    String EXPECTED_TITLE = NewsTestData.TEST_TITLE_EN;
    String EXPECTED_TEXT = NewsTestData.TEST_CONTENT_EN;
    String EXPECTED_SOURCE = NewsTestData.TEST_SOURCE;
    String SHORT_DESCRIPTION = "Short description";

    @AfterClass
    public void deleteCreatedNews() {
        for (Long id : newsToDelete) {
            ecoNewsClient.deleteEcoNewsById(id);
        }
    }

    @Test
    @Description("This test verifies that an authorized user can successfully create a new EcoNews item " +
            "and receive a valid response with generated ID and correct data.")
    public void createEcoNewsSuccessTest() {

        EcoNewsRequest requestBody = Allure.step("Prepare request body with title: " + EXPECTED_TITLE, () ->
                EcoNewsRequest.builder()
                        .title(EXPECTED_TITLE)
                        .text(EXPECTED_TEXT)
                        .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
                        .source(EXPECTED_SOURCE)
                        .shortInfo(SHORT_DESCRIPTION)
                        .build()
        );

        Response response = Allure.step("Send POST request to create news", () ->
                ecoNewsClient.postEcoNews(requestBody)
        );

        EcoNewsResponse responseBody = Allure.step("Parse response body", () ->
                response.as(EcoNewsResponse.class)
        );

        if (responseBody.getId() != null) {
            newsToDelete.add(responseBody.getId());
        }

        Allure.step("Verify that status code is 201 Created", () ->
                assertCreated(response)
        );

        Allure.step("Verify response data fields", () -> {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(responseBody.getId(), "EcoNews ID should not be null");
            softAssert.assertEquals(responseBody.getTitle(), EXPECTED_TITLE,
                    "Response title should match the expected title");
            softAssert.assertEquals(responseBody.getContent(), EXPECTED_TEXT,
                    "Response content should match the expected text");
            softAssert.assertEquals(responseBody.getAuthor().getId(), testValueProvider.getUserId(),
                    "Author ID does not match the current user ID");
            softAssert.assertEquals(responseBody.getAuthor().getName(), testValueProvider.getUserName(),
                    "Author name does not match the current user name");
            softAssert.assertAll();
        });
    }

    @Test
    @Description("This test verifies that an authorized user can successfully create a new EcoNews item with image " +
            "and receive a valid response with generated ID and correct data.")
    public void createEcoNewsWithImageTest() {
        String imagePath = "src/test/resources/images/test2.png";
        String expectedFileName = new File(imagePath).getName();

        EcoNewsRequest requestBody = Allure.step("Prepare request body for news with image", () ->
                EcoNewsRequest.builder()
                        .title(EXPECTED_TITLE)
                        .text(EXPECTED_TEXT)
                        .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
                        .source(EXPECTED_SOURCE)
                        .shortInfo(SHORT_DESCRIPTION)
                        .build()
        );

        Response response = Allure.step("Send POST request with image: " + expectedFileName, () ->
                ecoNewsClient.postEcoNews(requestBody, imagePath)
        );

        Allure.step("Verify that status code is 201 Created", () ->
                assertCreated(response)
        );

        EcoNewsResponse responseBody = Allure.step("Parse response body", () ->
                response.as(EcoNewsResponse.class)
        );

        if (responseBody.getId() != null) {
            newsToDelete.add(responseBody.getId());
        }

        Allure.step("Verify response data fields and image presence", () -> {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(responseBody.getId(), "EcoNews ID should not be null");
            softAssert.assertEquals(responseBody.getTitle(), EXPECTED_TITLE,
                    "Response title should match the expected title");
            softAssert.assertEquals(responseBody.getContent(), EXPECTED_TEXT,
                    "Response content should match the expected text");
            softAssert.assertNotNull(responseBody.getImagePath(),
                    "Image path should be present in response");
            softAssert.assertTrue(responseBody.getImagePath().endsWith(expectedFileName),
                    String.format("Image path '%s' should end with expected file name '%s'",
                            responseBody.getImagePath(), expectedFileName));
            softAssert.assertEquals(responseBody.getAuthor().getId(), testValueProvider.getUserId(),
                    "Author ID does not match the current user ID");
            softAssert.assertEquals(responseBody.getAuthor().getName(), testValueProvider.getUserName(),
                    "Author name does not match the current user name");
            softAssert.assertAll();
        });
    }
}