package org.greencity.ui.enums;

import java.util.Arrays;
import java.util.List;

public enum EcoNewsTag {
    NEWS("News", "Новини"),
    EVENTS("Events", "Події"),
    EDUCATION("Education", "Освіта"),
    INITIATIVES("Initiatives", "Ініціативи"),
    ADS("Ads", "Реклама");

    private final String en;
    private final String ua;

    EcoNewsTag(String en, String ua) {
        this.en = en;
        this.ua = ua;
    }

    public String getByLocale(String locale) {
        return "uk".equalsIgnoreCase(locale) ? ua : en;
    }

    public String getEn() {
        return en;
    }

    public String getUa() {
        return ua;
    }

    public static List<String> getAllByLocale(String locale) {
        return Arrays.stream(values())
                .map(tag -> tag.getByLocale(locale))
                .toList();
    }

    public static List<String> getAllEn() {
        return getAllByLocale("en");
    }

    public static List<String> getAllUa() {
        return getAllByLocale("uk");
    }

    public static List<String> getByLocale(List<EcoNewsTag> tags, String locale) {
        return tags.stream()
                .map(tag -> tag.getByLocale(locale))
                .toList();
    }

    public static List<String> getEn(List<EcoNewsTag> tags) {
        return getByLocale(tags, "en");
    }

    public static List<String> getUa(List<EcoNewsTag> tags) {
        return getByLocale(tags, "uk");
    }
}
