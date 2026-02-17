package org.greencity.api.models.ecoNewsComment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.greencity.api.models.AuthorResponse;
import org.greencity.api.utils.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCommentResponse {

    private int id;
    private String createdDate;
    private String modifiedDate;
    private AuthorResponse author;
    private int parentCommentId;
    private String text;
    private int replies = 0;
    private int likes = 0;
    private int dislikes = 0;
    private boolean currentUserLiked;
    private boolean currentUserDisliked;
    private String[] additionalImages;

    public LocalDate getCreatedDate() {
        return parseDate(createdDate);
    }

    public LocalDate getModifiedDate() {
        return parseDate(modifiedDate);
    }

    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        LocalDateTime ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return ldt.toLocalDate();
    }
}
