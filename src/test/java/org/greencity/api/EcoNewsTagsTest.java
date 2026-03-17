package org.greencity.api;

import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.TagResponse;
import org.greencity.api.testrunners.FirstUserRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import static org.greencity.utils.api.ApiTestAssertions.assertOk;


@Epic("EcoNews API")
@Feature("EcoNews Tags")
@Story("Get EcoNews tags by language")
@Severity(SeverityLevel.NORMAL)
@Tag("API")
public class EcoNewsTagsTest extends FirstUserRunner {

    @Test
    public void getTagsByLanguageTest() {

        Response response = ecoNewsClient.getTags("en");
        assertOk(response);

        TagResponse[] tags = response.as(TagResponse[].class);

        Assert.assertTrue(tags.length > 0, "Tags list should not be empty");

        for (TagResponse tag : tags) {
            Assert.assertNotNull(tag.getName());
            Assert.assertTrue(tag.getId() > 0, "Tag ID should be greater than 0");
        }
    }
}