package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.EcoNewsWithTokenRunner;
import org.greencity.utils.api.EcoNewsPageResponse;
import org.greencity.utils.api.ErrorResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

@Epic("EcoNews API")
@Feature("Like EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class LikeEcoNewsByIdTest extends EcoNewsWithTokenRunner {

    @Test
    @Description("Verify that an attempt to like non-existing EcoNews returns 404 status code")
    public void likeNonExistingEcoNewsById() {
        long nonExistingId = 999999L;
        Response response = ecoNewsClient.likeEcoNewsById(nonExistingId);
        ErrorResponse error = response.as(ErrorResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 404, "Status code should be 404 for liking non-existing Eco News.");
        softAssert.assertEquals(error.getMessage(), "Eco new doesn't exist by this id: " + nonExistingId, "Error message should be 'Eco new doesn't exist by this id: " + nonExistingId + "'");
        softAssert.assertAll();
    }

    @Test
    @Description("Verify that an attempt to like another user's EcoNews returns 200 status code")
    public void likeAnotherUsersEcoNewsById() {
        long anotherUserId = 3;
        Map<String, Object> queryParams = Map.of("author-id", anotherUserId);
        Response anotherUserEcoNewsPageResponse = ecoNewsClient.getEcoNews(queryParams);
        Assert.assertEquals(anotherUserEcoNewsPageResponse.getStatusCode(), 200, "Status code should be 200 for getting Eco News created by user with ID " + anotherUserId);

        EcoNewsPageResponse ecoNewsPageResponse = anotherUserEcoNewsPageResponse.as(EcoNewsPageResponse.class);
        long anotherUserEcoNewsId = ecoNewsPageResponse.getPage().getFirst().getId();
        Response anotherUserEcoNewsResponse = ecoNewsClient.likeEcoNewsById(anotherUserEcoNewsId);
        Assert.assertEquals(anotherUserEcoNewsResponse.getStatusCode(), 200, "Status code should be 200 for liking Eco News by ID " + anotherUserEcoNewsId);
    }
}
