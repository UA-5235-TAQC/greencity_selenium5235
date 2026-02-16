package org.greencity.api;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.econews.TagResponse;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Tag;

@Epic("EcoNews API")
@Feature("EcoNews Tags")
@Story("Get EcoNews tags by language")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsTagsTest extends ApiTestRunner {

    private EcoNewsClient client;

    @BeforeClass
    public void setUpClient() {

        //  Create security client (USER API)
        OwnSecurityClient securityClient =
                new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());

        //  Log in
        Response loginResponse = securityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        //  Get token
        String token = loginResponse
                .as(SignInResponse.class)
                .getAccessToken();

        //  Create EcoNewsClient (CORE API) with token
        client = new EcoNewsClient(
                testValueProvider.getGreencityAPIUrl(),
                token
        );
    }

    @Test
    public void getTagsByLanguageTest() {

        Response response = client.getTags("en");

        Assert.assertEquals(response.getStatusCode(), 200);

        TagResponse[] tags = response.as(TagResponse[].class);

        Assert.assertTrue(tags.length > 0, "Tags list should not be empty");

        for (TagResponse tag : tags) {
            Assert.assertNotNull(tag.getName());
            Assert.assertTrue(tag.getId() > 0, "Tag ID should be greater than 0");
        }
    }
}