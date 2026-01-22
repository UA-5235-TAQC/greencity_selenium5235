package org.greencity.ui.components;

import org.greencity.ui.components.AuthModal.SignUpModal;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.MySpace.MySpaceBasePage;
import org.greencity.ui.components.AuthModal.SignInModal;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

public class HeaderComponent extends BaseComponent {
    @FindBy(xpath = "//a[contains(@href, '#/greenCity/news')]")
    protected WebElement ecoNewsLink;

    @FindBy(xpath = "//li[contains(@class, 'header_sign-up-link')]")
    protected WebElement signUpLink;

    @FindBy(css = "a.header_sign-in-link")
    protected WebElement signInLink;

    @FindBy(css = "a.header_logo")
    protected WebElement logo;

    @FindBy(xpath = "//a[contains(@href, '#/greenCity/profile')]")
    protected WebElement mySpace;

    @FindBy(css = "li.search-icon")
    protected WebElement searchBtn;

    @FindBy(css = "ul.header_lang-switcher-wrp")
    protected WebElement languageDropdown;

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public HeaderComponent changeToEN() {
        return switchLanguage("En");
    }

    public HeaderComponent changeToUK() {
        return switchLanguage("Uk");
    }

    private HeaderComponent switchLanguage(String lang) {
        String currentLang = languageDropdown.getText().trim();
        if (currentLang.equalsIgnoreCase(lang)) {
            return this;
        }
        languageDropdown.click();
        WebElement langOption = languageDropdown.findElement(
                By.xpath(".//span[text()='" + lang + "']")
        );
        langOption.click();
        return this;
    }

    public EcoNewsPage clickEcoNewsLink() {
        ecoNewsLink.click();
        return new EcoNewsPage(driver);
    }

    public SignUpModal clickSignUpLink() {
        signUpLink.click();
        return new SignUpModal(driver);
    }

    public SignInModal clickSignInLink() {
        signInLink.click();
        return new SignInModal(driver);
    }

    public HomePage clickLogo() {

        logo.click();
        return new HomePage(driver);
    }

    public MySpaceBasePage clickMySpace() {
        mySpace.click();
        return new MySpaceBasePage(driver);
    }

    public void clickSearchBtn() {
        searchBtn.click();
    }

    public void clickLanguageDropdown() {
        languageDropdown.click();
    }
}
