package org.greencity.utils.api;

import io.restassured.response.Response;
import org.testng.Assert;

public final class ApiTestAssertions {

    private ApiTestAssertions() {}

    /**
     * Asserts that the response is 400 Bad Request and the error message matches.
     *
     * @param response        Response from API call
     * @param expectedMessage Expected error message
     */
    public static void assertBadRequest(Response response, String expectedMessage) {
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected 400 Bad Request");
        assertErrorMessage(response, expectedMessage);
    }

    /**
     * Asserts that the response is 404 Not Found and the error message matches.
     *
     * @param response        Response from API call
     * @param expectedMessage Expected error message
     */
    public static void assertNotFound(Response response, String expectedMessage) {
        Assert.assertEquals(response.getStatusCode(), 404,
                "Expected 404 Not Found");
        assertErrorMessage(response, expectedMessage);
    }

    /**
     * Asserts that the response is 200 OK.
     *
     * @param response Response from API call
     */
    public static void assertOk(Response response) {
        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected 200 OK");
    }

    /**
     * Asserts that the response is 201 Created.
     */
    public static void assertCreated(Response response) {
        Assert.assertEquals(response.getStatusCode(), 201,
                "Expected 201 Created");
    }

    /**
     * Asserts that the response is 401 Unauthorized and the error message is "Unauthorized".
     *
     * @param response Response from API call
     */
    public static void assertUnauthorized(Response response) {
        Assert.assertEquals(response.getStatusCode(), 401,
                "Expected 401 Unauthorized");
        ErrorResponse error = response.as(ErrorResponse.class);
        Assert.assertEquals(error.getError(), "Unauthorized",
                "Error message should match expected");
    }

    public static void assertErrorMessage(Response response, String expectedMessage) {
        ErrorResponse error = response.as(ErrorResponse.class);
        Assert.assertEquals(error.getMessage(), expectedMessage,
                "Error message should match expected");
    }
}
