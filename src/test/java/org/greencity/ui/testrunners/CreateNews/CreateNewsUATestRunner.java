package org.greencity.ui.testrunners.CreateNews;

import org.greencity.ui.components.HeaderComponent;

public class CreateNewsUATestRunner extends BaseCreateNewsTestRunner {

    @Override
    protected void switchLanguage(HeaderComponent header) {
        header.changeToUK();
    }
}
