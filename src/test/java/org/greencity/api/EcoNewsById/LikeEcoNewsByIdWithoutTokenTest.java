package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsPageResponse;
import org.greencity.api.models.econews.EcoNewsQuery;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.assertOk;
import static org.greencity.utils.api.ApiTestAssertions.assertUnauthorized;

@Epic("EcoNews API")
@Feature("Like EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class LikeEcoNewsByIdWithoutTokenTest extends EcoNewsWithoutTokenRunner {

    @Test
    @Description("Verify that an attempt to like another user's EcoNews without being authorized returns 401 status code")
    public void likeAnotherUsersEcoNewsByIdWithoutToken() {
        int anotherUserId = 3;
        EcoNewsQuery query = EcoNewsQuery.builder()
                .authorId(anotherUserId)
                .build();
        Response anotherUserEcoNewsPageResponse = ecoNewsClient.getEcoNews(query);
        assertOk(anotherUserEcoNewsPageResponse);

        EcoNewsPageResponse ecoNewsPageResponse = anotherUserEcoNewsPageResponse.as(EcoNewsPageResponse.class);
        long anotherUserEcoNewsId = ecoNewsPageResponse.getPage().getFirst().getId();
        Response anotherUserEcoNewsResponse = ecoNewsClient.likeEcoNewsById(anotherUserEcoNewsId);
        assertUnauthorized(anotherUserEcoNewsResponse);
    }
}
