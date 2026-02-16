package org.greencity.api.testrunners;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.models.econews.EcoNewsPageResponse;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

public class EcoNewsWithoutTokenRunner extends ApiTestRunner {
    protected EcoNewsClient ecoNewsClient;
    protected long ecoNewsId;
    protected EcoNewsPageResponse pageResponse;
    protected EcoNewsResponse firstNews;

    @BeforeClass
    @Description("Get first EcoNews before each test")
    public void setUpEcoNewsClient() {
        ObjectMapper mapper = new ObjectMapper();

        RestAssured.config = RestAssured.config().objectMapperConfig(
                RestAssured.config().getObjectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> mapper)
        );

        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl());

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("author-id", testValueProvider.getUserId());
        queryParams.put("favorite", false);
        queryParams.put("page", 0);
        queryParams.put("size", 20);

        Response response = ecoNewsClient.getEcoNews(queryParams);
        Assert.assertEquals(response.getStatusCode(), 200);
        pageResponse = response.as(EcoNewsPageResponse.class);

        firstNews = pageResponse.getPage().getFirst();
        ecoNewsId = firstNews.getId();
    }
}
