package org.greencity.api.testrunners;

import org.testng.annotations.BeforeClass;

public class SecondUserRunner extends UserRunner {

    @BeforeClass
    public void loginSecondUser() {
        loginUser(
                testValueProvider.getSecondUserEmail(),
                testValueProvider.getSecondUserPassword()
        );
    }
}
