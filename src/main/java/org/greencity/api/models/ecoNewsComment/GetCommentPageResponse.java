package org.greencity.api.models.ecoNewsComment;

import lombok.Data;
import java.util.List;

@Data
public class GetCommentPageResponse {
    private List<GetCommentResponse> page;
    private int totalElements;
    private int currentPage;
    private int totalPages;
}
