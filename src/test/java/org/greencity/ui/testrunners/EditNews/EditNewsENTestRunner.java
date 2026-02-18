package org.greencity.ui.testrunners.EditNews;

import org.greencity.ui.components.HeaderComponent;
import org.greencity.utils.ui.NewsTestData;

public class EditNewsENTestRunner extends BaseEditNewsTestRunner {

    @Override
    protected void switchLanguage(HeaderComponent header) {
        header.changeToEN();
    }

    @Override
    protected void applyNewsTestData() {
        new NewsTestData().applyToEn(createNewsPage);
    }
}
