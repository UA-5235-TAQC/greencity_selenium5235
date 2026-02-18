package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.CreateNewsBeforeTestRunner;
import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.*;

@Epic("EcoNews API")
@Feature("Delete EcoNews")
@Severity(SeverityLevel.NORMAL)
@Tag("EcoNewsById API")
public class DeleteEcoNewsByIdTest extends CreateNewsBeforeTestRunner {

    @Test
    @Story("Delete existing EcoNews")
    @Description("Verify that an authorized user can successfully delete an existing EcoNews item")
    public void testDeleteEcoNewsById() {

        // --- Delete the existing news ---
        Response deleteResponse = ecoNewsClient.deleteEcoNewsById(ecoNewsId);
        assertOk(deleteResponse);

        // --- Verify it no longer exists ---
        Response getResponse = ecoNewsClient.getEcoNewsById(ecoNewsId);
        assertNotFound(getResponse,
                "Eco new doesn't exist by this id: " + ecoNewsId);
    }

    @Test
    @Story("Delete non-existing EcoNews")
    @Description("Verify that deleting a non-existing EcoNews returns 404 Not Found")
    public void testDeleteNonExistingEcoNewsShouldReturn404() {

        long nonExistingId = ecoNewsId + 10;

        Response deleteResponse = ecoNewsClient.deleteEcoNewsById(nonExistingId);
        assertNotFound(deleteResponse,
                "Eco new doesn't exist by this id: " + nonExistingId);
    }
}
