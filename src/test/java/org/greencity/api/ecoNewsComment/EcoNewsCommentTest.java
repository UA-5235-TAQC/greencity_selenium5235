package org.greencity.api.ecoNewsComment;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.EcoNewsCommentClient;
import org.greencity.api.models.ecoNewsComment.AddCommentResponse;
import io.qameta.allure.*;
import org.greencity.api.models.ecoNewsComment.GetCommentResponse;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.testrunners.FirstUserRunner;
import org.greencity.api.testrunners.SecondUserRunner;
import org.greencity.utils.api.EcoNewsDtoFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.greencity.utils.api.ApiTestAssertions.assertCreated;
import static org.greencity.utils.api.ApiTestAssertions.assertOk;
import static org.greencity.utils.api.CommentAssertions.verifyCommentResponse;
import static org.greencity.utils.api.EcoNewsCommentFactory.COMMENT_IMAGES_PATHS;

@Epic("Eco News")
@Feature("Comments")
public class EcoNewsCommentTest extends FirstUserRunner {
    private EcoNewsCommentClient ecoNewsCommentClient;
    private EcoNewsClient ecoNewsClient;
    private final EcoNewsRequest newsRequestBody = EcoNewsDtoFactory.createTestNews();
    private Long newsId;
    private int commentIdWithImage;
    private int commentId;
    private int subCommentId;
    private String author;
    private SecondUserRunner secondUser;

    @BeforeClass
    public void setUpEcoNewsComment() {
        this.ecoNewsCommentClient = new EcoNewsCommentClient(testValueProvider.getGreencityAPIUrl(), accessToken);
        this.ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), accessToken);
        Response response = ecoNewsClient.postEcoNews(newsRequestBody);
        assertCreated(response);
        this.newsId = response.as(EcoNewsResponse.class).getId();
        this.author = testValueProvider.getUserName();
        secondUser = new SecondUserRunner();
        secondUser.loginSecondUser();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("User should be able to add a comment without images")
    @Description("Verified that a text-only comment is successfully created and returns correct data.")
    public void addCommentTest() {
        Response response = ecoNewsCommentClient.addComment(newsId, "Test comment from API Automation", 0);
        assertCreated(response);

        AddCommentResponse responseBody = response.as(AddCommentResponse.class);
        this.commentId = responseBody.getId();
        String text = "Test comment from API Automation";
        verifyCommentResponse(responseBody, text, this.author, 0);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("User should be able to add a comment with images")
    public void addCommentWithImagesTest() {
        Response response = ecoNewsCommentClient.addComment(
                newsId, "Test comment from API Automation with images", 0, COMMENT_IMAGES_PATHS
        );
        assertCreated(response);

        AddCommentResponse responseBody = response.as(AddCommentResponse.class);
        this.commentIdWithImage = responseBody.getId();
        String text = "Test comment from API Automation with images";
        verifyCommentResponse(responseBody, text, this.author, COMMENT_IMAGES_PATHS.length);
    }

    @Test(dependsOnMethods = {"addCommentWithImagesTest"})
    @Severity(SeverityLevel.NORMAL)
    @Story("User should be able to reply to a comment with images")
    public void addSubCommentTestWithImages() {
        String text = "Test subComment from API Automation with images";
        Response response = ecoNewsCommentClient.addComment(
                newsId, text, commentIdWithImage, COMMENT_IMAGES_PATHS
        );
        assertCreated(response);
        AddCommentResponse responseBody = response.as(AddCommentResponse.class);
        this.subCommentId = responseBody.getId();
        verifyCommentResponse(responseBody, text, this.author, COMMENT_IMAGES_PATHS.length);
    }

    @Test(dependsOnMethods = {"addCommentTest"})
    @Severity(SeverityLevel.MINOR)
    @Story("User should be able to reply to a comment without images")
    public void addSubCommentTest() {
        String text = "Test subComment from API Automation";
        Response response = ecoNewsCommentClient.addComment(newsId, text, commentId);
        assertCreated(response);
        AddCommentResponse responseBody = response.as(AddCommentResponse.class);
        verifyCommentResponse(responseBody, text, this.author, 0);
    }

    @Test(dependsOnMethods = {"addSubCommentTest"})
    @Severity(SeverityLevel.MINOR)
    @Story("User should be able to delete the subcomment")
    public void deleteSubCommentTest() {
        Response response = ecoNewsCommentClient.deleteComment(subCommentId);
        assertOk(response);
    }

    @Test(dependsOnMethods = {"likeCommentTest"})
    @Severity(SeverityLevel.MINOR)
    @Story("User should be able to delete the comment")
    public void deleteCommentTest() {
        Response response = ecoNewsCommentClient.deleteComment(commentId);
        assertOk(response);
    }

    @Test(dependsOnMethods = {"addCommentTest"}, description = "Testing if GET comment by ID works correctly")
    @Severity(SeverityLevel.TRIVIAL)
    public void getCommentTest() {
        Response response = ecoNewsCommentClient.getComment(commentId);
        assertOk(response);
        GetCommentResponse responseBody = response.as(GetCommentResponse.class);
        Assert.assertEquals(
                responseBody.getId(), commentId, "The server returned an object with a different ID"
        );
    }

    @Test(dependsOnMethods = {"addCommentTest"}, description = "Like a comment")
    @Severity(SeverityLevel.TRIVIAL)
    public void likeCommentTest() {
        EcoNewsCommentClient secondUserEcoNewsCommentClient = secondUser.getEcoNewsCommentClient();
        Response response = secondUserEcoNewsCommentClient.likeComment(commentId);
        assertOk(response);
    }

    @AfterClass
    public void clearAfterTest() {
        Response response = ecoNewsClient.deleteEcoNewsById(newsId);
        assertOk(response);
    }
}
