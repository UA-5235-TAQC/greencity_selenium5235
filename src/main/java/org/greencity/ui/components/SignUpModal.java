package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpModal extends AuthModalBase {

    @FindBy(css = "input[formcontrolname='username']")
    private WebElement usernameInput;

    @FindBy(css = "input[formcontrolname='password']")
    private WebElement passwordInput;

    @FindBy(css = "input[formcontrolname='confirmPassword']")
    private WebElement confirmPasswordInput;

    @FindBy(css = ".show-hide-btn")
    private WebElement showPasswordBtn;

    @FindBy(css = "button[type='submit']")
    private WebElement signUpBtn;

    @FindBy(css = "a[aria-label='sign up modal window']")
    private WebElement switchSignInLink;

    public SignUpModal(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String name) {
        usernameInput.clear();
        usernameInput.sendKeys(name);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(password);
    }

    public void togglePasswordVisibility() {
        showPasswordBtn.click();
    }

    public void clickSignUp() {
        signUpBtn.click();
    }

    public void switchToSignIn() {
        switchSignInLink.click();
    }
}
