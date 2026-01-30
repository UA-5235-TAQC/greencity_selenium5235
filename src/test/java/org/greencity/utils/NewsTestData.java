package org.greencity.utils;

import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateEditNews.CreateEditNewsPage;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;

import java.io.File;
import java.util.List;

public class NewsTestData {

    public static final String TEST_TITLE_EN = "Test";
    public static final String TEST_CONTENT_EN = "Test content with 20 chars";
    public static final String TEST_TITLE_UA = "Tecт";
    public static final String TEST_CONTENT_UA = "Тестовий контент з 30 символів";
    public static final String TEST_SOURCE = "https://chatgpt.com/";
    public static final List<EcoNewsTag> TEST_TAGS = List.of(EcoNewsTag.NEWS);
    public static final File TEST_FILE = new File("src/test/resources/images/test.jfif");
    public static final String TEST_FILEPATH = TEST_FILE.getAbsolutePath();
    public static final File TEST2_FILE = new File("src/test/resources/images/test2.png");
    public static final String TEST2_FILEPATH = TEST2_FILE.getAbsolutePath();

    public void applyToEn(CreateEditNewsPage page) {
        page.createNews(TEST_TITLE_EN, EcoNewsTag.getEn(TEST_TAGS), TEST_SOURCE, TEST_CONTENT_EN, TEST_FILEPATH);
    }

    public void applyToUa(CreateEditNewsPage page) {
        page.createNews(TEST_TITLE_UA, EcoNewsTag.getUa(TEST_TAGS), TEST_SOURCE, TEST_CONTENT_UA, TEST_FILEPATH);
    }
}
