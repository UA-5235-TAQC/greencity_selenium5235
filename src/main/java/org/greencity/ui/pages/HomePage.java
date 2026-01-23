package org.greencity.ui.pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open() {

    }

    @Override
    public boolean isPageOpened() {
        return false;
    }
}
