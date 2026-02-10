package org.greencity.ui.testrunners.EditNews;

import org.greencity.ui.components.HeaderComponent;

public class EditNewsUATestRunner extends BaseEditNewsTestRunner {

    @Override
    protected long getNewsId() {
        return 888;
    }

    @Override
    protected void switchLanguage(HeaderComponent header) {
        header.changeToUK();
    }
}
