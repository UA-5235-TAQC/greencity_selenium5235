package org.greencity.api.models.ecoNewsComment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCommentResponse extends AddCommentResponse {

    private String modifiedDate;
    private int parentCommentId;
    private int replies = 0;
    private int likes = 0;
    private int dislikes = 0;
    private boolean currentUserLiked;
    private boolean currentUserDisliked;
    private String status = "ORIGINAL";

    public LocalDate getCreationDate() {
        return parseDate(getCreatedDate());
    }

    public LocalDate getModificationDate() {
        return parseDate(modifiedDate);
    }

    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        LocalDateTime ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return ldt.toLocalDate();
    }
}
