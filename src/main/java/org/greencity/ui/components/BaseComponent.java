package org.greencity.ui.components;

import org.greencity.ui.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public abstract class BaseComponent extends Base {
    protected WebElement rootElement;

    public BaseComponent(WebDriver driver) {
        super(driver);
    }

    public BaseComponent(WebDriver driver, WebElement rootElement) {
        super(driver);
        this.rootElement = rootElement;
        PageFactory.initElements(new DefaultElementLocatorFactory(rootElement), this);
    }
}
