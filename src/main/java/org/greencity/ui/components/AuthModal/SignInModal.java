package org.greencity.ui.components.AuthModal;

import org.greencity.ui.pages.MySpace.MySpaceBasePage;
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

    public MySpaceBasePage loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
        return new MySpaceBasePage(driver);
    }

    public void switchToSignUp() {
        signUpLink.click();
    }
}
