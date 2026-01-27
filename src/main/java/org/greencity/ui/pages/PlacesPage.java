package org.greencity.ui.pages;

import org.openqa.selenium.WebDriver;

public class PlacesPage extends BasePage {
    public PlacesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public PlacesPage open() {
        return this;
    }

    @Override
    public boolean isPageOpened() {
        return false;
    }
}
