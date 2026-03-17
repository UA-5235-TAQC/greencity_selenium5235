package org.greencity.api.testrunners;

import org.testng.annotations.BeforeClass;

public class FirstUserRunner extends UserRunner {

    @BeforeClass
    public void loginFirstUser() {
        loginUser(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );
    }
}
