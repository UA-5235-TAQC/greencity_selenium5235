package org.greencity.ui.testrunners.EditNews;

import org.greencity.ui.components.HeaderComponent;
import org.greencity.utils.ui.NewsTestData;

public class EditNewsUATestRunner extends BaseEditNewsTestRunner {

    @Override
    protected void switchLanguage(HeaderComponent header) {
        header.changeToUK();
    }

    @Override
    protected void applyNewsTestData() {
        new NewsTestData().applyToUa(createNewsPage);
    }
}
