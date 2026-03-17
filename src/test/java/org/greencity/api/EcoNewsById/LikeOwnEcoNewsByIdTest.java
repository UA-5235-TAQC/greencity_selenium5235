package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.CreateNewsRunner;

import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.assertBadRequest;

@Epic("EcoNews API")
@Feature("Like EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class LikeOwnEcoNewsByIdTest extends CreateNewsRunner {

    @Test
    @Description("Verify that an attempt to like user's own EcoNews returns 400 status code ")
    public void likeOwnEcoNews() {
        Response response = ecoNewsClient.likeEcoNewsById(ecoNewsId);
        assertBadRequest(response,
                "Current user has no permission for this action");
    }
}
