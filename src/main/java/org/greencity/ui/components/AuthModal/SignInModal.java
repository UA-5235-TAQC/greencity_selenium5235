package org.greencity.ui.components.AuthModal;

import org.greencity.ui.pages.MySpace.MySpaceEventsTabPage;
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

    public void clickForgotPassword() {
        forgotPasswordBtn.click();
    }

    public SignInModal togglePasswordVisibility() {
        showPasswordBtn.click();
        return this;
    }

    public void switchToSignUp() {
        signUpLink.click();
    }

    @Override
    public SignInModal enterEmail(String email) {
        super.enterEmail(email);
        return this;
    }

    @Override
    public SignInModal enterPassword(String password) {
        super.enterPassword(password);
        return this;
    }

    public MySpaceEventsTabPage clickSubmit() {
        submitBtn.click();
        return new MySpaceEventsTabPage(driver);
    }
}
