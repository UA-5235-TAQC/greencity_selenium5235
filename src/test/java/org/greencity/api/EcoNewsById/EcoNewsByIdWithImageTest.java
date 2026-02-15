package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.utils.api.EcoNewsAssertions;
import org.greencity.utils.api.EcoNewsDtoFactory;
import org.greencity.utils.api.ValidationErrorResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

import static org.greencity.utils.api.EcoNewsDtoFactory.*;

@Epic("EcoNews API")
@Feature("CRUD operations with created news with image")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsByIdWithImageTest extends CreateNewsRunner {

    @BeforeClass
    @Description("Set image path before running tests")
    public void setupImage() {
        setImagePath("src/test/resources/images/test2.png");
    }

    @Test
    @Story("Get EcoNews by ID")
    @Description("Verify that EcoNews can be successfully retrieved by ID.")
    public void getEcoNewsByIdTest() {
        Response response = ecoNewsClient.getEcoNewsById(ecoNewsId);
        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected status code 200");

        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        LocalDate creationDate = createdNews.getCreationDate().toLocalDate();

        EcoNewsAssertions.assertEcoNewsResponse(
                ecoNews,
                createdNews.getId(),
                createdNews.getTitle(),
                createdNews.getContent(),
                createdNews.getShortInfo(),
                creationDate,
                createdNews.getTagsEn(),
                createdNews.getTagsUk(),
                createdNews.getAuthor().getId(),
                createdNews.getAuthor().getName(),
                true,
                true
        );
    }

    @Test
    @Story("Get EcoNews in English and Ukrainian")
    @Description("Verify that EcoNews can be retrieved in English and Ukrainian using lang parameter.")
    public void getEcoNewsLangTest() {
        Response response = ecoNewsClient.getEcoNewsByIdWithLang(ecoNewsId, "en");
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200 with lang parameter");
        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        Assert.assertNotNull(ecoNews.getTitle(), "Title in English should not be null");

        response = ecoNewsClient.getEcoNewsByIdWithLang(ecoNewsId, "uk");
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200 with lang parameter");
        ecoNews = response.as(EcoNewsResponse.class);
        Assert.assertNotNull(ecoNews.getTitle(), "Title in Ukrainian should not be null");
    }

    @Test
    @Story("Get non-existing EcoNews")
    @Description("Verify that requesting a non-existing EcoNews returns 404 status code.")
    public void getNonExistingEcoNewsTestShouldReturn404() {
        long nonExistingEcoNewsId = ecoNewsId + 1;
        Response response = ecoNewsClient.getEcoNewsById(nonExistingEcoNewsId);
        Assert.assertEquals(response.getStatusCode(), 404,
                "Status code should be 404 for non-existing news");
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "Eco new doesn't exist by this id: " + nonExistingEcoNewsId,
                "Error message should match expected");
    }

    @Test
    @Story("Update EcoNews with invalid id, tag, title, content")
    @Description("Verify that updating EcoNews with invalid id, tag, title, content returns 400 Bad Request")
    public void testUpdateEcoNewsByIdShouldReturn400() {
        UpdateEcoNewsDto updateDto = new UpdateEcoNewsDto();
        updateDto.setTags(EcoNewsTag.getEn(TEST_TAGS));
        updateDto.setTitle(TITLE_UK);
        updateDto.setContent(CONTENT_UK);
        updateDto.setId(ecoNewsId + 1);
        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, null);
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected status code 400");
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message,
                "Eco news id in path param and eco news id in entity not equal",
                "Message should match expected");

        updateDto.setId(ecoNewsId);
        updateDto.setTags(List.of("string"));
        response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, null);
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected status code 400");
        message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "There should be at least one valid tag",
                "Message should match expected");

        updateDto.setId(ecoNewsId);
        updateDto.setTags(EcoNewsTag.getEn(TEST_TAGS));
        updateDto.setTitle("");
        response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, null);
        List<ValidationErrorResponse> errors =
                response.jsonPath().getList("", ValidationErrorResponse.class);
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected status code 400");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(
                errors.stream().anyMatch(e ->
                        e.getMessage().equals("must not be empty")),
                "Error 'must not be empty' was not returned"
        );
        softAssert.assertTrue(
                errors.stream().anyMatch(e ->
                        e.getMessage().equals("size must be between 1 and 170")),
                "Error 'size must be between 1 and 170' was not returned"
        );
        softAssert.assertAll();

        updateDto.setId(ecoNewsId);
        updateDto.setTags(EcoNewsTag.getEn(TEST_TAGS));
        updateDto.setTitle(TITLE_UK);
        updateDto.setContent("");
        response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, null);
        errors = response.jsonPath().getList("", ValidationErrorResponse.class);
        Assert.assertEquals(response.getStatusCode(), 400);
        softAssert = new SoftAssert();
        softAssert.assertTrue(
                errors.stream().anyMatch(e ->
                        e.getMessage().equals("must not be empty")),
                "Error 'must not be empty' was not returned"
        );
        softAssert.assertTrue(
                errors.stream().anyMatch(e ->
                        e.getMessage().equals("size must be between 20 and 63206")),
                "Error 'size must be between 20 and 63206' was not returned"
        );
        softAssert.assertAll();
    }

    @Test
    @Story("Update EcoNews by ID with image")
    @Description("Verify that updating EcoNews with a specific image is successful")
    public void testUpdateEcoNewsByIdWithImage() {
        EcoNewsDtoFactory dtoFactory = new EcoNewsDtoFactory(ecoNewsId);
        UpdateEcoNewsDto updateDto = dtoFactory.updateDtoUa();
        String imagePath = "src/test/resources/images/test.jfif";

        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, imagePath);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);

        EcoNewsAssertions.assertEcoNewsResponse(
                ecoNews,
                updateDto.getId(),
                updateDto.getTitle(),
                updateDto.getContent(),
                updateDto.getShortInfo(),
                null,
                updateDto.getTagsEn(),
                updateDto.getTagsUk(),
                null,
                null,
                true,
                false
        );
    }
}
