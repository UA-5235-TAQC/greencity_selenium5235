package org.greencity.api;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.econews.TagResponse;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EcoNewsTagsTest extends ApiTestRunner {

    private EcoNewsClient client;

    @BeforeClass
    public void setUpClient() {

        //  Створюємо security client (USER API)
        OwnSecurityClient securityClient =
                new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());

        //  Логінимось
        Response loginResponse = securityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        //  Беремо токен
        String token = loginResponse
                .as(SignInResponse.class)
                .getAccessToken();

        //  Створюємо EcoNewsClient (CORE API) з токеном
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

        System.out.println(response.asString());

        for (TagResponse tag : tags) {
            
            Assert.assertNotNull(tag.getName());
            Assert.assertTrue(tag.getId() > 0);
        }
    }
}