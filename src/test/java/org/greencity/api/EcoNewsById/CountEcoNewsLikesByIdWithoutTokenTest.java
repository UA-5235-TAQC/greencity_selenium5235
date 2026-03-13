package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.greencity.utils.api.ErrorResponse;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


@Epic("EcoNews API")
@Feature("Count likes on EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class CountEcoNewsLikesByIdWithoutTokenTest extends EcoNewsWithoutTokenRunner {

    @Test
    @Description("Verify that an attempt to get likes count on non-existing Eco News returns 404 status code.")
    public void countNonExistingEcoNewsLikes() {
        long nonExistingId = 999999L;
        Response response = ecoNewsClient.countEcoNewsLikes(nonExistingId);
        ErrorResponse error = response.as(ErrorResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 404, "Status code should be 404 for getting likes count on non-existing Eco News.");
        softAssert.assertEquals(error.getMessage(), "Eco new doesn't exist by this id: " + nonExistingId, "Error message should be 'Eco new doesn't exist by this id: " + nonExistingId + "'");
        softAssert.assertAll();
    }
}
