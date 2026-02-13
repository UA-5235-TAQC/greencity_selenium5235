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

import java.time.LocalDate;

@Epic("EcoNews API")
@Feature("EcoNews CRUD with authorization")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsByIdWithoutImageTest extends CreateNewsRunner {

    public EcoNewsByIdWithoutImageTest() {
        super(null);
    }

    @Test
    @Story("Update EcoNews without image")
    @Description("Verify that updating EcoNews without providing an image works correctly")
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
}
