package org.greencity.ui.components;

import org.greencity.ui.components.AuthModal.SignUpModal;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BaseComponent {
    @FindBy(xpath = "//a[contains(@href, '#/greenCity/news')]")
    protected WebElement ecoNewsLink;

    @FindBy(xpath = "//li[contains(@class, 'header_sign-up-link')]")
    protected WebElement signUpLink;

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public void clickEcoNewsLink() {
        ecoNewsLink.click();
    }
    public SignUpModal clickSignUpLink() {
        signUpLink.click();
        return new SignUpModal(driver);
    }
}
