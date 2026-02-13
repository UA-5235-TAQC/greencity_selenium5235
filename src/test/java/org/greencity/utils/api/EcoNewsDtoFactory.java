package org.greencity.utils.api;

import lombok.Data;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import java.util.List;

@Data
public class EcoNewsDtoFactory {

    private final long ecoNewsId;

    public EcoNewsDtoFactory(long ecoNewsId) {
        this.ecoNewsId = ecoNewsId;
    }

    public UpdateEcoNewsDto createDefaultDto() {
        UpdateEcoNewsDto dto = new UpdateEcoNewsDto();
        dto.setId(ecoNewsId);
        dto.setTitle("Welcome to Wikipedia");
        dto.setContent("The Saxe-Goldstein hypothesis is a prediction in archaeology " +
                "about the relationship between a society's funerary practices " +
                "and its social organization.");
        dto.setShortInfo("The main page of Wikipedia in English");
        dto.setTags(List.of("News", "Education"));
        dto.setSource("https://en.wikipedia.org/wiki/Main_Page");
        return dto;
    }
}
