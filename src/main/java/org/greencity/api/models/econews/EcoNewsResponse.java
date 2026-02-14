package org.greencity.api.models.econews;

import lombok.Data;
import org.greencity.api.models.AuthorResponse;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class EcoNewsResponse {

    private long id;
    private String title;
    private String content;
    private String shortInfo;
    private AuthorResponse author;
    private OffsetDateTime creationDate;
    private String imagePath;
    private String source;
    private List<String> tagsUk;
    private List<String> tagsEn;
    private int likes;
    private int countComments;
    private int countOfEcoNews;
    private boolean favorite;
    private int dislikes = 0;
    private boolean hidden;
}
