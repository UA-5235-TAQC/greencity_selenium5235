package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.greencity.api.testrunners.SecondUserRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.assertNotFound;
import static org.greencity.utils.api.ApiTestAssertions.assertOk;
import static org.greencity.utils.api.EcoNewsAssertions.getEcoNewsByAuthor;

@Epic("EcoNews API")
@Feature("Like EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class LikeEcoNewsByIdTest extends CreateNewsRunner {

    private EcoNewsClient secondUserClient;

    @BeforeClass
    public void prepareSecondUser() {
        SecondUserRunner secondUser = new SecondUserRunner();
        secondUser.loginSecondUser();
        secondUserClient = secondUser.getEcoNewsClient();
    }

    @Test
    @Description("Verify that an attempt to like non-existing EcoNews returns 404 status code")
    public void likeNonExistingEcoNewsById() {
        long nonExistingId = ecoNewsId + 10;
        Response response = ecoNewsClient.likeEcoNewsById(nonExistingId);
        assertNotFound(response, "Eco new doesn't exist by this id: " + nonExistingId);
    }

    @Test
    @Description("Verify that an attempt to like another user's EcoNews returns 200 status code")
    public void likeAnotherUsersEcoNewsById() {
        getEcoNewsByAuthor(secondUserClient, testValueProvider.getSecondUserId());
        Response response = secondUserClient.likeEcoNewsById(ecoNewsId);
        assertOk(response);
    }
}
