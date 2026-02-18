package org.greencity.api.models.ecoNewsComment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentQuery {
    private Integer page;
    private Integer size;
    private List<String> sort;
}
