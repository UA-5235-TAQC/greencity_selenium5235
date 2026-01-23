package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NoDataComponent extends BaseComponent {

    public NoDataComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }
    public boolean isVisible() {
        return rootElement.isDisplayed();
    }
    
}
