package org.greencity.ui.enums;

public enum EcoNewsTag {
    NEWS("News"),
    EVENTS("Events"),
    EDUCATION("Education"),
    INITIATIVES("Initiatives"),
    ADS("Ads");

    private String tagName;

    EcoNewsTag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
