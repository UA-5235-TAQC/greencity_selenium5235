package org.greencity.api.ecoNewsComment;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.ecoNewsComment.CommentQuery;
import org.greencity.api.models.ecoNewsComment.GetCommentPageResponse;
import org.greencity.api.models.ecoNewsComment.GetCommentResponse;
import org.greencity.api.testrunners.CreateCommentRunner;
import org.greencity.utils.api.CommentAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import static org.greencity.utils.api.ApiTestAssertions.*;
import static org.greencity.utils.api.EcoNewsCommentFactory.*;

@Epic("EcoNewsComment API")
@Feature("EcoNews Comments")
@Story("Verify replies and replies count for EcoNews comments")
@Severity(SeverityLevel.NORMAL)
@Tag("EcoNewsComment API")
public class EcoNewsCommentRepliesTest extends CreateCommentRunner {

    @Test
    @Description("Verify that active replies for a comment can be retrieved " +
            "successfully without query parameters.")
    public void testGetActiveRepliesDefault() {
        GetCommentPageResponse pageResponse = getActiveRepliesPage(commentIdWithImages, 0, 20, null);
        CommentAssertions.assertPageMeta(pageResponse, 2, 0);
        GetCommentResponse firstActiveReply = pageResponse.getPage().getFirst();
        GetCommentResponse createdActiveReply = getCommentById(subCommentIdWithImages);
        CommentAssertions.assertCommentResponse(firstActiveReply, createdActiveReply);
    }

    private GetCommentPageResponse getActiveRepliesPage(long parentCommentId, int page, int size, List<String> sort) {
        CommentQuery query = CommentQuery.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        Response response = ecoNewsCommentClient.getActiveReplies(parentCommentId, query);
        assertOk(response);

        return response.as(GetCommentPageResponse.class);
    }

    private GetCommentResponse getCommentById(int commentId) {
        Response response = ecoNewsCommentClient.getComment(commentId);
        assertOk(response);
        return response.as(GetCommentResponse.class);
    }

    @Test
    @Description("Verify that active replies for a comment can be retrieved " +
            "for a specific page (page=0, size=10).")
    public void testGetActiveRepliesWithPage() {
        GetCommentPageResponse pageResponse = getActiveRepliesPage(commentIdWithImages, 0, 10, null);

        CommentAssertions.assertPageMeta(pageResponse, 2, 0);

        GetCommentResponse secondActiveReply = pageResponse.getPage().get(1);
        GetCommentResponse createdActiveReply = getCommentById(subCommentId);

        CommentAssertions.assertCommentResponse(secondActiveReply, createdActiveReply);
    }

    @Test
    @Description("Verify that active replies for a comment can be retrieved " +
            "when page size is set to 1.")
    public void testGetActiveRepliesWithSizeOne() {
        GetCommentPageResponse pageResponse = getActiveRepliesPage(parentCommentId, 0, 1, null);

        Assert.assertEquals(pageResponse.getPage().size(), 1, "Page size should be 1");
        CommentAssertions.assertPageMeta(pageResponse, 1, 0);

        GetCommentResponse firstActiveReply = pageResponse.getPage().getFirst();
        GetCommentResponse createdActiveReply = getCommentById(parentSubCommentId);

        CommentAssertions.assertCommentResponse(firstActiveReply, createdActiveReply);
    }

