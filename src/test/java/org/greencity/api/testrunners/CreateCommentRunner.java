package org.greencity.api.testrunners;

import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.greencity.api.clients.EcoNewsCommentClient;
import org.greencity.api.models.ecoNewsComment.AddCommentResponse;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import static org.greencity.utils.api.EcoNewsCommentFactory.*;

public class CreateCommentRunner extends CreateNewsRunner {

    protected EcoNewsCommentClient ecoNewsCommentClient;

    protected int parentCommentId;
    protected List<Integer> createdCommentIds = new ArrayList<>();
    protected int commentIdWithImages;
    protected int subCommentId;
    protected int subCommentIdWithImages;

    @BeforeClass
    @Description("Create comments for testing")
    public void createComment() {
        ecoNewsCommentClient = new EcoNewsCommentClient(testValueProvider.getGreencityAPIUrl(), accessToken);
        Response response = ecoNewsCommentClient
                .addComment(ecoNewsId, DEFAULT_COMMENT, 0);
        AddCommentResponse responseBody = response.as(AddCommentResponse.class);
        parentCommentId = responseBody.getId();
        response = ecoNewsCommentClient
                .addComment(ecoNewsId, COMMENT_WITH_IMAGES, 0, IMAGES_PATHS);
        responseBody = response.as(AddCommentResponse.class);
        commentIdWithImages = responseBody.getId();
        response = ecoNewsCommentClient.addComment(ecoNewsId, SUB_COMMENT, parentCommentId);
        responseBody = response.as(AddCommentResponse.class);
        subCommentId = responseBody.getId();
        response = ecoNewsCommentClient.addComment(ecoNewsId, SUB_COMMENT_WITH_IMAGES, commentIdWithImages);
        responseBody = response.as(AddCommentResponse.class);
        subCommentIdWithImages = responseBody.getId();
        createdCommentIds = List.of(
                parentCommentId,
                commentIdWithImages,
                subCommentId,
                subCommentIdWithImages
        );
    }

    @AfterClass
    @Description("Delete all created comments")
    public void deleteComments() {
        List<Integer> parentCommentIds = List.of(
                parentCommentId,
                commentIdWithImages
        );
        for (Integer commentId : parentCommentIds) {
            Response response = ecoNewsCommentClient.deleteCommentWithChildren(commentId);
            Assert.assertEquals(response.getStatusCode(), 200,
                    "Failed to delete comment with id: " + commentId);
        }
    }
}
