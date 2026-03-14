package org.greencity.api.EcoNewsById;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.assertBadRequest;
import static org.greencity.utils.api.ApiTestAssertions.assertOk;

@Feature("Add News to Favorites with a token")
@Severity(SeverityLevel.CRITICAL)
public class FavoriteEcoNewsWithTokenTest extends CreateNewsRunner {

    @BeforeMethod
    public void cleanFavoritesState() {
        ecoNewsClient.removeFromFavorites(ecoNewsId);
    }

    @Test
    @Description("Add EcoNews to favorites and then remove it (authorized)")
    public void addAndRemoveEcoNewsFavorites_authorized() {
        assertOk(ecoNewsClient.addToFavorites(ecoNewsId));
        assertOk(ecoNewsClient.removeFromFavorites(ecoNewsId));
    }

    @Test
    @Description("Add the same EcoNews twice - should return 400 error")
    public void addToFavoritesTwiceShouldReturnError400() {
        assertOk(ecoNewsClient.addToFavorites(ecoNewsId));

        Response duplicateAddResponse = ecoNewsClient.addToFavorites(ecoNewsId);
        assertBadRequest(duplicateAddResponse,
                "User has already added this eco new to favorites.");
    }
}
