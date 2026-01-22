package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FooterComponent extends BaseComponent{
    public FooterComponent(WebDriver driver) {
        super(driver);
    }

    public FooterComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }
}
