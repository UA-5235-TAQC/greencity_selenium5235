package org.greencity.api.EcoNewsById;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.greencity.utils.api.EcoNewsDtoFactory;
import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.assertBadRequest;
import static org.greencity.utils.api.ApiTestAssertions.assertOk;

@Feature("Add News to Favorites with a token")
@Severity(SeverityLevel.CRITICAL)
public class FavoriteEcoNewsWithTokenTest extends CreateNewsRunner {

    @Test
    @Description("Add EcoNews to favorites and then remove it (authorized)")
    public void addAndRemoveEcoNewsFavorites_authorized() {
        Response addResponse = ecoNewsClient.addToFavorites(ecoNewsId);
        assertOk(addResponse);
        Response removeResponse = ecoNewsClient.removeFromFavorites(ecoNewsId);
        assertOk(removeResponse);
    }

    @Test
    @Description("Add the same EcoNews twice - should return 400 error")
    public void addToFavoritesTwiceShouldReturnError400() {
        EcoNewsRequest request = EcoNewsDtoFactory.createNewsUa();
        Response response = ecoNewsClient.postEcoNews(request);
        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        Long newsId = ecoNews.getId();

        Response addResponse = ecoNewsClient.addToFavorites(newsId);
        assertOk(addResponse);

        Response duplicateAddResponse = ecoNewsClient.addToFavorites(newsId);
        assertBadRequest(duplicateAddResponse,
                "User has already added this eco new to favorites.");
        ecoNewsClient.deleteEcoNewsById(newsId);
    }
}
