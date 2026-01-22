package org.greencity.ui.pages;

import org.greencity.ui.Base;
import org.greencity.ui.components.FooterComponent;
import org.greencity.ui.components.HeaderComponent;
import org.greencity.utils.TestValueProvider;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BasePage extends Base {

    protected TestValueProvider testValueProvider = new TestValueProvider();

    @FindBy(xpath = "//app-header")
    protected WebElement rootHeaderElement;
    protected HeaderComponent header;

    @FindBy(xpath = "//app-footer")
    protected WebElement rootFooterElement;
    protected FooterComponent footerComponent;

    public BasePage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver, rootHeaderElement);
        this.footerComponent = new FooterComponent(driver, rootFooterElement);
    }

    abstract public void open();

    public abstract boolean isPageOpened();

    public HeaderComponent getHeader() {
        return header;
    }

    public FooterComponent getFooter() {
        return footerComponent;
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

    protected void waitUntilVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitUntilClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

}
