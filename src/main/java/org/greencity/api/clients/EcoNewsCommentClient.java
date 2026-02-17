package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.greencity.api.models.ecoNewsComment.AddCommentRequest;

public class EcoNewsCommentClient extends BaseApiClient{
    protected final String resourcePath = "/eco-news/";

    public EcoNewsCommentClient(String baseUrl, String token) {
        super(baseUrl, token);
    }

    @Step("API: Add comment to news ID {newsId} with text: '{text}'")
    public Response addComment(int newsId, String text, int parentComId, String... imagePaths){
        String jsonRequest = serializeAddComment(text, parentComId);

        RequestSpecification request = prepareRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("request", jsonRequest, "application/json");

        attachImagesToMultipart(request, "images", imagePaths);

        return request
                .post(this.resourcePath + newsId + "/comments")
                .then()
                .log().ifValidationFails()
                .extract()
                .response();
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
    public Response getComment(int commentId){
       return prepareRequest()
                .contentType(ContentType.JSON)
                .get(this.resourcePath + "comments/" + commentId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    @Step("API: Like a comment by ID {commentId}")
    public Response likeComment(int commentId){
        return prepareRequest()
                .contentType(ContentType.JSON)
                .queryParam("commentId", commentId)
                .post(this.resourcePath + "comments/like")
                .then()
                .log().ifValidationFails()
                .extract()
                .response();
    }

    @Step("API: Delete comment by ID {commentId}")
    public Response deleteComment(int commentId){
        return prepareRequest()
                .contentType(ContentType.JSON)
                .delete(this.resourcePath + "comments/" + commentId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
