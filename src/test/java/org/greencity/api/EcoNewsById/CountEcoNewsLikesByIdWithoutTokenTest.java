package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.assertNotFound;


@Epic("EcoNews API")
@Feature("Count likes on EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class CountEcoNewsLikesByIdWithoutTokenTest extends EcoNewsWithoutTokenRunner {

    @Test
    @Description("Verify that an attempt to get likes count on non-existing Eco News returns 404 status code.")
    public void countNonExistingEcoNewsLikes() {
        long nonExistingId = ecoNewsId + 10;
        Response response = ecoNewsClient.countEcoNewsLikes(nonExistingId);
        assertNotFound(response, "Eco new doesn't exist by this id: " + nonExistingId);
    }
}
