package org.greencity.api.models.econews;

import java.util.List;

public interface EcoNewsBase {
    String getTitle();
    String getContent();
    String getShortInfo();
    List<String> getTagsEn();
    List<String> getTagsUk();
    String getSource();
    Long getId();
}
