package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.greencity.api.models.ecoNewsComment.AddCommentRequest;
import org.greencity.api.models.ecoNewsComment.GetCommentPageResponse;
import org.greencity.api.models.ecoNewsComment.GetCommentResponse;

import java.util.List;

public class EcoNewsCommentClient extends BaseApiClient {
    protected final String resourcePath = "/eco-news/";

    public EcoNewsCommentClient(String baseUrl, String token) {
        super(baseUrl, token);
    }

    @Step("API: Add comment to news ID {newsId} with text: '{text}'")
    public Response addComment(long newsId, String text, int parentComId, String... imagePaths) {
        AddCommentRequest commentBody = new AddCommentRequest(text, parentComId);
        String jsonRequest = serialize(commentBody);
        RequestSpecification request = prepareRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("request", jsonRequest, "application/json");
        attachImagesToMultipart(request, "images", imagePaths);
        return execute(request
                .post(this.resourcePath + newsId + "/comments"));
    }

    @Step("API: Get comment details by ID {commentId}")
    public Response getComment(int commentId) {
        return execute(prepareRequest()
                .contentType(ContentType.JSON)
                .get(this.resourcePath + "comments/" + commentId));
    }

    @Step("API: Like a comment by ID {commentId}")
    public Response likeComment(int commentId) {
        return execute(prepareRequest()
                .contentType(ContentType.JSON)
                .queryParam("commentId", commentId)
                .post(this.resourcePath + "comments/like"));
    }

    @Step("API: Delete comment by ID {commentId}")
    public Response deleteComment(int commentId) {
        return execute(prepareRequest()
                .contentType(ContentType.JSON)
                .delete(this.resourcePath + "comments/" + commentId));
    }

    @Step("API: Get all active replies for comment ID {parentCommentId}, page {page}, size {size}, sort {sort}")
    public Response getActiveReplies(long parentCommentId, Integer page, Integer size, List<String> sort) {
        RequestSpecification request = prepareRequest()
                .contentType("application/json");

        if (page != null) request.queryParam("page", page);
        if (size != null) request.queryParam("size", size);
        if (sort != null && !sort.isEmpty()) request.queryParam("sort", String.join(",", sort));

        return execute(request
                .get(this.resourcePath + "comments/" + parentCommentId + "/replies/active"));
    }

    public Response getActiveReplies(long parentCommentId) {
        return getActiveReplies(parentCommentId, 0, 20, null);
    }

    @Step("API: Delete comment by ID {commentId} along with all child comments")
    public Response deleteCommentWithChildren(int commentId) {
        Response repliesResponse = getActiveReplies(commentId);
        GetCommentPageResponse repliesList = repliesResponse.as(GetCommentPageResponse.class);

        if (repliesList.getPage() != null) {
            for (GetCommentResponse reply : repliesList.getPage()) {
                deleteCommentWithChildren(reply.getId());
            }
        }
        return deleteComment(commentId);
    }
}
