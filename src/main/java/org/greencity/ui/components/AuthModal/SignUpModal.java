package org.greencity.ui.components.AuthModal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpModal extends AuthModalBase {

    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(css = "input[formcontrolname='password']")
    private WebElement passwordInput;

    @FindBy(css = "input[formcontrolname='confirmPassword']")
    private WebElement confirmPasswordInput;

    @FindBy(css = ".show-hide-btn")
    private WebElement showPasswordBtn;

    @FindBy(css = "button[type='submit']")
    private WebElement signUpBtn;

    @FindBy(css = "a[aria-label='sign up modal window']")
    private WebElement switchSignUpLink;

    public SignUpModal(WebDriver driver) {
        super(driver);
    }

    public SignUpModal enterUsername(String name) {
        firstNameInput.clear();
        firstNameInput.sendKeys(name);
        return this;
    }

    public SignUpModal enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public SignUpModal enterConfirmPassword(String password) {
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(password);
        return this;
    }

    public SignUpModal togglePasswordVisibility() {

        showPasswordBtn.click();
        return this;
    }

    public void clickSignUp() {
        signUpBtn.click();
    }

    public void switchToSignIn() {
        switchSignUpLink.click();
    }
}
