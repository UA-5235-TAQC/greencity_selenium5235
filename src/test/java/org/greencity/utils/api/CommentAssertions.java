package org.greencity.utils.api;

import org.greencity.api.models.ecoNewsComment.GetCommentResponse;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;

public final class CommentAssertions {

    private CommentAssertions() {
    }

    public static void assertCommentResponse(GetCommentResponse comment,
                                             int expectedId,
                                             int expectedParentId,
                                             String expectedText,
                                             LocalDate expectedCreatedDate,
                                             LocalDate expectedModifiedDate,
                                             Integer expectedAuthorId,
                                             String expectedAuthorName,
                                             int expectedReplies,
                                             int expectedLikes,
                                             int expectedDislikes,
                                             String[] expectedImages,
                                             boolean expectedCurrentUserLiked,
                                             boolean expectedCurrentUserDisliked,
                                             boolean checkImagesNotNull) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(comment.getId(), expectedId,
                "Comment ID should match");
        softAssert.assertEquals(comment.getParentCommentId(), expectedParentId,
                "ParentCommentId should match");
        softAssert.assertEquals(comment.getText(), expectedText,
                "Text should match");

        LocalDate created = comment.getCreatedDate();
        LocalDate modified = comment.getModifiedDate();
        softAssert.assertNotNull(created, "Created date should not be null");
        softAssert.assertNotNull(modified, "Modified date should not be null");
        softAssert.assertEquals(created, expectedCreatedDate,
                "Created date should match expected");
        softAssert.assertEquals(modified, expectedModifiedDate,
                "Modified date should match expected");

        softAssert.assertEquals(comment.getReplies(), expectedReplies,
                "Replies count should match");
        softAssert.assertEquals(comment.getLikes(), expectedLikes,
                "Likes should match");
        softAssert.assertEquals(comment.getDislikes(), expectedDislikes,
                "Dislikes should match");

        softAssert.assertNotNull(comment.getAuthor(),
                "Author should not be null");
        softAssert.assertEquals(Integer.valueOf(comment.getAuthor().getId()), expectedAuthorId,
                "Author ID should match");
        softAssert.assertEquals(comment.getAuthor().getName(), expectedAuthorName,
                "Author name should match");

        softAssert.assertEquals(comment.isCurrentUserLiked(), expectedCurrentUserLiked,
                "currentUserLiked should match expected");
        softAssert.assertEquals(comment.isCurrentUserDisliked(), expectedCurrentUserDisliked,
                "currentUserDisliked should match expected");

        if (checkImagesNotNull) {
            softAssert.assertNotNull(comment.getAdditionalImages(),
                    "Additional images should not be null");
            softAssert.assertEquals(comment.getAdditionalImages(), expectedImages,
                    "Additional images should match expected");
        } else {
            softAssert.assertNull(comment.getAdditionalImages(),
                    "Additional images should be null");
        }

        softAssert.assertAll();
    }
}
