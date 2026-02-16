package org.greencity.api.testrunners;

import io.qameta.allure.Description;
import org.testng.annotations.AfterClass;

public class CreateNewsRunner extends CreateNewsBeforeTestRunner {

    @AfterClass
    @Description("Delete the created EcoNews after each test")
    public void deleteEcoNews() {
        ecoNewsClient.deleteEcoNewsById(ecoNewsId);
    }
}
