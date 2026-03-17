package org.greencity.api.models.ecoNewsComment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCommentRequest {
    private String text;
    private int parentCommentId;
}
