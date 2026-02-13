package org.greencity.api.models.econews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.greencity.api.models.AuthorDto;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcoNewsResponse {
    private OffsetDateTime creationDate;
    private String imagePath;
    private long id;
    private String title;
    private String content;
    private String shortInfo;
    private AuthorDto author;
    private int likes;
    private int dislikes;
    private int countComments;
    private boolean hidden;
    private List<String> tagsEn;
    private List<String> tagsUk;
}
