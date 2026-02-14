package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.utils.api.EcoNewsDtoFactory;
import org.greencity.utils.api.ValidationErrorResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

import static org.greencity.utils.api.EcoNewsDtoFactory.TEST_TAGS;
import static org.greencity.utils.api.EcoNewsTexts.*;

@Epic("EcoNews API")
@Feature("EcoNews CRUD without authorization")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsByIdWithImageTest extends CreateNewsRunner {

    @BeforeClass
    public void setupImage() {
        setImagePath("src/test/resources/images/test2.png");
    }

    private void assertEcoNewsResponse(EcoNewsResponse ecoNews,
                                       long expectedId,
                                       String expectedTitle,
                                       String expectedContent,
                                       String expectedShortInfo,
                                       LocalDate expectedDate,
                                       List<String> expectedTagsEn,
                                       List<String> expectedTagsUk) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(ecoNews.getId(), expectedId, "EcoNews ID should match");
        softAssert.assertEquals(ecoNews.getTitle(), expectedTitle, "Title should match expected");
        softAssert.assertEquals(ecoNews.getContent(), expectedContent, "Content should match expected");
        softAssert.assertEquals(ecoNews.getShortInfo(), expectedShortInfo, "ShortInfo should match expected");
        softAssert.assertEquals(ecoNews.getCreationDate().toLocalDate(), expectedDate, "Creation date should match expected");
        softAssert.assertNotNull(ecoNews.getImagePath(), "Image path should not be null");
        softAssert.assertNotNull(ecoNews.getAuthor(), "Author should not be null");
        softAssert.assertEquals(ecoNews.getAuthor().getId(), 149, "Author ID should match expected");
        softAssert.assertEquals(ecoNews.getAuthor().getName(), "NameForTest611", "Author name should match expected");
        softAssert.assertEquals(ecoNews.getLikes(), 0, "Likes should be 0");
        softAssert.assertEquals(ecoNews.getDislikes(), 0, "Dislikes should be 0");
        softAssert.assertEquals(ecoNews.getCountComments(), 0, "Count of comments should be 0");
        softAssert.assertFalse(ecoNews.isHidden(), "Hidden should be false");

        softAssert.assertNotNull(ecoNews.getTagsEn(), "Tags in English should not be null");
        softAssert.assertEquals(ecoNews.getTagsEn(), expectedTagsEn, "Tags in English should match expected");
        softAssert.assertNotNull(ecoNews.getTagsUk(), "Tags in Ukrainian should not be null");
        softAssert.assertEquals(ecoNews.getTagsUk(), expectedTagsUk, "Tags in Ukrainian should match expected");

        softAssert.assertAll();
    }

    @Test
    @Story("Get EcoNews by ID")
    @Description("Verify that EcoNews can be successfully retrieved by ID.")
    public void getEcoNewsByIdTest() {
        Response response = ecoNewsClient.getEcoNewsById(ecoNewsId);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        LocalDate today = LocalDate.now();

        assertEcoNewsResponse(
                ecoNews,
                ecoNewsId,
                TITLE_EN,
                CONTENT_EN,
                SHORT_INFO_EN,
                today,
                List.of(EcoNewsTag.NEWS.getEn(), EcoNewsTag.EDUCATION.getEn()),
                List.of(EcoNewsTag.NEWS.getUa(), EcoNewsTag.EDUCATION.getUa())
        );
    }

    @Test
    @Story("Get EcoNews in English")
    @Description("Verify that EcoNews can be retrieved in English using lang parameter.")
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
    @Story("Update EcoNews with invalid tag")
    @Description("Verify that updating EcoNews with an invalid tag returns 400 Bad Request")
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
    @Story("Update EcoNews by ID")
    @Description("Verify that updating EcoNews with a specific image is successful")
    public void testUpdateEcoNewsByIdWithImage() {
        EcoNewsDtoFactory dtoFactory = new EcoNewsDtoFactory(ecoNewsId);
        UpdateEcoNewsDto updateDto = dtoFactory.createDefaultDtoUa();
        String imagePath = "src/test/resources/images/test2.png";

        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, imagePath);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        LocalDate today = LocalDate.now();

        assertEcoNewsResponse(
                ecoNews,
                ecoNewsId,
                TITLE_UK,
                CONTENT_UK,
                SHORT_INFO_UK,
                today,
                List.of(EcoNewsTag.NEWS.getEn(), EcoNewsTag.EDUCATION.getEn()),
                List.of(EcoNewsTag.NEWS.getUa(), EcoNewsTag.EDUCATION.getUa())
        );
    }
}
