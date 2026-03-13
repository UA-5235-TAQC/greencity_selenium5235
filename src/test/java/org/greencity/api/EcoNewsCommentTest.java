package org.greencity.api;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.EcoNewsCommentClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.ecoNewsComment.AddCommentResponse;
import io.qameta.allure.*;
import org.greencity.api.models.ecoNewsComment.GetCommentResponse;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.models.ownsecurity.SignInResponse;
import org.greencity.api.testrunners.ApiTestRunner;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.utils.api.DateUtil;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

@Epic("Eco News")
@Feature("Comments")
public class EcoNewsCommentTest extends ApiTestRunner {
    private EcoNewsCommentClient ecoNewsCommentClient;
    private EcoNewsClient ecoNewsClient;
    EcoNewsRequest newsRequestBody = EcoNewsRequest.builder()
            .title("News title for testing api comment controller")
            .text("News text for testing api comment controller. Should be more than 20 character, or no, I don't remember.")
            .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
            .source("https://example.com")
            .shortInfo("Short info")
            .build();
    private Long newsId;
    private final String[] imagesPaths = {
            "src/test/resources/images/test.jfif",
            "src/test/resources/images/test2.png",
    };
    private int commentIdWithImage;
    private int commentId;
    private int subCommentId;

    @BeforeClass
    public void setUpEcoNewsComment() {
        OwnSecurityClient securityClient = new OwnSecurityClient(testValueProvider.getBaseGreencityUserAPIUrl());
        Response signInResponseRaw = securityClient.signIn(testValueProvider.getUserEmail(), testValueProvider.getUserPassword());
        Assert.assertEquals(signInResponseRaw.getStatusCode(), 200, "Login failed during setup");
        String accessToken = signInResponseRaw.as(SignInResponse.class).getAccessToken();
        this.ecoNewsCommentClient = new EcoNewsCommentClient(testValueProvider.getGreencityAPIUrl(), accessToken);
        this.ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), accessToken);
        Response response = ecoNewsClient.postEcoNews(newsRequestBody);
        Assert.assertEquals(response.getStatusCode(), 201, "Eco News was not created!");
        this.newsId = response.as(EcoNewsResponse.class).getId();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("User should be able to add a comment without images")
    @Description("Verified that a text-only comment is successfully created and returns correct data.")
    public void addCommentTest() {
        Response response = ecoNewsCommentClient.addComment(newsId, "Test comment from API Automation", 0);
        Assert.assertEquals(response.getStatusCode(), 201);

        AddCommentResponse responseBody = response.as(AddCommentResponse.class);
        this.commentId = responseBody.getId();

        verifyCommentResponse(responseBody, "Test comment from API Automation", 0);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("User should be able to add a comment with images")
    public void addCommentWithImagesTest() {
        Response response = ecoNewsCommentClient.addComment(newsId, "Test comment from API Automation with images", 0, imagesPaths);
        Assert.assertEquals(response.getStatusCode(), 201);

        AddCommentResponse responseBody = response.as(AddCommentResponse.class);
        this.commentIdWithImage = responseBody.getId();
        verifyCommentResponse(responseBody, "Test comment from API Automation with images", imagesPaths.length);
    }

    @Test(dependsOnMethods = {"addCommentWithImagesTest"})
    @Severity(SeverityLevel.NORMAL)
    @Story("User should be able to reply to a comment with images")
    public void addSubCommentTestWithImages() {
        String text = "Test subComment from API Automation with images";
        Response response = ecoNewsCommentClient.addComment(newsId, text, commentIdWithImage, imagesPaths);
        Assert.assertEquals(response.getStatusCode(), 201);
        AddCommentResponse responseBody = response.as(AddCommentResponse.class);
        this.subCommentId = responseBody.getId();
        verifyCommentResponse(responseBody, text, imagesPaths.length);
    }

    @Test(dependsOnMethods = {"addCommentTest"})
    @Severity(SeverityLevel.MINOR)
    @Story("User should be able to reply to a comment without images")
    public void addSubCommentTest() {
        String text = "Test subComment from API Automation";
        Response response = ecoNewsCommentClient.addComment(newsId, text, commentId);
        Assert.assertEquals(response.getStatusCode(), 201);
        AddCommentResponse responseBody = response.as(AddCommentResponse.class);
        verifyCommentResponse(responseBody, text, 0);

    }

    @Test(dependsOnMethods = {"addSubCommentTest"})
    @Severity(SeverityLevel.MINOR)
    @Story("User should be able to delete the subcomment")
    public void deleteSubCommentTest() {
        Response response = ecoNewsCommentClient.deleteComment(subCommentId);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"deleteSubCommentTest"})
    @Severity(SeverityLevel.MINOR)
    @Story("User should be able to delete the comment")
    public void deleteCommentTest() {
        Response response = ecoNewsCommentClient.deleteComment(commentId);
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(dependsOnMethods = {"addCommentTest"}, description = "Testing if GET comment by ID works correctly")
    @Severity(SeverityLevel.TRIVIAL)
    public void getCommentTest() {
        Response response = ecoNewsCommentClient.getComment(commentId);
        Assert.assertEquals(response.getStatusCode(), 200);
        GetCommentResponse responseBody = response.as(GetCommentResponse.class);
        Assert.assertEquals(responseBody.getId(), commentId, "The server returned an object with a different ID");
    }

    @Test(dependsOnMethods = {"addCommentTest"}, description = "Like a comment")
    @Severity(SeverityLevel.TRIVIAL)
    public void likeCommentTest() {
        Response response = ecoNewsCommentClient.likeComment(3921);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Step("Verify comment response: text='{expectedText}', images count='{expectedImagesCount}'")
    private void verifyCommentResponse(AddCommentResponse response, String expectedText, int expectedImagesCount) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(response.getText(), expectedText, "Comment text mismatch!");
        softAssert.assertEquals(response.getAuthor().getName(), testValueProvider.getUserName(), "Author name mismatch!");

        String expectedDateTime = DateUtil.getCurrentDateTimeToMinutes();
        softAssert.assertTrue(response.getCreatedDate().contains(expectedDateTime),
                String.format("Creation date mismatch! Server: [%s], Expected contain: [%s] (UTC).",
                        response.getCreatedDate(), expectedDateTime));

        List<String> actualImages = response.getAdditionalImages();
        int actualCount = (actualImages == null) ? 0 : actualImages.size();

        softAssert.assertEquals(actualCount, expectedImagesCount, "Images count mismatch!");

        if (actualImages != null && !actualImages.isEmpty()) {
            for (String imageUrl : actualImages) {
                softAssert.assertNotNull(imageUrl, "Image URL is null");
                softAssert.assertTrue(imageUrl.startsWith("http"), "Image URL should start with 'http'");
                softAssert.assertTrue(imageUrl.toLowerCase().matches(".*\\.(jpg|jpeg|png|jfif|webp)$"),
                        "Invalid image format: " + imageUrl);
            }
        }
        softAssert.assertAll();
    }

    @AfterClass
    public void clearAfterTest() {
        Response response = ecoNewsClient.deleteEcoNewsById(newsId);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
