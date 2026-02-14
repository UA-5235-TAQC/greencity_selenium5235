package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;

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

        Assert.assertEquals(response.getStatusCode(), 404, "Status code should be 404 for getting likes count on non-existing Eco News.");
    }
}
