package org.greencity.ui.components.AuthModal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpModal extends AuthModalBase {

    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(css = "input[formcontrolname='repeatPassword']")
    private WebElement confirmPasswordInput;

    @FindBy(css = ".show-password-img")
    private WebElement showPasswordBtn;

    @FindBy(css = "a[aria-label='sign in modal window']")
    private WebElement signInLink;

    public SignUpModal(WebDriver driver) {
        super(driver);
    }

    public SignUpModal enterUsername(String name) {
        firstNameInput.clear();
        firstNameInput.sendKeys(name);
        return this;
    }

    @Override
    public SignUpModal enterPassword(String password) {
        super.enterPassword(password);
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

    public void switchToSignIn() {
        signInLink.click();
    }
}
