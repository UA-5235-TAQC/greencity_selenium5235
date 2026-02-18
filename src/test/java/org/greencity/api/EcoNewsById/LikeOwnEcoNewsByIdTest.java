package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.greencity.utils.api.ErrorResponse;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("EcoNews API")
@Feature("Like EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class LikeOwnEcoNewsByIdTest extends CreateNewsRunner {

    @Test
    @Description("Verify that an attempt to like user's own EcoNews returns 400 status code ")
    public void likeOwnEcoNews() {
        Response response = ecoNewsClient.likeEcoNewsById(ecoNewsId);
        ErrorResponse error = response.as(ErrorResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 400, "Status code should be 400 for liking own Eco News");
        softAssert.assertEquals(error.getMessage(), "Current user has no permission for this action", "Error message should be 'Current user has no permission for this action'");
        softAssert.assertAll();
    }
}
