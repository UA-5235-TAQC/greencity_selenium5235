package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.greencity.utils.api.EcoNewsDtoFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Epic("EcoNews API")
@Feature("EcoNews CRUD without authorization")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsByIdWithImageTest extends CreateNewsRunner {

    public EcoNewsByIdWithImageTest() {
        super("src/test/resources/images/test2.png");
    }

    @Test
    @Story("Get EcoNews by ID")
    @Description("Verify that EcoNews can be successfully retrieved by ID without authorization.")
    public void getEcoNewsByIdTest() {
        Response response = ecoNewsClient.getEcoNewsById(ecoNewsId);
        Assert.assertEquals(response.getStatusCode(), 200);
        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);

        SoftAssert softAssert = new SoftAssert();
        LocalDate expectedDate = LocalDate.of(2026, 2, 13);
        softAssert.assertEquals(ecoNews.getCreationDate().toLocalDate(), expectedDate,
                "Creation date should match expected");
        softAssert.assertNotNull(ecoNews.getImagePath(), "Image path should not be null");
        softAssert.assertEquals(ecoNews.getId(), ecoNewsId, "EcoNews ID should match");
        softAssert.assertNotNull(ecoNews.getTitle(), "Title should not be null");
        softAssert.assertEquals(ecoNews.getTitle(), "Welcome to Wikipedia", "Title should match expected");
        softAssert.assertEquals(
                ecoNews.getContent(),
                "The Saxe-Goldstein hypothesis is a prediction in archaeology about " +
                        "the relationship between a society's funerary practices and its social organization.",
                "Content should match expected"
        );
        softAssert.assertNotNull(ecoNews.getShortInfo(), "ShortInfo should not be null");
        softAssert.assertEquals(
                ecoNews.getShortInfo(),
                "The main page of Wikipedia in English",
                "Content should match expected"
        );
        softAssert.assertNotNull(ecoNews.getAuthor(), "Author should not be null");
        softAssert.assertEquals(ecoNews.getAuthor().getId(), 149,
                "Author ID should match expected");
        softAssert.assertEquals(ecoNews.getAuthor().getName(), "NameForTest611",
                "Author name should match expected");
        softAssert.assertEquals(ecoNews.getLikes(), 0, "Likes should be 0");
        softAssert.assertEquals(ecoNews.getDislikes(), 0, "Dislikes should be 0");
        softAssert.assertEquals(ecoNews.getCountComments(), 0,
                "Count of comments should be 0");
        softAssert.assertFalse(ecoNews.isHidden(), "Hidden should be false");
        softAssert.assertNotNull(ecoNews.getTagsEn(), "Tags in English should not be null");
        softAssert.assertEquals(ecoNews.getTagsEn().size(), 2,
                "Tags in English should contain 1 element");
        softAssert.assertEquals(ecoNews.getTagsEn().getFirst(), "News",
                "Tags in English element should match");
        softAssert.assertNotNull(ecoNews.getTagsUk(), "Tags in Ukrainian should not be null");
        softAssert.assertEquals(ecoNews.getTagsUk().size(), 2,
                "Tags in Ukrainian should contain 1 element");
        softAssert.assertEquals(ecoNews.getTagsUk().getFirst(), "Новини",
                "Tags in Ukrainian element should match");
        softAssert.assertAll();
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
    public void testUpdateEcoNewsByIdWithInvalidTagShouldReturn400() {
        UpdateEcoNewsDto updateDto = new UpdateEcoNewsDto();
        updateDto.setId(ecoNewsId);
        updateDto.setTags(List.of("string"));
        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, null);
        Assert.assertEquals(response.getStatusCode(), 400,
                "There should be at least one valid tag");
    }

    @Test
    @Story("Update EcoNews with image causing server error")
    @Description("Verify that updating EcoNews with a specific image triggers a 500 Internal Server Error")
    public void testUpdateEcoNewsByIdWithImageShouldReturn200() {
        EcoNewsDtoFactory dtoFactory = new EcoNewsDtoFactory(ecoNewsId);
        UpdateEcoNewsDto updateDto = dtoFactory.createDefaultDto();

        String imagePath = "src/test/resources/images/test2.png";
        File image = new File(imagePath);

        if (!image.exists()) {
            throw new RuntimeException("Image file not found: " + image.getAbsolutePath());
        }

        Response response = ecoNewsClient
                .updateEcoNewsById(ecoNewsId, updateDto, imagePath);

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected status code 200"
        );
        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        Assert.assertNotNull(
                ecoNews.getImagePath(),
                "Image path should not be null"
        );
    }
}
