package org.greencity.ui.enums;

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
}
