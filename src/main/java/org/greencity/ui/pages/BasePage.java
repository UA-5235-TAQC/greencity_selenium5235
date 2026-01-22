package org.greencity.ui.pages;

import org.greencity.ui.Base;
import org.greencity.ui.components.HeaderComponent;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    protected void click(WebElement element) {
        WebElement clickable = wait.until(
                ExpectedConditions.elementToBeClickable(element)
        );
        clickable.click();
    }

    protected String getText(WebElement element) {
        return wait.until(
                ExpectedConditions.visibilityOf(element)
        ).getText();
    }

    protected boolean isVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
