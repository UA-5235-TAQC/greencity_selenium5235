package org.greencity.ui.pages;

import org.greencity.ui.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void clickGoogleSignIn() {
        googleSignInBtn.click();
    }

    public void close() {
        closeBtn.click();
    }

    public boolean isVisible() {
        return root.isDisplayed();
    }
}
