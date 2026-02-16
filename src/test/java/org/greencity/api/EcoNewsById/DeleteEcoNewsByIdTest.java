package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.CreateNewsBeforeTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("EcoNews API")
@Feature("Delete EcoNews")
@Severity(SeverityLevel.NORMAL)
@Tag("EcoNewsById API")
public class DeleteEcoNewsByIdTest extends CreateNewsBeforeTestRunner {

    @Test
    @Story("Delete existing EcoNews")
    @Description("Verify that an authorized user can successfully delete an existing EcoNews item")
    public void testDeleteEcoNewsById() {
        Response deleteResponse = ecoNewsClient.deleteEcoNewsById(ecoNewsId);
        Assert.assertEquals(deleteResponse.getStatusCode(), 200,
                "EcoNews should be deleted successfully");

        Response getResponse = ecoNewsClient.getEcoNewsById(ecoNewsId);
        Assert.assertEquals(getResponse.getStatusCode(), 404,
                "Deleted EcoNews should not be found");
    }

    @Test
    @Story("Delete non-existing EcoNews")
    @Description("Verify that deleting a non-existing EcoNews returns 404 Not Found")
    public void testDeleteNonExistingEcoNewsShouldReturn404() {
        long nonExistingId = 999999L;
        Response deleteResponse = ecoNewsClient.deleteEcoNewsById(nonExistingId);
        Assert.assertEquals(deleteResponse.getStatusCode(), 404, "Deleting non-existing EcoNews should return 404");

        String message = deleteResponse.jsonPath().getString("message");
        Assert.assertEquals(message, "Eco new doesn't exist by this id: " + nonExistingId,
                "Message should match expected");
    }
}
