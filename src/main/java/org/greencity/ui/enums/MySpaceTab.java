package org.greencity.ui.enums;

public enum MySpaceTab {

    HABITS("My habits", "Мої звички"),
    NEWS("My news", "Мої новини"),
    EVENTS("My events", "Мої події");

    private final String en;
    private final String ua;

    MySpaceTab(String en, String ua) {
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

    public boolean matches(String actualText) {
        if (actualText == null) return false;
        String normalized = actualText.trim();
        return normalized.equalsIgnoreCase(en)
                || normalized.equalsIgnoreCase(ua);
    }
}
