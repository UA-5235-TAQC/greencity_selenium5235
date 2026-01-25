package org.greencity.ui.testrunners;

import org.greencity.ui.components.AuthModal.SignInModal;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.MySpace.MySpaceBasePage;
import org.greencity.ui.pages.MySpace.MySpaceEventsTabPage;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class AuthenticatedTestRunner extends BaseTestRunner {

    @BeforeMethod
    public void loginBeforeEachTest() {
        driver.get(testValueProvider.getBaseUIGreenCityUrl());

        HomePage homePage = new HomePage(driver);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page should be opened");

        MySpaceEventsTabPage mySpacePage = homePage.getHeader()
                .clickSignInLink()
                .enterEmail(testValueProvider.getUserEmail())
                .enterPassword(testValueProvider.getUserPassword())
                .clickSubmit();
        Assert.assertTrue(mySpacePage.isPageOpened(), "My space page should be opened");
    }
}