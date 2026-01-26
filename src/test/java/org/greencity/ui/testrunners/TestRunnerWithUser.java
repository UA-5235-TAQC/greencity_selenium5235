package org.greencity.ui.testrunners;

import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.MySpace.MySpaceEventsTabPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class TestRunnerWithUser extends BaseTestRunner {

    @BeforeClass
    public void loginUser() {
        new HomePage(driver)
                .open()
                .getHeader()
                .clickSignInLink()
                .enterEmail(testValueProvider.getUserEmail())
                .enterPassword(testValueProvider.getUserPassword())
                .clickSubmit();

        // NOTE: after login, we're redirected to the MySpace page, which opens pretty slowly
        MySpaceEventsTabPage mySpace = new MySpaceEventsTabPage(driver);
        Assert.assertTrue(mySpace.isPageOpened());
    }
}
