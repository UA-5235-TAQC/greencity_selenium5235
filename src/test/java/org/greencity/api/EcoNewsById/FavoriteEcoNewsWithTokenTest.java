package org.greencity.api.EcoNewsById;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.greencity.api.ApiTestRunner;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.models.common.ErrorResponse;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Add News to Favorites with a token")
@Severity(SeverityLevel.CRITICAL)
public class FavoriteEcoNewsWithTokenTest extends ApiTestRunner {

    private EcoNewsClient ecoNewsClientWithToken;
    private long ecoNewsId;

    @BeforeClass
    public void setUpClientWithToken() {

        // Sign in and create authorized client
        ecoNewsClientWithToken = getAuthorizedEcoNewsClient();

        ecoNewsId = createTestEcoNews(ecoNewsClientWithToken);
        Assert.assertTrue(ecoNewsId > 0, "Created EcoNews ID is invalid.");

    }

    @BeforeMethod
    public void cleanFavoritesState() { ecoNewsClientWithToken.removeFromFavorites(ecoNewsId);}

    @AfterClass
    public void deleteCreatedNews() {deleteEcoNewsAndAssert(ecoNewsClientWithToken, ecoNewsId);}


    @Test
    @Description("Add EcoNews to favorites and then remove it (authorized)")
    public void addAndRemoveEcoNewsFavorites_authorized() {

        Response addResp = ecoNewsClientWithToken.addToFavorites(ecoNewsId);
        Assert.assertEquals(addResp.getStatusCode(), 200);

        Response removeResp = ecoNewsClientWithToken.removeFromFavorites(ecoNewsId);
        Assert.assertEquals(removeResp.getStatusCode(), 200);
    }

    @Test
    @Description("Add the same EcoNews twice - should return 400 error")
    public void addToFavoritesTwiceShouldReturnError400() {

        // First add should succeed
        Response addResponse = ecoNewsClientWithToken.addToFavorites(ecoNewsId);
        Assert.assertEquals(addResponse.getStatusCode(), 200);

        // Second should fail
        Response duplicateAddResponse = ecoNewsClientWithToken.addToFavorites(ecoNewsId);
        Assert.assertEquals(duplicateAddResponse.getStatusCode(), 400);

        // Assert the error message matches expected duplicate favorite message
        ErrorResponse error = duplicateAddResponse.as(ErrorResponse.class);
        Assert.assertEquals(error.getMessage(), "User has already added this eco new to favorites."
        );
    }
}
