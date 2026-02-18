package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.testng.Assert;
import org.testng.annotations.Test;


@Epic("EcoNews API")
@Feature("Count likes on EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class CountEcoNewsLikesByIdTest extends CreateNewsRunner {

    @Test
    @Description("Verify that an attempt to get likes count on Eco News returns 200 status code.")
    public void countEcoNewsLikes() {
        Response response = ecoNewsClient.countEcoNewsLikes(ecoNewsId);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 for getting likes count on Eco News with ID " + ecoNewsId);

        String body = response.getBody().asString().trim();
        Assert.assertTrue(body.matches("-?\\d+"), "Response body is not an integer: " + body);
    }
}
