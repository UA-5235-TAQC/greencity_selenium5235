package org.greencity.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.utils.TestValueProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EcoNewsCountTest extends ApiTestRunner {

    private EcoNewsClient ecoNewsClient;

    @BeforeClass
    public void setUp() {

        if (testValueProvider == null) {
            testValueProvider = new TestValueProvider();
        }

        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl());
    }

    @Epic("EcoNews API")
    @Feature("EcoNews Count")
    @Story("Verify EcoNews count by author ID")
    @Severity(SeverityLevel.NORMAL)
    @Tag("API")
    @Test
    public void getEcoNewsCountByAuthorIdTest() {

        int authorId = testValueProvider.getUserId();

        Response response = ecoNewsClient.getEcoNewsCountByAuthorId(authorId);

        // verify status code
        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Status code should be 200");

        // verify response body
        int count = response.as(Integer.class);

        Assert.assertTrue(
                count >= 0,
                "EcoNews count should be >= 0");

    }
}
