package org.greencity.api.testrunners;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.parsing.Parser;
import org.greencity.utils.TestValueProvider;
import org.testng.annotations.BeforeSuite;

public class ApiTestRunner {

    protected static TestValueProvider testValueProvider;

    @BeforeSuite
    public void setUp() {
        testValueProvider = new TestValueProvider();
        RestAssured.registerParser("application/json", Parser.JSON);
        RestAssured.config = RestAssuredConfig.config()
                .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
    }
}
