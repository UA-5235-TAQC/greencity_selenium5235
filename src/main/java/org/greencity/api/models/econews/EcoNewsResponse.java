package org.greencity.api.models.econews;

import lombok.Data;
import java.util.List;

@Data
public class EcoNewsResponse {

    private int id;
    private String title;
    private String content;
    private String shortInfo;
    private AuthorResponse author;
    private String creationDate;
    private String imagePath;
    private String source;
    private List<String> tagsUk;
    private List<String> tagsEn;
    private int likes;
    private int countComments;
    private int countOfEcoNews;
    private boolean favorite;
}
