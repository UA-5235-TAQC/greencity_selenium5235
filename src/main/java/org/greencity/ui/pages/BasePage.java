package org.greencity.ui.pages;

import io.qameta.allure.Step;
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

    private final By messageLocator = By.cssSelector(".mat-mdc-snack-bar-label");

    public BasePage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver, rootHeaderElement);
        this.footerComponent = new FooterComponent(driver, rootFooterElement);
    }

    @Step("Open page")
    public abstract BasePage open();

    @Step("Check that page is opened")
    public abstract boolean isPageOpened();

    @Step("Get header component")
    public HeaderComponent getHeader() {
        return header;
    }

    @Step("Get footer component")
    public FooterComponent getFooter() {
        return footerComponent;
    }

    @Step("Click on web element")
    protected void click(WebElement element) {
        waitUntilClickable(element);
        element.click();
    }

    @Step("Get text from web element")
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    @Step("Wait for snackbar message to appear")
    public BasePage waitForMessageAppear() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
        return this;
    }

    @Step("Wait for snackbar message to disappear")
    public BasePage waitForMessageDisappear() {
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(messageLocator)));
        return this;
    }

    @Step("Get snackbar message text")
    public String getMessageText() {
        waitForMessageAppear();
        return driver.findElement(messageLocator).getText();
    }

    @Step("Wait until page is fully opened")
    public abstract BasePage waitUntilOpened();
}
