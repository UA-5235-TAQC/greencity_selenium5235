package org.greencity.api.models.ecoNewsComment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.greencity.api.models.AuthorResponse;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCommentResponse {
    private int id;
    private AuthorResponse author;
    private String text;
    private String createdDate;
    private List<String> additionalImages;
}
