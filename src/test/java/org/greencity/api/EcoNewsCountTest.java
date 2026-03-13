package org.greencity.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.testrunners.EcoNewsWithoutTokenRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.assertOk;

public class EcoNewsCountTest extends EcoNewsWithoutTokenRunner {

    @Epic("EcoNews API")
    @Feature("EcoNews Count")
    @Story("Verify EcoNews count by author ID")
    @Severity(SeverityLevel.NORMAL)
    @Tag("API")
    @Test
    public void getEcoNewsCountByAuthorIdTest() {

        int authorId = testValueProvider.getUserId();

        Response response = ecoNewsClient.getEcoNewsCountByAuthorId(authorId);
        assertOk(response);

        // verify response body
        int count = response.as(Integer.class);

        Assert.assertTrue(
                count >= 0,
                "EcoNews count should be >= 0");

    }
}
