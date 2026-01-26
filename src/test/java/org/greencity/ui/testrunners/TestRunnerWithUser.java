package org.greencity.ui.testrunners;

import org.greencity.ui.pages.BasePage;


public class TestRunnerWithUser extends BaseTestRunner {

    public void loginUser(BasePage page) {
        page.getHeader()
                .clickSignInLink()
                .enterEmail(testValueProvider.getUserEmail())
                .enterPassword(testValueProvider.getUserPassword())
                .clickSubmit();
    }
}
