package org.greencity.api.ecoNewsComment;

import io.restassured.response.Response;
import org.greencity.api.testrunners.CreateCommentRunner;
import org.greencity.utils.api.ErrorResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EcoNewsCommentRepliesTest extends CreateCommentRunner {

    @Test
    public void testGetActiveReplies() {
        Response response = ecoNewsCommentClient.getActiveReplies(parentCommentId);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 OK");
        response = ecoNewsCommentClient.getActiveReplies(
                parentCommentId, 0, 10, List.of("createdDate, desc"));
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 OK");
    }

    @Test
    public void testGetActiveRepliesShouldReturn400() {
        Response response = ecoNewsCommentClient.getActiveReplies(parentCommentId, -1, 10, null);
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request");
        ErrorResponse error = response.as(ErrorResponse.class);
        Assert.assertEquals(error.getMessage(),
                "page must be a positive number",
                "Message should match expected");

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, 0, -1, null);
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request");
        error = response.as(ErrorResponse.class);
        Assert.assertEquals(error.getMessage(),
                "size must be a positive number",
                "Message should match expected");

        String unsupportedSortParameter = "foo";
        String invalidSortParameter = "foo";
        String invalidSortParameterValue = "descending";

        List<String> unsupportedSort = List.of(unsupportedSortParameter);
        List<String> invalidSort = List.of("createdDate", invalidSortParameter);
        List<String> invalidSort3 = List.of("text", invalidSortParameterValue);

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, 0, 10, unsupportedSort);
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected 400 for invalid sort");
        error = response.as(ErrorResponse.class);
        Assert.assertEquals(error.getMessage(),
                "Unsupported value for sorting: [" + unsupportedSortParameter + "]",
                "Message should match expected");

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, 0, 10, invalidSort);
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected 400 for invalid sort: 'createdDate,up'");
        error = response.as(ErrorResponse.class);
        Assert.assertEquals(error.getMessage(),
                "Invalid value '" + invalidSortParameter +
                        "' for orders given; Has to be either 'desc' or 'asc' (case insensitive)",
        "Message should match expected");

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, 0, 10, invalidSort3);
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected 400 for invalid sort: 'text,descending'");
        error = response.as(ErrorResponse.class);
        Assert.assertEquals(error.getMessage(),
                "Invalid value '" + invalidSortParameterValue +
                        "' for orders given; Has to be either 'desc' or 'asc' (case insensitive)",
                "Message should match expected");
    }

    @Test
    public void testGetActiveRepliesShouldReturn404() {
        long nonExistingParentCommentId = subCommentIdWithImages + 1;
        Response response = ecoNewsCommentClient.getActiveReplies(nonExistingParentCommentId);
        Assert.assertEquals(response.getStatusCode(), 404,
                "Expected 404 Not Found");
        ErrorResponse error = response.as(ErrorResponse.class);
        Assert.assertEquals(error.getMessage(),
        "Comment doesn't exist by this id: " + nonExistingParentCommentId,
                "Message should match expected");
    }
}
