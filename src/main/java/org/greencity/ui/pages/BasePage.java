package org.greencity.ui.pages;

import org.greencity.ui.Base;
import org.greencity.ui.components.FooterComponent;
import org.greencity.ui.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BasePage extends Base {


    @FindBy(xpath = "//app-header")
    protected WebElement rootHeaderElement;
    protected HeaderComponent header;

    @FindBy(xpath = "//app-footer")
    protected WebElement rootFooterElement;
    protected FooterComponent footerComponent;

    private By messageLocator = By.xpath("//div[contains(text(),'successfully published')]");

    public BasePage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver, rootHeaderElement);
        this.footerComponent = new FooterComponent(driver, rootFooterElement);
    }

    abstract public BasePage open();

    public abstract boolean isPageOpened();

    public HeaderComponent getHeader() {
        return header;
    }

    public FooterComponent getFooter() {
        return footerComponent;
    }

    protected void click(WebElement element) {
        WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(element));
        clickable.click();
    }

    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }
    public BasePage waitForMessageAppear() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
        return this;
    }

    public BasePage waitForMessageDisappear() {
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(messageLocator)));
        return this;
    }
    public String getMessageText() {
        waitForMessageAppear();
        return driver.findElement(messageLocator).getText();
    }

}
