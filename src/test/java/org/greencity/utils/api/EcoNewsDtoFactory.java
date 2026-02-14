package org.greencity.utils.api;

import lombok.Data;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.ui.enums.EcoNewsTag;

import java.util.List;

@Data
public class EcoNewsDtoFactory {

    private final long ecoNewsId;
    public static final List<EcoNewsTag> TEST_TAGS = List.of(EcoNewsTag.NEWS, EcoNewsTag.EDUCATION);

    public EcoNewsDtoFactory(long ecoNewsId) {
        this.ecoNewsId = ecoNewsId;
    }

    public UpdateEcoNewsDto createDefaultDtoEn() {
        UpdateEcoNewsDto dto = new UpdateEcoNewsDto();
        dto.setId(ecoNewsId);
        dto.setTitle(EcoNewsTexts.TITLE_EN);
        dto.setContent(EcoNewsTexts.CONTENT_EN);
        dto.setShortInfo(EcoNewsTexts.SHORT_INFO_EN);
        dto.setTags(EcoNewsTag.getEn(TEST_TAGS));
        dto.setSource(EcoNewsTexts.SOURCE_EN);
        return dto;
    }

    public UpdateEcoNewsDto createDefaultDtoUa() {
        UpdateEcoNewsDto dto = new UpdateEcoNewsDto();
        dto.setId(ecoNewsId);
        dto.setTitle(EcoNewsTexts.TITLE_UK);
        dto.setContent(EcoNewsTexts.CONTENT_UK);
        dto.setShortInfo(EcoNewsTexts.SHORT_INFO_UK);
        dto.setTags(EcoNewsTag.getUa(TEST_TAGS));
        dto.setSource(EcoNewsTexts.SOURCE_UK);
        return dto;
    }
}
