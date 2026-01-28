package org.greencity.ui.pages;

import org.openqa.selenium.WebDriver;

public class AboutUsPage extends BasePage {
    public AboutUsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public AboutUsPage open() {
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
