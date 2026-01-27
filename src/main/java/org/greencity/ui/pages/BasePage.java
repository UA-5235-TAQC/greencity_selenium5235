package org.greencity.ui.pages;

import org.greencity.ui.Base;
import org.greencity.ui.components.FooterComponent;
import org.greencity.ui.components.HeaderComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public abstract class BasePage extends Base {


    @FindBy(xpath = "//app-header")
    protected WebElement rootHeaderElement;
    protected HeaderComponent header;

    @FindBy(xpath = "//app-footer")
    protected WebElement rootFooterElement;
    protected FooterComponent footerComponent;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    abstract public BasePage open();

    public abstract boolean isPageOpened();

    public HeaderComponent getHeader() {
        if (header == null) {
            header = new HeaderComponent(driver, rootHeaderElement);
        }
        return header;
    }

    public FooterComponent getFooter() {
        if (footerComponent == null) {
            footerComponent = new FooterComponent(driver, rootFooterElement);
        }
        return footerComponent;
    }

    protected void click(WebElement element) {
        waitUntilClickable(element);
        element.click();
    }

    protected String getText(WebElement element) {
        waitUntilVisible(element);
        return element.getText();
    }
}
