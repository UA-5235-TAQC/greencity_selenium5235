package org.greencity.api.models.econews;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.greencity.ui.enums.EcoNewsTag;

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

    @Step("Get tags in English")
    public List<String> getTagsEn() {
        return EcoNewsTag.mapStringsToLocale(tags, "en");
    }

    @Step("Get tags in Ukrainian")
    public List<String> getTagsUk() {
        return EcoNewsTag.mapStringsToLocale(tags, "uk");
    }
}
