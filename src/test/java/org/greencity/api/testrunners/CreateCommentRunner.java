package org.greencity.api.testrunners;

import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.greencity.api.clients.EcoNewsCommentClient;
import org.greencity.api.models.ecoNewsComment.AddCommentResponse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import static org.greencity.utils.api.ApiTestAssertions.assertCreated;
import static org.greencity.utils.api.ApiTestAssertions.assertOk;
import static org.greencity.utils.api.EcoNewsCommentFactory.*;

public class CreateCommentRunner extends CreateNewsRunner {

    protected EcoNewsCommentClient ecoNewsCommentClient;

    protected List<Integer> createdCommentIds = new ArrayList<>();
    protected int parentCommentId;
    protected int parentSubCommentId;
    protected int commentIdWithImages;
    protected int subCommentId;
    protected int subCommentIdWithImages;

    @BeforeClass
    @Description("Create comments for testing")
    public void createComment() {

        ecoNewsCommentClient = new EcoNewsCommentClient(
                testValueProvider.getGreencityAPIUrl(),
                accessToken
        );

        parentCommentId = createCommentAndGetId(
                ecoNewsId,
                PARENT_COMMENT,
                0
        );

        commentIdWithImages = createCommentAndGetId(
                ecoNewsId,
                COMMENT_WITH_IMAGES,
                0,
                COMMENT_IMAGES_PATHS
        );

        parentSubCommentId = createCommentAndGetId(
                ecoNewsId,
                PARENT_SUB_COMMENT,
                parentCommentId,
                PARENT_SUB_COMMENT_IMAGE_PATH
        );

        subCommentId = createCommentAndGetId(
                ecoNewsId,
                SUB_COMMENT,
                commentIdWithImages
        );

        subCommentIdWithImages = createCommentAndGetId(
                ecoNewsId,
                SUB_COMMENT_WITH_IMAGES,
                commentIdWithImages,
                COMMENT_IMAGES_PATHS
        );

        createdCommentIds.add(parentCommentId);
        createdCommentIds.add(commentIdWithImages);
        createdCommentIds.add(parentSubCommentId);
        createdCommentIds.add(subCommentId);
        createdCommentIds.add(subCommentIdWithImages);
    }

    protected int createCommentAndGetId(long newsId,
                                        String text,
                                        int parentId,
                                        String... imagePaths) {

        Response response = ecoNewsCommentClient
                .addComment(newsId, text, parentId, imagePaths);
        assertCreated(response);

        return response
                .as(AddCommentResponse.class)
                .getId();
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
            assertOk(response);
        }
    }
}
