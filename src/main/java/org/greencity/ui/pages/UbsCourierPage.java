package org.greencity.ui.pages;

import org.openqa.selenium.WebDriver;

public class UbsCourierPage extends BasePage {
    public UbsCourierPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public UbsCourierPage open() {
        return this;
    }

    @Override
    public boolean isPageOpened() {
        return false;
    }

    @Override
    public BasePage waitUntilOpened() {
        return null;
    }
}
