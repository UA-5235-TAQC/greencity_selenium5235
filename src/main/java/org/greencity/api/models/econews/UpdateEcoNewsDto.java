package org.greencity.api.models.econews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.greencity.ui.enums.EcoNewsTag;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEcoNewsDto implements EcoNewsBase{
    private Long id;
    private String title;
    private String content;
    private String shortInfo;
    private List<String> tags;
    private String source;

    public List<String> getTagsEn() {
        return EcoNewsTag.mapStringsToLocale(tags, "en");
    }

    public List<String> getTagsUk() {
        return EcoNewsTag.mapStringsToLocale(tags, "uk");
    }
}
