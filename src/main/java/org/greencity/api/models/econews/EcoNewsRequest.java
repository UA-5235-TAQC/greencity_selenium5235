package org.greencity.api.models.econews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EcoNewsRequest {
    private String title;
    private String text;
    private String source;
    private String shortInfo;
    private List<String> tags;
    private String image;
}