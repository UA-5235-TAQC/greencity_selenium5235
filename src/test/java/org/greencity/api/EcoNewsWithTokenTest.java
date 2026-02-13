package org.greencity.api;

import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.testrunners.EcoNewsWithTokenRunner;
import org.greencity.utils.api.EcoNewsDtoFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class EcoNewsWithTokenTest extends EcoNewsWithTokenRunner {

    private final long ecoNewsId = 1443L;

    @Test
    public void testUpdateEcoNewsByIdWithInvalidTagShouldReturn400() {
        UpdateEcoNewsDto updateDto = new UpdateEcoNewsDto();
        updateDto.setId(ecoNewsId);
        updateDto.setTags(List.of("string"));
        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, null);
        Assert.assertEquals(response.getStatusCode(), 400,
                "There should be at least one valid tag");
    }

    @Test
    public void testUpdateEcoNewsByIdWithoutImage() {
        EcoNewsDtoFactory dtoFactory = new EcoNewsDtoFactory(ecoNewsId);
        UpdateEcoNewsDto updateDto = dtoFactory.createDefaultDto();
        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, null);
        Assert.assertEquals(response.getStatusCode(), 200);
        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        SoftAssert softAssert = new SoftAssert();
        LocalDate expectedDate = LocalDate.of(2026, 2, 13);
        softAssert.assertEquals(ecoNews.getCreationDate().toLocalDate(), expectedDate,
                "Edit date should match expected");
        softAssert.assertNull(ecoNews.getImagePath(),
                "Image path should be null");
        softAssert.assertEquals(ecoNews.getId(), ecoNewsId, "EcoNews ID should match");
        softAssert.assertEquals(ecoNews.getTitle(), updateDto.getTitle(), "Title should match expected");
        softAssert.assertEquals(ecoNews.getContent(), updateDto.getContent(),
                "Content should match expected"
        );
        softAssert.assertEquals(ecoNews.getShortInfo(), updateDto.getShortInfo(),
                "Short information should match expected"
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
                "Tags in English should contain 2 elements");
        softAssert.assertEquals(ecoNews.getTagsEn().getFirst(), "News",
                "Tags in English element should match");
        softAssert.assertNotNull(ecoNews.getTagsUk(), "Tags in Ukrainian should not be null");
        softAssert.assertEquals(ecoNews.getTagsUk().size(), 2,
                "Tags in Ukrainian should contain 2 elements");
        softAssert.assertEquals(ecoNews.getTagsUk().getFirst(), "Новини",
                "Tags in Ukrainian element should match");
        softAssert.assertAll();
    }

    @Test
    public void testUpdateEcoNewsByIdWithImageShouldReturn500() {
        EcoNewsDtoFactory dtoFactory = new EcoNewsDtoFactory(ecoNewsId);
        UpdateEcoNewsDto updateDto = dtoFactory.createDefaultDto();

        File image = new File("src/test/resources/images/test2.png");
        if (!image.exists()) {
            throw new RuntimeException("Image file not found: " + image.getAbsolutePath());
        }

        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, image);
        Assert.assertEquals(response.getStatusCode(), 500,
                "Expected status code 500");
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "No message available",
                "Message should match expected");

        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        Assert.assertNull(ecoNews.getImagePath(),
                "Image path should be null");
    }

    @Test
    public void testDeleteEcoNewsByIdShouldReturn200() {
        long ecoNewsIdToDelete = 1445L;
        Response deleteResponse = ecoNewsClient.deleteEcoNewsById(ecoNewsIdToDelete);
        Assert.assertEquals(deleteResponse.getStatusCode(), 200,
                "EcoNews should be deleted successfully");

        Response getResponse = ecoNewsClient.getEcoNewsById(ecoNewsIdToDelete);
        Assert.assertEquals(getResponse.getStatusCode(), 404,
                "Deleted EcoNews should not be found");
    }

    @Test
    public void testDeleteNonExistingEcoNewsShouldReturn404() {
        long nonExistingId = 999999L;
        Response deleteResponse = ecoNewsClient.deleteEcoNewsById(nonExistingId);
        Assert.assertEquals(deleteResponse.getStatusCode(), 404, "Deleting non-existing EcoNews should return 404");

        String message = deleteResponse.jsonPath().getString("message");
        Assert.assertEquals(message, "Eco new doesn't exist by this id: " + nonExistingId,
                "Message should match expected");
    }
}
