package org.greencity.utils;

import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;

import java.io.File;
import java.util.List;

public class NewsTestData {

    public static final String TEST_TITLE = "Test";
    public static final String TEST_CONTENT = "Test content with 20 chars";
    public static final String TEST_SOURCE = "https://chatgpt.com/";
    public static final List<EcoNewsTag> TEST_TAGS = List.of(EcoNewsTag.NEWS);
    public static final File TEST_FILE = new File("src/test/resources/images/test.jfif");
    public static final String TEST_FILEPATH = TEST_FILE.getAbsolutePath();

    public void applyTo(CreateNewsPage page) {
        page.createNews(TEST_TITLE, EcoNewsTag.getEn(TEST_TAGS), TEST_SOURCE, TEST_CONTENT, TEST_FILEPATH);
    }
}
