package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CancelModalComponent extends BaseComponent {

    @FindBy(css = ".warning-text")
    private WebElement messageContainer;

    @FindBy(css = ".buttons-container .primary-global-button")
    private WebElement yesCancelBtn;

    @FindBy(css = ".buttons-container .secondary-global-button")
    private WebElement continueEditingBtn;

    @FindBy(css = ".close")
    private WebElement closeBtn;

    public CancelModalComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getMessage() {
        return messageContainer.getText().trim();
    }

    public void clickYesCancel() {
        yesCancelBtn.click();
    }

    public void clickContinueEditing() {
        continueEditingBtn.click();
    }

    public void clickClose() {
        closeBtn.click();
    }

    public boolean isVisible() {
        return rootElement.isDisplayed();
    }
}
