package org.greencity.api.models.ecoNewsComment;

import lombok.Data;

@Data
public class AddCommentRequest {
    private String text;
    private int parentCommentId;

    public AddCommentRequest(String text, int parentCommentId) {
        this.text = text;
        this.parentCommentId = parentCommentId;
    }
}
