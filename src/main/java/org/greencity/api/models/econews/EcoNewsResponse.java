package org.greencity.api.models.econews;

import lombok.Data;
import org.greencity.api.models.AuthorResponse;
import org.greencity.api.utils.DateUtil;
import org.greencity.ui.enums.EcoNewsTag;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Data
public class EcoNewsResponse implements EcoNewsBase {

    private Long id;
    private String title;
    private String content;
    private String shortInfo;
    private AuthorResponse author;
    private String creationDate;
    private String imagePath;
    private String source;
    private List<String> tagsEn;
    private int likes;
    private int countComments;
    private int countOfEcoNews;
    private boolean favorite;
    private int dislikes = 0;
    private boolean hidden;

    @JsonIgnore
    public List<String> getTagsUk() {
        return EcoNewsTag.mapStringsToLocale(tagsEn, "uk");
    }

    @JsonIgnore
    public LocalDate getCreationDate() {
        return DateUtil.parseDate(creationDate);
    }
}
