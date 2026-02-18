package org.greencity.api.EcoNewsById;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Add News to Favorites without token")
@Severity(SeverityLevel.CRITICAL)
public class FavoriteEcoNewsWithoutTokenTest extends EcoNewsWithoutTokenRunner {

    @Test
    @Description("Attempt to add EcoNews to favorites without token - should return 401")
    public void addToFavorites_unauthorized_shouldReturn401() {
        Assert.assertEquals(ecoNewsClient.addToFavorites(ecoNewsId).getStatusCode(), 401);
    }

    @Test
    @Description("Attempt to remove EcoNews from favorites without token - should return 401")
    public void removeFromFavorites_unauthorized_shouldReturn401() {
        Assert.assertEquals(ecoNewsClient.removeFromFavorites(ecoNewsId).getStatusCode(), 401);
    }
}
