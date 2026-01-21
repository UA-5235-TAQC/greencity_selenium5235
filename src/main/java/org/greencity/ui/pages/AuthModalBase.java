package org.greencity.ui.pages;

import org.greencity.ui.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Abstract base class for authentication-related modal dialogs in the UI.
 */
public abstract class AuthModalBase extends Base {

    @FindBy(css = "app-auth-modal .wrapper")
    protected WebElement root;

    @FindBy(css = "input[formcontrolname='email']")
    protected WebElement emailInput;

    @FindBy(css = ".close-modal-window")
    protected WebElement closeBtn;

    @FindBy(css = "button.google-sign-in")
    protected WebElement googleSignInBtn;

    protected AuthModalBase(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters the specified email address into the email input field of the authentication modal.
     *
     * @param email the email address to be typed into the email input field
     * @throws org.openqa.selenium.NoSuchElementException          if the email input element is not present in the DOM
     * @throws org.openqa.selenium.ElementNotInteractableException if the email input element cannot be interacted with
     */
    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    /**
     * Clicks the Google sign-in button within the authentication modal to start the Google authentication flow.
     *
     * @throws org.openqa.selenium.NoSuchElementException           if the Google sign-in button is not present in the DOM
     * @throws org.openqa.selenium.ElementClickInterceptedException if the Google sign-in button cannot be clicked
     */
    public void clickGoogleSignIn() {
        googleSignInBtn.click();
    }

    /**
     * Closes the authentication modal by clicking the close button.
     *
     * @throws org.openqa.selenium.NoSuchElementException           if the close button is not present in the DOM
     * @throws org.openqa.selenium.ElementClickInterceptedException if the close button cannot be clicked
     */
    public void close() {
        closeBtn.click();
    }

    /**
     * Indicates whether the authentication modal is currently visible.
     *
     * @return {@code true} if the root element of the authentication modal is displayed; {@code false} otherwise
     * @throws org.openqa.selenium.NoSuchElementException if the root element is not present in the DOM
     */
    public boolean isVisible() {
        return root.isDisplayed();
    }
}
