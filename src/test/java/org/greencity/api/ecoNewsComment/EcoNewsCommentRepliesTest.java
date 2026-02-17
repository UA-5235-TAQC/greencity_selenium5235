package org.greencity.api.ecoNewsComment;

import io.restassured.response.Response;
import org.greencity.api.testrunners.CreateCommentRunner;
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
        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, 0, -1, null);
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request");

        List<String> invalidSort1 = List.of("foo");
        List<String> invalidSort2 = List.of("createdDate, up");
        List<String> invalidSort3 = List.of("text, descending");

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, 0, 10, invalidSort1);
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected 400 for invalid sort: 'foo'");

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, 0, 10, invalidSort2);
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected 400 for invalid sort: 'createdDate,up'");

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, 0, 10, invalidSort3);
        Assert.assertEquals(response.getStatusCode(), 400,
                "Expected 400 for invalid sort: 'text,descending'");
    }

    @Test
    public void testGetActiveRepliesShouldReturn404() {
        long nonExistingParentCommentId = subCommentIdWithImages + 1;
        Response response = ecoNewsCommentClient.getActiveReplies(nonExistingParentCommentId);
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 Not Found");
    }
}
