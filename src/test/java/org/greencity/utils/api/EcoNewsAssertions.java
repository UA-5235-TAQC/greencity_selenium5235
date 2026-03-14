package org.greencity.utils.api;

import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.models.econews.EcoNewsBase;
import org.greencity.api.models.econews.EcoNewsQuery;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.testng.asserts.SoftAssert;
import io.restassured.response.Response;

import static org.greencity.utils.api.ApiTestAssertions.assertOk;

public class EcoNewsAssertions {

    public static void assertEcoNewsResponse(EcoNewsResponse actual,
                                             EcoNewsBase expected,
                                             boolean checkImage,
                                             boolean checkAuthor) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actual.getId(), expected.getId(),
                "ID should match");
        softAssert.assertEquals(actual.getTitle(), expected.getTitle(),
                "Title should match");
        softAssert.assertEquals(actual.getContent(), expected.getContent(),
                "Content should match");
        softAssert.assertEquals(actual.getShortInfo(), expected.getShortInfo(),
                "ShortInfo should match");

        softAssert.assertNotNull(actual.getTagsEn(),
                "Tags EN should not be null");
        softAssert.assertEquals(actual.getTagsEn(), expected.getTagsEn(),
                "Tags EN should match");
        softAssert.assertNotNull(actual.getTagsUk(),
                "Tags UK should not be null");
        softAssert.assertEquals(actual.getTagsUk(), expected.getTagsUk(),
                "Tags UK should match");

        if (checkImage) {
            softAssert.assertNotNull(actual.getImagePath(),
                    "Image path should not be null");
        } else {
            softAssert.assertNull(actual.getImagePath(),
                    "Image path should be null");
        }

        if (expected instanceof EcoNewsResponse expectedResponse) {
            if (expectedResponse.getCreationDate() != null) {
                softAssert.assertEquals(actual.getCreationDate(), expectedResponse.getCreationDate(),
                        "Creation date should match expected");
            }
        }

        if (checkAuthor) {
            softAssert.assertNotNull(actual.getAuthor(),
                    "Author should not be null");
            if (expected instanceof EcoNewsResponse expectedResponse) {
                if (expectedResponse.getAuthor() != null) {
                    softAssert.assertEquals(actual.getAuthor().getId(), expectedResponse.getAuthor().getId(),
                            "Author ID should match expected");
                    softAssert.assertEquals(actual.getAuthor().getName(), expectedResponse.getAuthor().getName(),
                            "Author name should match expected");
                }
            }
        }

        softAssert.assertEquals(actual.getLikes(), 0,
                "Likes should be 0");
        softAssert.assertEquals(actual.getDislikes(), 0,
                "Dislikes should be 0");
        softAssert.assertEquals(actual.getCountComments(), 0,
                "Count of comments should be 0");
        softAssert.assertFalse(actual.isHidden(),
                "Hidden should be false");

        softAssert.assertAll();
    }

    public static void getEcoNewsByAuthor(EcoNewsClient client, int authorId) {
        EcoNewsQuery query = EcoNewsQuery.builder()
                .authorId(authorId)
                .build();

        Response response = client.getEcoNews(query);
        assertOk(response);
    }
}
