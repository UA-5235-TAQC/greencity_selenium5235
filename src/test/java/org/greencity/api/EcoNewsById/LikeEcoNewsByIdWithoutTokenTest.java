package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.assertUnauthorized;
import static org.greencity.utils.api.EcoNewsAssertions.getEcoNewsByAuthor;

@Epic("EcoNews API")
@Feature("Like EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class LikeEcoNewsByIdWithoutTokenTest extends CreateNewsRunner {

    protected EcoNewsClient secondUserClient;

    @BeforeClass
    public void prepareSecondUserClient() {
        secondUserClient = new EcoNewsClient(
                testValueProvider.getGreencityAPIUrl()
        );
    }

    @Test
    @Description("Verify that an attempt to like another user's EcoNews without being authorized returns 401 status code")
    public void likeAnotherUsersEcoNewsByIdWithoutToken() {
        getEcoNewsByAuthor(secondUserClient, testValueProvider.getSecondUserId());
        Response anotherUserEcoNewsResponse = secondUserClient.likeEcoNewsById(ecoNewsId);
        assertUnauthorized(anotherUserEcoNewsResponse);
    }
}
