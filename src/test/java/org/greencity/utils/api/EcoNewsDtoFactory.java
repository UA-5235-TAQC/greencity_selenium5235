package org.greencity.utils.api;

import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.ui.enums.EcoNewsTag;

import java.util.List;

public record EcoNewsDtoFactory(long ecoNewsId) {

    public static final List<EcoNewsTag> TEST_TAGS = List.of(EcoNewsTag.NEWS, EcoNewsTag.EDUCATION);

    // ENGLISH
    public static final String TITLE_EN = "Welcome to Wikipedia";
    public static final String CONTENT_EN = "The Saxe-Goldstein hypothesis is a prediction in archaeology " +
            "about the relationship between a society's funerary practices " +
            "and its social organization.";
    public static final String SHORT_INFO_EN = "The main page of Wikipedia in English";
    public static final String SOURCE_EN = "https://en.wikipedia.org/wiki/Main_Page";

    // UKRAINIAN
    public static final String TITLE_UK = "Ласкаво просимо до Вікіпедії";
    public static final String CONTENT_UK = "Осип Тадейович Назарук (1883 — 1940) — український громадський і " +
            "політичний діяч, письменник, журналіст, воєнний кореспондент, публіцист, " +
            "адвокат.";
    public static final String SHORT_INFO_UK = "Головна сторінка Вікіпедії українською";
    public static final String SOURCE_UK = "https://uk.wikipedia.org/wiki/Main_Page";

    public static EcoNewsRequest createNewsEn() {
        return EcoNewsRequest.builder()
                .title(TITLE_EN)
                .text(CONTENT_EN)
                .shortInfo(SHORT_INFO_EN)
                .source(SOURCE_EN)
                .tags(EcoNewsTag.getEn(TEST_TAGS))
                .build();
    }

    public static EcoNewsRequest createNewsUa() {
        return EcoNewsRequest.builder()
                .title(TITLE_UK)
                .text(CONTENT_UK)
                .shortInfo(SHORT_INFO_UK)
                .source(SOURCE_UK)
                .tags(EcoNewsTag.getUa(TEST_TAGS))
                .build();
    }

    public UpdateEcoNewsDto updateDtoEn() {
        UpdateEcoNewsDto dto = new UpdateEcoNewsDto();
        dto.setId(ecoNewsId);
        dto.setTitle(TITLE_EN);
        dto.setContent(CONTENT_EN);
        dto.setShortInfo(SHORT_INFO_EN);
        dto.setTags(EcoNewsTag.getEn(TEST_TAGS));
        dto.setSource(SOURCE_EN);
        return dto;
    }

    public UpdateEcoNewsDto updateDtoUa() {
        UpdateEcoNewsDto dto = new UpdateEcoNewsDto();
        dto.setId(ecoNewsId);
        dto.setTitle(TITLE_UK);
        dto.setContent(CONTENT_UK);
        dto.setShortInfo(SHORT_INFO_UK);
        dto.setTags(EcoNewsTag.getUa(TEST_TAGS));
        dto.setSource(SOURCE_UK);
        return dto;
    }

    public static EcoNewsRequest createTestNews() {
        return EcoNewsRequest.builder()
                .title("News title for testing api comment controller")
                .text("News text for testing api comment controller. Should be more than 20 characters.")
                .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
                .source("https://example.com")
                .shortInfo("Short info")
                .build();
    }
}
