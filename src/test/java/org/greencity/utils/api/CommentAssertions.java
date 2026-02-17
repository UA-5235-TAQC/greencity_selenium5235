package org.greencity.utils.api;

import org.greencity.api.models.ecoNewsComment.GetCommentPageResponse;
import org.greencity.api.models.ecoNewsComment.GetCommentResponse;
import org.testng.asserts.SoftAssert;

public final class CommentAssertions {

    private CommentAssertions() {}

    public static void assertCommentResponse(GetCommentResponse actual,
                                             GetCommentResponse expected) {

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(actual, "Actual comment should not be null");
        softAssert.assertNotNull(expected, "Expected comment should not be null");

        softAssert.assertEquals(actual.getId(), expected.getId(),
                "Comment ID should match");

        softAssert.assertEquals(actual.getParentCommentId(),
                expected.getParentCommentId(),
                "ParentCommentId should match");

        softAssert.assertEquals(actual.getText(),
                expected.getText(),
                "Text should match");

        softAssert.assertEquals(actual.getCreationDate(),
                expected.getCreationDate(),
                "Created date should match");

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

        // Author
        softAssert.assertNotNull(actual.getAuthor(),
                "Author should not be null");

        softAssert.assertEquals(actual.getAuthor().getId(),
                expected.getAuthor().getId(),
                "Author ID should match");

        softAssert.assertEquals(actual.getAuthor().getName(),
                expected.getAuthor().getName(),
                "Author name should match");

        // User reactions
        softAssert.assertEquals(actual.isCurrentUserLiked(),
                expected.isCurrentUserLiked(),
                "currentUserLiked should match");

        softAssert.assertEquals(actual.isCurrentUserDisliked(),
                expected.isCurrentUserDisliked(),
                "currentUserDisliked should match");

        // Images
        String[] actualImages = actual.getAdditionalImages();
        String[] expectedImages = expected.getAdditionalImages();

        if (expectedImages == null || expectedImages.length == 0) {
            softAssert.assertTrue(
                    actualImages == null || actualImages.length == 0,
                    "Additional images should be null or empty"
            );
        } else {
            softAssert.assertNotNull(actualImages,
                    "Additional images should not be null");

            softAssert.assertEquals(actualImages.length,
                    expectedImages.length,
                    "Additional images length should match");

            for (int i = 0; i < actualImages.length; i++) {
                softAssert.assertEquals(actualImages[i],
                        expectedImages[i],
                        "Additional image at index " + i + " should match");
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
