package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("EcoNews API")
@Feature("Like own EcoNews by ID")
@Severity(SeverityLevel.CRITICAL)
@Tag("API")
public class LikeOwnEcoNewsByIdTest extends CreateNewsRunner {

    @Test
    @Description("Verify that an attempt to like user's own EcoNews returns 400 status code ")
    public void likeOwnEcoNews() {
        Response response = ecoNewsClient.likeEcoNewsById(ecoNewsId);

        Assert.assertEquals(response.getStatusCode(), 400, "Users can not like their own Eco News.");
    }
}
