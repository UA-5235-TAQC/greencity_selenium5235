package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.greencity.api.models.ecoNewsComment.CommentQuery;
import org.greencity.api.models.ecoNewsComment.GetCommentPageResponse;
import org.greencity.api.models.ecoNewsComment.GetCommentResponse;

import java.util.HashMap;
import java.util.Map;

public class EcoNewsCommentClient extends BaseApiClient {
    protected final String resourcePath = "/eco-news/";

    public EcoNewsCommentClient(String baseUrl, String token) {
        super(baseUrl, token);
    }

    @Step("API: Add comment to news ID {newsId} with text: '{text}'")
    public Response addComment(Long newsId, String text, int parentComId, String... imagePaths) {
        String jsonRequest = serializeAddComment(text, parentComId);
        RequestSpecification request = prepareRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("request", jsonRequest, "application/json");
        attachImagesToMultipart(request, "images", imagePaths);
        return execute(request.post(resourcePath + newsId + "/comments"));
    }

    private String serializeAddComment(String text, int parentComId) {
        if (text == null) text = "";

        String escapedText = text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

        return String.format("{\"text\":\"%s\",\"parentCommentId\":%d}", escapedText, parentComId);
    }

    @Step("API: Get comment details by ID {commentId}")
    public Response getComment(int commentId) {
        return get(resourcePath + "comments/" + commentId);
    }

    @Step("API: Like a comment by ID {commentId}")
    public Response likeComment(int commentId) {
        return execute(
                prepareRequest()
                        .queryParam("commentId", commentId)
                        .post(resourcePath + "comments/like")
        );
    }

    @Step("API: Delete comment by ID {commentId}")
    public Response deleteComment(int commentId) {
        return delete(resourcePath + "comments/" + commentId);
    }

    @Step("API: Get all active replies for comment ID {parentCommentId} with query params")
    public Response getActiveReplies(long parentCommentId, CommentQuery query) {
        Map<String, Object> queryParams = new HashMap<>();

        if (query != null) {
            if (query.getPage() != null) queryParams.put("page", query.getPage());
            if (query.getSize() != null) queryParams.put("size", query.getSize());
            if (query.getSort() != null && !query.getSort().isEmpty()) {
                queryParams.put("sort", query.getSort());
            }
        }

        return get(resourcePath + "comments/" + parentCommentId + "/replies/active", queryParams);
    }

    public Response getActiveReplies(long parentCommentId) {
        return getActiveReplies(parentCommentId, CommentQuery.builder().page(0).size(20).build());
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

    @Step("API: Count active replies for comment ID {parentCommentId}")
    public Response countActiveReplies(long parentCommentId) {
        return get(resourcePath + "comments/" + parentCommentId + "/replies/active/count");
    }
}
