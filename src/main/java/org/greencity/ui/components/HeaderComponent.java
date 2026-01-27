package org.greencity.ui.components;

import org.greencity.ui.components.AuthModal.SignUpModal;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.MySpace.MySpaceBasePage;
import org.greencity.ui.components.AuthModal.SignInModal;
import org.greencity.ui.pages.MySpace.MySpaceHabitsTabPage;
import org.openqa.selenium.StaleElementReferenceException;
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
//        waitUntilClickable(languageDropdown);
//        String currentLang = languageDropdown.getText().trim();
//        if (currentLang.equalsIgnoreCase(lang)) {
//            return this;
//        }
//        waitUntilClickable(languageDropdown);
//        languageDropdown.click();
//        WebElement langOption = languageDropdown.findElement(
//                By.xpath(".//span[text()='" + lang + "']")
//        );
//        waitUntilClickable(langOption);
//        langOption.click();
//        return this;
        By dropdownLocator = By.cssSelector("ul.header_lang-switcher-wrp");
        By langOptionLocator = By.xpath("//ul[contains(@class, 'header_lang-switcher-wrp')]//span[text()='" + lang + "']");

        // 1. Перевірка поточної мови з обробкою Stale
        String currentLang = wait.until(d -> {
            try {
                return d.findElement(dropdownLocator).getText().trim();
            } catch (StaleElementReferenceException e) {
                return null; // Повертаємо null, щоб wait спробував ще раз
            }
        });

        if (lang.equalsIgnoreCase(currentLang)) {
            return this;
        }

        // 2. Клік по дропдауну з автоматичним перепошуком
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    d.findElement(dropdownLocator).click();
                    return true;
                });

        // 3. Клік по самій мові (тут зазвичай "блимає" найбільше)
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    d.findElement(langOptionLocator).click();
                    return true;
                });

        return this;
    }

    public EcoNewsPage clickEcoNewsLink() {
        waitUntilVisible(ecoNewsLink);
        ecoNewsLink.click();
        return new EcoNewsPage(driver);
    }

    public SignUpModal clickSignUpLink() {
        signUpLink.click();
        return new SignUpModal(driver);
    }

    public SignInModal clickSignInLink() {
        waitUntilClickable(signInLink);
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

    public ProfileDropdownComponent clickProfileDropdown() {
        waitUntilClickable(drpButton);
        drpButton.click();
        waitUntilVisible(dropDown);
        return new ProfileDropdownComponent(driver, dropDown);
    }
}
