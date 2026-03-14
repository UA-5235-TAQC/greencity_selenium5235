package org.greencity.utils.api;

import io.qameta.allure.Step;
import org.greencity.api.models.ecoNewsComment.AddCommentResponse;
import org.greencity.api.models.ecoNewsComment.GetCommentPageResponse;
import org.greencity.api.models.ecoNewsComment.GetCommentResponse;
import org.greencity.api.utils.DateUtil;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public final class CommentAssertions {

    public static void assertCommentResponse(
            GetCommentResponse actual, GetCommentResponse expected
    ) {
        Assert.assertNotNull(actual, "Actual comment should not be null");
        Assert.assertNotNull(expected, "Expected comment should not be null");

        verifyCommentResponse(
                actual,
                expected.getText(),
                expected.getAuthor().getName(),
                (actual.getAdditionalImages() == null ? 0 : actual.getAdditionalImages().size())
        );

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actual.getId(), expected.getId(),
                "Comment ID should match");

        softAssert.assertEquals(actual.getParentCommentId(),
                expected.getParentCommentId(),
                "ParentCommentId should match");

        softAssert.assertEquals(actual.getModificationDate(),
                expected.getModificationDate(),
                "Modified date should match");

        softAssert.assertEquals(actual.getReplies(),
                expected.getReplies(),
                "Replies count should match");

        softAssert.assertEquals(actual.getLikes(),
                expected.getLikes(),
                "Likes should match");

        softAssert.assertEquals(actual.getDislikes(),
                expected.getDislikes(),
                "Dislikes should match");

        softAssert.assertEquals(actual.getStatus(),
                expected.getStatus(),
                "Status should match");

        softAssert.assertNotNull(actual.getAuthor(),
                "Author should not be null");

        softAssert.assertEquals(actual.getAuthor().getId(),
                expected.getAuthor().getId(),
                "Author ID should match");

        softAssert.assertEquals(actual.getAuthor().getName(),
                expected.getAuthor().getName(),
                "Author name should match");

        softAssert.assertEquals(actual.isCurrentUserLiked(),
                expected.isCurrentUserLiked(),
                "currentUserLiked should match");

        softAssert.assertEquals(actual.isCurrentUserDisliked(),
                expected.isCurrentUserDisliked(),
                "currentUserDisliked should match");

        softAssert.assertAll();
    }

    @Step("Verify comment response: text='{expectedText}', images count='{expectedImagesCount}'")
    public static void verifyCommentResponse(
            AddCommentResponse response, String expectedText, String expectedAuthorName, int expectedImagesCount) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(response.getText(), expectedText, "Comment text mismatch!");
        softAssert.assertEquals(response.getAuthor().getName(), expectedAuthorName, "Author name mismatch!");

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

    public static void assertPageMeta(GetCommentPageResponse pageResponse,
                                      int expectedTotalElements,
                                      int expectedCurrentPage) {

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(pageResponse.getPage(),
                "Page list should not be null");

        int totalElements = pageResponse.getTotalElements();
        softAssert.assertEquals(totalElements,
                expectedTotalElements,
                "Total elements should match");

        softAssert.assertEquals(pageResponse.getCurrentPage(),
                expectedCurrentPage,
                "Current page should match");

        int pageSize = pageResponse.getPage().size();
        int expectedTotalPages = (int) Math.ceil((double) totalElements / pageSize);

        softAssert.assertEquals(pageResponse.getTotalPages(),
                expectedTotalPages,
                "Total pages should match");

        softAssert.assertAll();
    }
}
