package org.greencity.api.models.econews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.greencity.api.models.AuthorResponse;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcoNewsResponse {

    private Integer id;
    private String title;
    private String content;
    private String shortInfo;
    private AuthorResponse author;
    private String creationDate;
    private String imagePath;
    private String source;
    private List<String> tagsUk;
    private List<String> tagsEn;
    private Integer likes;
    private Integer countComments;
    private Integer countOfEcoNews;
    private Boolean favorite;
}
