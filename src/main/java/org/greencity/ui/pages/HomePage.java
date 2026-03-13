package org.greencity.ui.pages;

import io.qameta.allure.Step;
import org.greencity.ui.components.StatRowComponent;
import org.greencity.ui.components.SubscribeComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    protected List<StatRowComponent> statRowComponentList;
    private SubscribeComponent subscribeComponent;

    @FindBy(css = "main")
    protected WebElement root;

    @FindBy(css = ".main-content h1")
    protected WebElement heroTitle;

    @FindBy(css = "#header-left p")
    protected WebElement heroDescription;

    @FindBy(css = "#header-left button.primary-global-button")
    protected WebElement startHabitButton;

    @FindBy(css = "#stats")
    protected WebElement statsSection;

    @FindBy(css = "#events")
    protected WebElement ecoNewsSection;

    @FindBy(css = ".subscribe-container")
    protected WebElement subscriptionSection;

    @FindBy(css = ".subscription-input")
    protected WebElement emailInput;

    @FindBy(css = "div #subscribe")
    protected WebElement subscribeButton;

    @FindBy(css = ".eco-events a") 
    protected WebElement readAllNewsLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Check that home page is opened")
    @Override
    public boolean isPageOpened() {
        return isVisible(root);
    }

    @Step("Wait until home page is loaded")
    @Override
    public HomePage waitUntilOpened() {
        return this;
    }

    @Step("Open home page")
    @Override
    public HomePage open() {

        driver.get(getBaseHost());
        isPageOpened();
        return new HomePage(driver);
    }

    @Step("Get hero title text")
    public String getHeroTitle() {
        return getText(heroTitle);
    }

    @Step("Get hero description text")
    public String getHeroDescription() {
        return getText(heroDescription);
    }

    @Step("Click 'Start habit' button")
    public void clickStartHabit() {
        click(startHabitButton);
    }

    @Step("Get statistics section text")
    public String getStats() {
        return getText(statsSection);
    }

    @Step("Get eco news preview text")
    public String getEcoNewsPreview() {
        return getText(ecoNewsSection);
    }

    @Step("Click 'Read all news' link")
    public void clickReadAllNews() {
        click(readAllNewsLink);
    }

    @Step("Subscribe with email: {email}")
    public void subscribe(String email) {
        waitUntilVisible(emailInput);
        emailInput.sendKeys(email);
        click(subscribeButton);
    }

    @Step("Get statistics row components list")
    public List<StatRowComponent> getStatRowComponentList() {
        return statRowComponentList;
    }

    @Step("Get subscribe component")
    public SubscribeComponent getSubscribeComponent() {
        return subscribeComponent;
    }
}
