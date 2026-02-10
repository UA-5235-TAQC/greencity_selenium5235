package org.greencity.ui.testrunners.EditNews;

import org.greencity.ui.components.HeaderComponent;

public class EditNewsENTestRunner extends BaseEditNewsTestRunner {

    @Override
    protected long getNewsId() {
        return 830;
    }

    @Override
    protected void switchLanguage(HeaderComponent header) {
        header.changeToEN();
    }
}
