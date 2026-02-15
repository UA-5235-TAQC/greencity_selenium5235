package org.greencity.utils.api;

import org.greencity.api.models.econews.EcoNewsResponse;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

public class EcoNewsAssertions {

    public static void assertEcoNewsResponse(EcoNewsResponse ecoNews,
                                             long expectedId,
                                             String expectedTitle,
                                             String expectedContent,
                                             String expectedShortInfo,
                                             LocalDate expectedDate,
                                             List<String> expectedTagsEn,
                                             List<String> expectedTagsUk,
                                             Integer expectedAuthorId,
                                             String expectedAuthorName,
                                             boolean checkImageNotNull,
                                             boolean checkAuthor) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(ecoNews.getId(), expectedId,
                "EcoNews ID should match");
        softAssert.assertEquals(ecoNews.getTitle(), expectedTitle,
                "Title should match expected");
        softAssert.assertEquals(ecoNews.getContent(), expectedContent,
                "Content should match expected");
        softAssert.assertEquals(ecoNews.getShortInfo(), expectedShortInfo,
                "ShortInfo should match expected");
        if (expectedDate != null) {
            softAssert.assertEquals(ecoNews.getCreationDate().toLocalDate(), expectedDate,
                    "Creation date should match expected");
        }

        if (checkImageNotNull) {
            softAssert.assertNotNull(ecoNews.getImagePath(),
                    "Image path should not be null");
        } else {
            softAssert.assertNull(ecoNews.getImagePath(),
                    "Image path should be null");
        }
        if (checkAuthor) {
            softAssert.assertNotNull(ecoNews.getAuthor(), "Author should not be null");
            softAssert.assertEquals(Integer.valueOf(ecoNews.getAuthor().getId()),
                    expectedAuthorId,
                    "Author ID should match expected");
            softAssert.assertEquals(ecoNews.getAuthor().getName(), expectedAuthorName,
                    "Author name should match expected");
        }

        softAssert.assertEquals(ecoNews.getLikes(), 0, "Likes should be 0");
        softAssert.assertEquals(ecoNews.getDislikes(), 0, "Dislikes should be 0");
        softAssert.assertEquals(ecoNews.getCountComments(), 0,
                "Count of comments should be 0");
        softAssert.assertFalse(ecoNews.isHidden(), "Hidden should be false");

        softAssert.assertNotNull(ecoNews.getTagsEn(),
                "Tags in English should not be null");
        softAssert.assertEquals(ecoNews.getTagsEn(), expectedTagsEn,
                "Tags in English should match expected");
        softAssert.assertNotNull(ecoNews.getTagsUk(),
                "Tags in Ukrainian should not be null");
        softAssert.assertEquals(ecoNews.getTagsUk(), expectedTagsUk,
                "Tags in Ukrainian should match expected");

        softAssert.assertAll();
    }
}
