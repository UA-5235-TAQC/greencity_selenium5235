package org.greencity.api.models.econews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EcoNewsQuery {
    private Integer authorId;
    private Boolean favorite;
    private Integer page;
    private Integer size;
}
