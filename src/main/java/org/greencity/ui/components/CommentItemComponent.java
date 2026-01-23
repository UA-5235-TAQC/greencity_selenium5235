package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommentItemComponent extends BaseComponent {
    public CommentItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public CommentItemComponent(WebDriver driver) {
        super(driver);
    }
}
