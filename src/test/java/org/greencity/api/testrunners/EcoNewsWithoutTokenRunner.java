package org.greencity.api.testrunners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import org.greencity.api.clients.EcoNewsClient;
import org.testng.annotations.BeforeClass;

public class EcoNewsWithoutTokenRunner extends ApiTestRunner {
    protected EcoNewsClient ecoNewsClient;

    @BeforeClass
    public void setUpEcoNewsClient() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        RestAssured.config = RestAssured.config().objectMapperConfig(
                RestAssured.config().getObjectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> mapper)
        );

        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl());
    }
}