    @Test
    @Description("""
        Verify that active replies are correctly sorted:
        1. By createdDate in descending order
        2. By modifiedDate in descending order
        3. By multiple fields (createdDate, modifiedDate) in descending order
        
        Also verifies:
        - Correct page metadata
        - Correct reply order positions
        """)
    public void testSortByDates() {

        int anotherSubCommentId = createCommentAndGetId(
                ecoNewsId,
                ANOTHER_SUB_COMMENT,
                commentIdWithImages
        );
        createdCommentIds.add(anotherSubCommentId);

        // -------- createdDate DESC --------

        List<String> sort = List.of("createdDate,desc");
        GetCommentPageResponse pageResponse =
                getActiveRepliesPage(commentIdWithImages, 0, 20, sort);

        List<GetCommentResponse> replies = pageResponse.getPage();

        assertSortedByDate(replies, GetCommentResponse::getCreatedDate, "createdDate");

        CommentAssertions.assertPageMeta(pageResponse, 3, 0);

        CommentAssertions.assertCommentResponse(
                replies.getFirst(),
                getCommentById(anotherSubCommentId)
        );

        // -------- modifiedDate DESC --------

        sort = List.of("modifiedDate,desc");
        pageResponse = getActiveRepliesPage(commentIdWithImages, 0, 20, sort);
        replies = pageResponse.getPage();

        assertSortedByDate(replies, GetCommentResponse::getModifiedDate, "modifiedDate");

        CommentAssertions.assertPageMeta(pageResponse, 3, 0);

        CommentAssertions.assertCommentResponse(
                replies.get(2),
                getCommentById(subCommentId)
        );

        // -------- createdDate + modifiedDate DESC --------

        sort = List.of("createdDate,desc", "modifiedDate,desc");
        pageResponse = getActiveRepliesPage(commentIdWithImages, 0, 20, sort);
        replies = pageResponse.getPage();

        assertSortedByDate(replies, GetCommentResponse::getCreatedDate, "createdDate");
        assertSortedByDate(replies, GetCommentResponse::getModifiedDate, "modifiedDate");

        CommentAssertions.assertPageMeta(pageResponse, 3, 0);

        CommentAssertions.assertCommentResponse(
                replies.get(1),
                getCommentById(subCommentIdWithImages)
        );
    }

    private void assertSortedByDate(
            List<GetCommentResponse> replies,
            Function<GetCommentResponse, String> dateExtractor,
            String fieldName
    ) {
        for (int i = 0; i < replies.size() - 1; i++) {

            LocalDateTime current =
                    LocalDateTime.parse(dateExtractor.apply(replies.get(i)));

            LocalDateTime next =
                    LocalDateTime.parse(dateExtractor.apply(replies.get(i + 1)));

            Assert.assertFalse(
                    current.isBefore(next),
                    "Replies should be sorted descending by " + fieldName
            );
        }
    }

    @Test
    @Description("Verify that comment without replies returns empty active replies page")
    public void testNoActiveReplies() {
        GetCommentPageResponse pageResponse = getActiveRepliesPage(subCommentId, 0, 20, null);
        CommentAssertions.assertPageMeta(pageResponse, 0, 0);
        Assert.assertTrue(
                pageResponse.getPage().isEmpty(),
                "Replies list should be empty"
        );
    }

    @Test
    @Description("Verify that the system returns 400 Bad Request when invalid " +
            "pagination or sorting parameters are provided.")
    public void testGetActiveRepliesShouldReturn400() {

        // --- Invalid page ---
        CommentQuery invalidPageQuery = CommentQuery.builder()
                .page(-1)
                .size(10)
                .build();

        Response response = ecoNewsCommentClient.getActiveReplies(parentCommentId, invalidPageQuery);
        assertBadRequest(response, "page must be a positive number");

        // --- Invalid size ---
        CommentQuery invalidSizeQuery = CommentQuery.builder()
                .page(0)
                .size(-1)
                .build();

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, invalidSizeQuery);
        assertBadRequest(response, "size must be a positive number");

        // --- Unsupported sort field ---
        CommentQuery unsupportedSortQuery = CommentQuery.builder()
                .page(0)
                .size(10)
                .sort(List.of("foo"))
                .build();

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, unsupportedSortQuery);
        assertBadRequest(response, "Unsupported value for sorting: [foo]");

        // --- Invalid sort direction (missing asc/desc) ---
        CommentQuery missingSortDirectionQuery = CommentQuery.builder()
                .page(0)
                .size(10)
                .sort(List.of("createdDate", "foo"))
                .build();

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, missingSortDirectionQuery);
        assertBadRequest(response, "Unsupported value for sorting: [foo]");

        // --- Invalid sort direction value ---
        CommentQuery invalidSortDirectionQuery = CommentQuery.builder()
                .page(0)
                .size(10)
                .sort(List.of("text", "descending"))
                .build();

        response = ecoNewsCommentClient.getActiveReplies(parentCommentId, invalidSortDirectionQuery);
        assertBadRequest(response, "Unsupported value for sorting: [text, descending]");
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
    public void testReplyToReplyShouldReturnError() {
        Response response = ecoNewsCommentClient.addComment(ecoNewsId, ANOTHER_SUB_COMMENT, parentSubCommentId);
        assertBadRequest(response, "You can't reply on reply");
    }
}
