package org.greencity.api.testrunners;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.greencity.utils.TestValueProvider;
import org.testng.annotations.BeforeSuite;

public class ApiTestRunner {

    protected static TestValueProvider testValueProvider;


    @BeforeSuite
    public void setUp() {
        testValueProvider = new TestValueProvider();
        RestAssured.registerParser("application/json", Parser.JSON);
    }
}
