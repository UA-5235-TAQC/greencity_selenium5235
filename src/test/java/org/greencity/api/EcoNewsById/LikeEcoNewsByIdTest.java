package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.EcoNewsWithTokenRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        Assert.assertEquals(response.getStatusCode(), 404, "Status code should be 404 for liking non-existing Eco News.");
    }

    @Test
    @Description("Verify that an attempt to like another user's EcoNews returns 200 status code")
    public void likeAnotherUsersEcoNewsById() {
        long anotherUserId = 3;
        Map<String, Object> queryParams = Map.of("author-id", anotherUserId);
        Response anotherUserEcoNewsPageResponse = ecoNewsClient.getEcoNews(queryParams);
        Assert.assertEquals(anotherUserEcoNewsPageResponse.getStatusCode(), 200, "Status code should be 200 for getting Eco News created by user with ID " + anotherUserId);

        long anotherUserEcoNewsId = anotherUserEcoNewsPageResponse.jsonPath().getLong("page[0].id");
        Response anotherUserEcoNewsResponse = ecoNewsClient.likeEcoNewsById(anotherUserEcoNewsId);
        Assert.assertEquals(anotherUserEcoNewsResponse.getStatusCode(), 200, "Status code should be 200 for liking Eco News by ID " + anotherUserEcoNewsId);
    }
}
