package org.greencity.api.models.econews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcoNewsPageResponse {

    private List<EcoNewsResponse> page;

    private int totalElements;
    private int currentPage;
    private int totalPages;
    private int number;

    private boolean hasPrevious;
    private boolean hasNext;
    private boolean first;
    private boolean last;
}
