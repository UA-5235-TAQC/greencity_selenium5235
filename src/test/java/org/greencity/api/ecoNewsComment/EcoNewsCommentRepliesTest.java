package org.greencity.api.ecoNewsComment;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.ecoNewsComment.GetCommentPageResponse;
import org.greencity.api.models.ecoNewsComment.GetCommentResponse;
import org.greencity.api.testrunners.CreateCommentRunner;
import org.greencity.utils.api.CommentAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.greencity.utils.api.ApiTestAssertions.*;
import static org.greencity.utils.api.EcoNewsCommentFactory.ANOTHER_SUB_COMMENT;

@Epic("EcoNewsComment API")
@Feature("EcoNews Comments")
@Story("Verify replies and replies count for EcoNews comments")
@Severity(SeverityLevel.NORMAL)
@Tag("EcoNewsComment API")
public class EcoNewsCommentRepliesTest extends CreateCommentRunner {

    @Test
    @Description("Verify that active replies for a comment can be retrieved successfully without query parameters.")
    public void testGetActiveRepliesDefault() {
        Response response = ecoNewsCommentClient.getActiveReplies(commentIdWithImages);
        assertOk(response);

        GetCommentPageResponse pageResponse = response.as(GetCommentPageResponse.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(pageResponse.getPage(),
                "Page list should not be null");
        softAssert.assertEquals(pageResponse.getTotalElements(), 2,
                "Comment with images should have exactly 2 active replies");
        softAssert.assertEquals(pageResponse.getCurrentPage(), 0,
                "Default current page should be 0");
        softAssert.assertEquals(pageResponse.getTotalPages(), 1,
                "The amount of pages should be 1");
        softAssert.assertAll();

        GetCommentResponse firstActiveReply = pageResponse.getPage().getFirst();

        response = ecoNewsCommentClient.getComment(subCommentIdWithImages);
        GetCommentResponse createdActiveReply = response.as(GetCommentResponse.class);

        CommentAssertions.assertCommentResponse(
                firstActiveReply,
                createdActiveReply.getId(),
                createdActiveReply.getParentCommentId(),
                createdActiveReply.getText(),
                createdActiveReply.getCreatedDate(),
                createdActiveReply.getModifiedDate(),
                createdActiveReply.getAuthor().getId(),
                createdActiveReply.getAuthor().getName(),
                createdActiveReply.getReplies(),
                createdActiveReply.getLikes(),
                createdActiveReply.getDislikes(),
                createdActiveReply.getAdditionalImages(),
                false,
                false,
                true);
    }

    @Test
    @Description("Verify that the system returns 400 Bad Request when invalid " +
            "pagination or sorting parameters are provided.")
    public void testGetActiveRepliesShouldReturn400() {

        // --- Invalid page ---
        Response response = ecoNewsCommentClient
                .getActiveReplies(parentCommentId, -1, 10, null);

        assertBadRequest(response, "page must be a positive number");

        // --- Invalid size ---
        response = ecoNewsCommentClient
                .getActiveReplies(parentCommentId, 0, -1, null);

        assertBadRequest(response, "size must be a positive number");

        // --- Unsupported sort field ---
        List<String> unsupportedSortField = List.of("foo");

        response = ecoNewsCommentClient
                .getActiveReplies(parentCommentId, 0, 10, unsupportedSortField);

        assertBadRequest(response,
                "Unsupported value for sorting: [foo]");

        // --- Invalid sort direction (missing asc/desc) ---
        List<String> missingSortDirection = List.of("createdDate", "foo");

        response = ecoNewsCommentClient
                .getActiveReplies(parentCommentId, 0, 10, missingSortDirection);

        assertBadRequest(response,
                "Invalid value 'foo' for orders given; Has to be either 'desc' or 'asc' (case insensitive)");

        // --- Invalid sort direction value ---
        List<String> invalidSortDirection = List.of("text", "descending");

        response = ecoNewsCommentClient
                .getActiveReplies(parentCommentId, 0, 10, invalidSortDirection);

        assertBadRequest(response,
                "Invalid value 'descending' for orders given; " +
                        "Has to be either 'desc' or 'asc' (case insensitive)");
    }

    @Test
    @Description("Verify that the system returns 404 Not Found when requesting " +
            "replies for a non-existing comment.")
    public void testGetActiveRepliesShouldReturn404() {
        long nonExistingParentCommentId = subCommentIdWithImages + 10;
        Response response = ecoNewsCommentClient.getActiveReplies(nonExistingParentCommentId);
        assertNotFound(
                response,
                "Comment doesn't exist by this id: " + nonExistingParentCommentId
        );
    }

    @Test
    @Description("This test verifies that an authorized user can successfully create a new EcoNews item " +
            "and receive a valid response with generated ID and correct data.")
    public void testCountActiveReplies() {

        assertRepliesCount(parentCommentId, 1,
                "Parent comment should have exactly 1 active reply");

        assertRepliesCount(commentIdWithImages, 2,
                "Comment with images should have exactly 2 active replies");

        assertRepliesCount(parentSubCommentId, 0,
                "Parent subcomment should have no active replies");

        assertRepliesCount(subCommentId, 0,
                "Subcomment should have no active replies");

        assertRepliesCount(subCommentIdWithImages, 0,
                "Subcomment with images should have no active replies");
    }

    private void assertRepliesCount(long commentId, int expectedCount, String message) {
        Response response = ecoNewsCommentClient.countActiveReplies(commentId);
        assertOk(response);
        int actualCount = response.as(Integer.class);
        Assert.assertEquals(actualCount, expectedCount, message);
    }

    @Test
    @Description("Verify that the system returns 404 Not Found when counting " +
            "replies for a non-existing comment.")
    public void testCountActiveRepliesShouldReturn404() {
        long nonExistingId = subCommentIdWithImages + 10;
        Response response =
                ecoNewsCommentClient.countActiveReplies(nonExistingId);
        assertNotFound(
                response,
                "Comment doesn't exist by this id: " + nonExistingId
        );

        nonExistingId = -1;
        response = ecoNewsCommentClient.countActiveReplies(nonExistingId);
        assertNotFound(
                response,
                "Comment doesn't exist by this id: " + nonExistingId
        );
    }

    @Test
    @Description("Verify that the system returns error when replying to a reply")
    public void testReplyToPeplyShouldReturnError() {
        Response response = ecoNewsCommentClient.addComment(ecoNewsId, ANOTHER_SUB_COMMENT, parentSubCommentId);
        assertBadRequest(response, "You can't reply on reply");
    }
}
