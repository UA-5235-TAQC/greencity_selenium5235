package org.greencity.ui.components.AuthModal;

import io.qameta.allure.Step;
import org.greencity.ui.Base;
import org.greencity.ui.pages.BasePage;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


//Abstract base class for authentication-related modal dialogs in the UI.
public abstract class AuthModalBase extends Base {

    @FindBy(css = "app-auth-modal .wrapper")
    protected WebElement root;

    @FindBy(css = "input[formcontrolname='email']")
    protected WebElement emailInput;

    @FindBy(css = "input[formcontrolname='password']")
    protected WebElement passwordInput;

    @FindBy(css = ".close-modal-window")
    protected WebElement closeBtn;

    @FindBy(css = "button[type='submit']")
    protected WebElement submitBtn;

    @FindBy(css = "button.google-sign-in")
    protected WebElement googleSignInBtn;

    protected AuthModalBase(WebDriver driver) {
        super(driver);
    }

    //Enters the specified email address into the email input field of the authentication modal.
    @Step("Enter email: {email}")
    public AuthModalBase enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    //Clicks the Google sign-in button within the authentication modal to start the Google authentication flow.
    @Step("Click Google Sign-In")
    public void clickGoogleSignIn() {
        googleSignInBtn.click();
    }

    //Closes the authentication modal by clicking the close button.
    @Step("Close authentication modal")
    public void close() {
        closeBtn.click();
    }

    //Indicates whether the authentication modal is currently visible.
    @Step("Check authentication modal visibility")
    public boolean isVisible() {
        return root.isDisplayed();
    }

    @Step("Submit authentication form")
    public BasePage clickSubmit() {
        submitBtn.click();
        return new MySpaceHabitsTabPage(driver);
    }

    @Step("Enter password")
    public AuthModalBase enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }
}
