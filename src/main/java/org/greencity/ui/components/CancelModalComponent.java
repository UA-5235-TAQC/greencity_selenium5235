package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Component class for the Cancel Modal dialog.
 * Handles all interactions with the confirmation modal that appears
 * when user clicks the "Cancel" button on the Create News form.
 */

public class CancelModalComponent extends BaseComponent {

    @FindBy(css = ".warning-text")
    private WebElement messageContainer;

    @FindBy(css = ".buttons-container .primary-global-button")
    private WebElement yesCancelBtn;

    @FindBy(css = ".buttons-container .secondary-global-button")
    private WebElement continueEditingBtn;

    @FindBy(css = ".close")
    private WebElement closeBtn;

    @FindBy(css = ".warning-title")
    private WebElement warningTitle;

    @FindBy(css = ".warning-subtitle")
    private WebElement warningSubtitle;

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

    public String getWarningTitleText() {
        return warningTitle.getText().trim();
    }

    public String getWarningSubtitleText() {
        return warningSubtitle.getText().trim();
    }

    public boolean isCancelButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(yesCancelBtn));
            return yesCancelBtn.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isContinueEditingButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(continueEditingBtn));
            return continueEditingBtn.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    // Waits until the modal becomes visible.
    public void waitUntilVisible() {
        wait.until(ExpectedConditions.visibilityOf(rootElement));
    }

    public void waitUntilClosed() {
        wait.until(ExpectedConditions.invisibilityOf(rootElement));
    }
}
