package org.greencity.ui.components;

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
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

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
        waitUntilClickable(ecoNewsLink);
        ecoNewsLink.click();
        wait.until(ExpectedConditions.urlContains("/greenCity/news"));
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

    public MySpaceHabitsTabPage clickMySpace() {
        waitUntilClickable(mySpace);
        mySpace.click();
        return new MySpaceHabitsTabPage(driver);
    }

    public void clickSearchBtn() {
        searchBtn.click();
    }

    public void clickLanguageDropdown() {
        languageDropdown.click();
    }

    public String getUser() {
        try {
            waitUntilVisible(userName);
        } catch (Exception e) {
            return "";
        }
        return userName.getText().trim();
    }

    public ProfileDropdownComponent clickProfileDropdown() {
        waitUntilClickable(drpButton);
        drpButton.click();
        waitUntilVisible(dropDown);
        return new ProfileDropdownComponent(driver, dropDown);
    }

    public String getCurrentLocale() {
        String lang = drpButton.getText().trim();
        return lang.equalsIgnoreCase("Uk") ? "uk" : "en";
    }
}
