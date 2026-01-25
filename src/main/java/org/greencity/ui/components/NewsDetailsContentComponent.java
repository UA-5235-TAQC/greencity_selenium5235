package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewsDetailsContentComponent extends BaseComponent{
    public NewsDetailsContentComponent(WebDriver driver) {
        super(driver);
    }

    public NewsDetailsContentComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }
}
