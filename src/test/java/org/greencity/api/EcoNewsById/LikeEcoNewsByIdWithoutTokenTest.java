package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
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
public class LikeEcoNewsByIdWithoutTokenTest extends EcoNewsWithoutTokenRunner {

    @Test
    @Description("Verify that an attempt to like another user's EcoNews without being authorized returns 401 status code")
    public void likeAnotherUsersEcoNewsByIdWithoutToken() {
        long anotherUserId = 3;
        Map<String, Object> queryParams = Map.of("author-id", anotherUserId);
        Response anotherUserEcoNewsPageResponse = ecoNewsClient.getEcoNews(queryParams);
        Assert.assertEquals(anotherUserEcoNewsPageResponse.getStatusCode(), 200, "Status code should be 200 for getting Eco News created by user with ID " + anotherUserId);

        EcoNewsPageResponse ecoNewsPageResponse = anotherUserEcoNewsPageResponse.as(EcoNewsPageResponse.class);
        long anotherUserEcoNewsId = ecoNewsPageResponse.getPage().getFirst().getId();
        Response anotherUserEcoNewsResponse = ecoNewsClient.likeEcoNewsById(anotherUserEcoNewsId);
        ErrorResponse error = anotherUserEcoNewsResponse.as(ErrorResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getStatus(), 401, "Status code should be 401 for liking Eco News without being authorized");
        softAssert.assertEquals(error.getError(), "Unauthorized", "Error message should be 'Unauthorized'");
        softAssert.assertAll();
    }
}
