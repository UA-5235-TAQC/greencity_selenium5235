package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("EcoNews API")
@Feature("EcoNews CRUD without authorization")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsByIdWithoutTokenTest extends EcoNewsWithoutTokenRunner {

    @Test
    @Story("Update EcoNews without token")
    @Description("Verify that updating EcoNews without authorization returns 401 status code.")
    public void testUpdateEcoNewsByIdWithoutTokenShouldReturn401() {
        UpdateEcoNewsDto updateDto = new UpdateEcoNewsDto();
        updateDto.setId(ecoNewsId);
        updateDto.setTitle("Another string");
        updateDto.setContent("Test content with more than 20 chars");
        updateDto.setShortInfo("Short info");
        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto, null);
        Assert.assertEquals(response.getStatusCode(), 401,
                "Status code should be 401 Unauthorized");
    }

    @Test
    @Story("Delete EcoNews without token")
    @Description("Verify that deleting EcoNews without authorization returns 401 status code.")
    public void testDeleteEcoNewsByIdWithoutTokenShouldReturn401() {
        Response deleteResponse = ecoNewsClient.deleteEcoNewsById(ecoNewsId);
        Assert.assertEquals(deleteResponse.getStatusCode(), 401,
                "Status code should be 401 Unauthorized");
    }
}
