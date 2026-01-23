package org.greencity.ui.pages;

import org.openqa.selenium.WebDriver;

public class EventsPage extends BasePage {
    public EventsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public EventsPage open() {
        return this;
    }

    @Override
    public boolean isPageOpened() {
        return false;
    }
}
