package org.greencity.api;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.time.LocalDate;

@Epic("EcoNews API")
@Feature("EcoNews CRUD without authorization")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsWithoutTokenTest extends EcoNewsWithoutTokenRunner {

    private final long ecoNewsIdEn = 830L;

    @Test
    @Story("Get EcoNews by ID")
    @Description("Verify that EcoNews can be successfully retrieved by ID without authorization.")
    public void getEcoNewsByIdTest() {
        Response response = ecoNewsClient.getEcoNewsById(ecoNewsIdEn);
        Assert.assertEquals(response.getStatusCode(), 200);
        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);

        SoftAssert softAssert = new SoftAssert();
        LocalDate expectedDate = LocalDate.of(2026, 1, 30);
        softAssert.assertEquals(ecoNews.getCreationDate().toLocalDate(), expectedDate,
                "Creation date should match expected");
        softAssert.assertNotNull(ecoNews.getImagePath(), "Image path should not be null");
        softAssert.assertEquals(ecoNews.getImagePath(),
                "https://csb10032000a548f571.blob.core.windows.net/allfiles/c02466d2-aaf2-40c4-b54f-109fc0aa4887test.jfif",
                "Image path should match expected");
        softAssert.assertEquals(ecoNews.getId(), ecoNewsIdEn, "EcoNews ID should match");
        softAssert.assertNotNull(ecoNews.getTitle(), "Title should not be null");
        softAssert.assertEquals(ecoNews.getTitle(), "Test", "Title should match expected");
        softAssert.assertEquals(
                ecoNews.getContent(),
                "<p>Test content with 20 chars</p>",
                "Content should match expected"
        );
        softAssert.assertNull(ecoNews.getShortInfo(), "ShortInfo should be null");
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
        softAssert.assertEquals(ecoNews.getTagsEn().size(), 1,
                "Tags in English should contain 1 element");
        softAssert.assertEquals(ecoNews.getTagsEn().getFirst(), "News",
                "Tags in English element should match");
        softAssert.assertNotNull(ecoNews.getTagsUk(), "Tags in Ukrainian should not be null");
        softAssert.assertEquals(ecoNews.getTagsUk().size(), 1,
                "Tags in Ukrainian should contain 1 element");
        softAssert.assertEquals(ecoNews.getTagsUk().getFirst(), "Новини",
                "Tags in Ukrainian element should match");
        softAssert.assertAll();
    }

    @Test
    @Story("Get non-existing EcoNews")
    @Description("Verify that requesting non-existing EcoNews returns 404 status code.")
    public void getNonExistingEcoNewsTest() {
        long ecoNewsId = 1L;
        Response response = ecoNewsClient.getEcoNewsById(ecoNewsId);

        Assert.assertEquals(response.getStatusCode(), 404,
                "Status code should be 404 for non-existing news");

        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "Eco new doesn't exist by this id: " + ecoNewsId,
                "Error message should match expected");
    }

    @Test
    @Story("Get EcoNews in English")
    @Description("Verify that EcoNews can be retrieved in English using lang parameter.")
    public void getEcoNewsEnTest() {
        Response response = ecoNewsClient.getEcoNewsByIdWithLang(ecoNewsIdEn, "en");
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200 with lang parameter");
        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        Assert.assertNotNull(ecoNews.getTitle(), "Title in English should not be null");
    }

    @Test
    @Story("Get EcoNews in Ukrainian")
    @Description("Verify that EcoNews can be retrieved in Ukrainian using lang parameter.")
    public void getEcoNewsUkTest() {
        long ecoNewsIdEn = 888L;
        Response response = ecoNewsClient.getEcoNewsByIdWithLang(ecoNewsIdEn, "uk");
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200 with lang parameter");
        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(ecoNews.getTitle(),
                "Title in Ukrainian should not be null");
        softAssert.assertEquals(ecoNews.getTitle(), "Tecт",
                "Title should match expected");
        softAssert.assertEquals(
                ecoNews.getContent(),
                "<p>Тестовий контент з 30 символів</p>",
                "Content should match expected"
        );
        softAssert.assertAll();
    }

    @Test
    @Story("Update EcoNews without token")
    @Description("Verify that updating EcoNews without authorization returns 401 status code.")
    public void testUpdateEcoNewsByIdWithoutTokenShouldReturn401() {
        UpdateEcoNewsDto updateDto = new UpdateEcoNewsDto();
        updateDto.setId(ecoNewsIdEn);
        updateDto.setTitle("Another string");
        updateDto.setContent("Test content with more than 20 chars");
        updateDto.setShortInfo("Short info");
        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsIdEn, updateDto, null);
        Assert.assertEquals(response.getStatusCode(), 401,
                "Status code should be 401 Unauthorized");
    }

    @Test
    @Story("Delete EcoNews without token")
    @Description("Verify that deleting EcoNews without authorization returns 401 status code.")
    public void testDeleteEcoNewsByIdWithoutTokenShouldReturn401() {
        long ecoNewsId = 1488L;
        Response deleteResponse = ecoNewsClient.deleteEcoNewsById(ecoNewsId);
        Assert.assertEquals(deleteResponse.getStatusCode(), 401,
                "Status code should be 401 Unauthorized");
    }
}
