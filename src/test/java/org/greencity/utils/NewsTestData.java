package org.greencity.utils;

import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;

import java.util.List;

public class NewsTestData {

    public static final String TEST_TITLE = "Test";
    public static final String TEST_CONTENT = "Test content with 20 chars";
    public static final String TEST_SOURCE = "https://chatgpt.com/";
    public static final List<String> TEST_TAGS = List.of(EcoNewsTag.NEWS.getEn());

    public void applyTo(CreateNewsPage page) {
        page.createNews(TEST_TITLE, TEST_TAGS, TEST_SOURCE, TEST_CONTENT, null);
    }
}
