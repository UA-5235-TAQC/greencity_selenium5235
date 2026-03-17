package org.greencity.ui.testrunners.NewsDetails;

import org.greencity.ui.components.HeaderComponent;
import org.greencity.utils.ui.NewsTestData;

public class NewsDetailsENTestRunner extends BaseNewsDetailsTestRunner {

    @Override
    protected void switchLanguage(HeaderComponent header) {
        header.changeToEN();
    }

    @Override
    protected void applyNewsTestData() {
        new NewsTestData().applyToEn(createNewsPage);
    }
}
