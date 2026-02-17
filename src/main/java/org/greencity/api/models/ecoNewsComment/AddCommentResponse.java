package org.greencity.api.models.ecoNewsComment;

import lombok.Data;
import org.greencity.api.models.AuthorResponse;

import java.util.List;

@Data
public class AddCommentResponse {
    private int id;
    private AuthorResponse author;
    private String text;
    private String createdDate;
    private List<String> additionalImages;
}
