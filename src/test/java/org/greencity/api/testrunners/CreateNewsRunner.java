package org.greencity.api.testrunners;

import org.testng.annotations.AfterMethod;

public class CreateNewsRunner extends CreateNewsBeforeTestRunner {

    public CreateNewsRunner() {
        super(null);
    }

    public CreateNewsRunner(String imagePath) {
        super(imagePath);
    }

    @AfterMethod(description = "Delete the created EcoNews after each test")
    public void deleteEcoNews() {
        ecoNewsClient.deleteEcoNewsById(ecoNewsId);
    }
}
