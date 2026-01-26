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

    /**
     * Checks if the "Yes, cancel" button is visible in the modal.
     *
     * @return true if button is visible and displayed, false otherwise
     */
    public boolean isYesCancelButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(yesCancelBtn));
            return yesCancelBtn.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks if the "Continue editing" button is visible in the modal.
     *
     * @return true if button is visible and displayed, false otherwise
     */
    public boolean isContinueEditingButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(continueEditingBtn));
            return continueEditingBtn.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Waits until the modal becomes visible.
     * Useful for ensuring modal appears after Cancel button click.
     */
    public void waitUntilVisible() {
        wait.until(ExpectedConditions.visibilityOf(rootElement));
    }

    /**
     * Waits until the modal is no longer visible/disappears.
     * Useful for ensuring modal closes after button click.
     */
    public void waitUntilClosed() {
        wait.until(ExpectedConditions.invisibilityOf(rootElement));
    }
}
