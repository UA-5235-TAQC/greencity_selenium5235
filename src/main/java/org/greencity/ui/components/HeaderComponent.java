package org.greencity.ui.components;

import io.qameta.allure.Step;
import org.greencity.ui.components.AuthModal.SignUpModal;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.components.AuthModal.SignInModal;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;


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

    @FindBy(css = ".body-2")
    protected WebElement drpButton;

    @FindBy(css = "ul.dropdown-list")
    protected WebElement dropDown;

    @FindBy(xpath = "//ul[@id='header_user-wrp']/li[contains(@class,'user-name')]")
    private WebElement userName;

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("The selected language for change is English")
    public HeaderComponent changeToEN() {
        return switchLanguage("En");
    }

    @Step("The selected language for change is Ukrainian")
    public HeaderComponent changeToUK() {
        return switchLanguage("Uk");
    }

    @Step("Switch to the selected language")
    private HeaderComponent switchLanguage(String lang) {
        String currentLang = languageDropdown.getText().trim();
        if (currentLang.equalsIgnoreCase(lang)) {
            return this;
        }
        languageDropdown.click();
        WebElement langOption = languageDropdown.findElement(
                By.xpath(".//span[text()='" + lang + "']")
        );
        waitUntilClickable(langOption);
        langOption.click();
        return this;
    }

    @Step("Clicked on the 'EcoNews' link in the header menu")
    public EcoNewsPage clickEcoNewsLink() {
        waitUntilClickable(ecoNewsLink);
        ecoNewsLink.click();
        wait.until(ExpectedConditions.urlContains("/greenCity/news"));
        return new EcoNewsPage(driver);
    }

    @Step("Clicked on the 'SignUp' link in the header menu")
    public SignUpModal clickSignUpLink() {
        signUpLink.click();
        return new SignUpModal(driver);
    }

    @Step("Clicked on the 'SignIn' link in the header menu")
    public SignInModal clickSignInLink() {
        waitUntilClickable(signInLink);
        signInLink.click();
        return new SignInModal(driver);
    }

    @Step("Clicked on the logo in the header")
    public HomePage clickLogo() {
        logo.click();
        return new HomePage(driver);
    }

    @Step("Clicked on the 'MySpace' link in the header menu")
    public MySpaceHabitsTabPage clickMySpace() {
        waitUntilClickable(mySpace);
        mySpace.click();
        return new MySpaceHabitsTabPage(driver);
    }

    @Step("Clicked on the 'Search' button in the header menu")
    public void clickSearchBtn() {
        searchBtn.click();
    }

    @Step("Clicked on the language dropdown button in the header menu")
    public void clickLanguageDropdown() {
        languageDropdown.click();
    }

    @Step("Getting the name of a registered user")
    public String getUser() {
        try {
            waitUntilVisible(userName);
        } catch (Exception e) {
            return "";
        }
        return userName.getText().trim();
    }

    @Step("Click the profile dropdown button")
    public ProfileDropdownComponent clickProfileDropdown() {
        waitUntilClickable(drpButton);
        drpButton.click();
        waitUntilVisible(dropDown);
        return new ProfileDropdownComponent(driver, dropDown);
    }

    @Step("Getting the current language")
    public String getCurrentLocale() {
        String lang = languageDropdown.getText().trim();
        return lang.equalsIgnoreCase("Uk") ? "uk" : "en";
    }
}
