package org.greencity.ui.pages;

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

    @FindBy(css = ".eco-events a") // "Read all news" link
    protected WebElement readAllNewsLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageOpened() {
        return isVisible(root);
    }

    @Override
    public void open() {
        driver.get("#/greenCity");
    }

    public String getHeroTitle() {
        return getText(heroTitle);
    }

    public String getHeroDescription() {
        return getText(heroDescription);
    }

    public void clickStartHabit() {
        click(startHabitButton);
    }

    public String getStats() {
        return getText(statsSection);
    }

    public String getEcoNewsPreview() {
        return getText(ecoNewsSection);
    }

    public void clickReadAllNews() {
        click(readAllNewsLink);
    }

    public void subscribe(String email) {
        waitUntilVisible(emailInput);
        emailInput.sendKeys(email);
        click(subscribeButton);
    }

    public List<StatRowComponent> getStatRowComponentList() {
        return statRowComponentList;
    }

    public SubscribeComponent getSubscribeComponent() {
        return subscribeComponent;
    }
}
