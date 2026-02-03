package org.greencity.ui.components.AuthModal;

import io.qameta.allure.Step;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class SignInModal extends AuthModalBase {

    //selector differs from SignUpModal
    @FindBy(css = ".image-show-hide-password")
    private WebElement showPasswordBtn;

    @FindBy(css = "a.forgot-password")
    private WebElement forgotPasswordBtn;

    @FindBy(css = "a[aria-label='sign up modal window']")
    private WebElement signUpLink;

    public SignInModal(WebDriver driver) {
        super(driver);
    }

    @Step("Enter password")
    @Override
    public SignInModal enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    @Step("Enter email: {email}")
    @Override
    public SignInModal enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Click 'Forgot password'")
    public void clickForgotPassword() {
        forgotPasswordBtn.click();
    }

    @Step("Toggle password visibility")
    public SignInModal togglePasswordVisibility() {
        showPasswordBtn.click();
        return this;
    }

    @Step("Login as user with email: {email}")
    public MySpaceHabitsTabPage loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
        return new MySpaceHabitsTabPage(driver);
    }

    @Step("Switch to Sign Up modal")
    public void switchToSignUp() {
        signUpLink.click();
    }

    @Step("Submit login form")
    public MySpaceHabitsTabPage clickSubmit() {
        submitBtn.click();
        return new MySpaceHabitsTabPage(driver);
    }
}
