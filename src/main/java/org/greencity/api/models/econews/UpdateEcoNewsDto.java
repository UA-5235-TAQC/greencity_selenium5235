package org.greencity.api.models.econews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEcoNewsDto {
    private long id;
    private String title;
    private String content;
    private String shortInfo;
    private List<String> tags;
    private String source;
}
