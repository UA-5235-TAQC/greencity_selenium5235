package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("EcoNews API")
@Feature("Like EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class LikeEcoNewsByIdWithoutTokenTest extends EcoNewsWithoutTokenRunner {

    @Test
    @Description("Verify that an attempt to like another user's EcoNews without being authorized returns 401 status code")
    public void likeAnotherUsersEcoNewsByIdWithoutToken() {
        long anotherUserId = 3;
        Map<String, Object> queryParams = Map.of("author-id", anotherUserId);
        Response anotherUserEcoNewsPageResponse = ecoNewsClient.getEcoNews(queryParams);
        Assert.assertEquals(anotherUserEcoNewsPageResponse.getStatusCode(), 200, "Status code should be 200 for getting Eco News created by user with ID " + anotherUserId);

        long anotherUserEcoNewsId = anotherUserEcoNewsPageResponse.jsonPath().getLong("page[0].id");
        Response anotherUserEcoNewsResponse = ecoNewsClient.likeEcoNewsById(anotherUserEcoNewsId);
        Assert.assertEquals(anotherUserEcoNewsResponse.getStatusCode(), 401, "Status code should be 401 for liking Eco News without being authorized");
    }
}
