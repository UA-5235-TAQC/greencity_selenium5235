package org.greencity.ui.pages;

import org.greencity.ui.Base;
import org.greencity.ui.components.HeaderComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BasePage extends Base {

    @FindBy(xpath = "//app-header")
    protected WebElement rootHeaderElement;
    protected HeaderComponent header;


    public BasePage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver, rootHeaderElement);
    }

    public HeaderComponent getHeader() {
        return header;
    }

}
