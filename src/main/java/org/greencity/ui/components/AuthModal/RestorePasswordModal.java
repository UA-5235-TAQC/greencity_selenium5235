package org.greencity.ui.components.AuthModal;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RestorePasswordModal extends AuthModalBase {

    @FindBy(xpath = "//a[normalize-space(text())='Back to Sign in']")
    private WebElement backToSignInLink;

    public RestorePasswordModal(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Back to Sign in' link")
    public void clickBackToSignIn() {
        backToSignInLink.click();
    }
}
