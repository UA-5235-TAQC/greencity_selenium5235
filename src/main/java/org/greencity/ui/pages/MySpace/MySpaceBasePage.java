package org.greencity.ui.pages.MySpace;


import org.greencity.ui.components.MySpace.ProfilePanelComponent;
import org.greencity.ui.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class MySpaceBasePage extends BasePage {

    @FindBy(xpath = "//div[@class='left-column']")
    public WebElement profilePanel;
    @FindBy(xpath = "//div[@role='tablist']")
    public WebElement tabs;
    @FindBy(className = "app-calendar")
    public WebElement calendar;
    @FindBy(xpath = "(//div[@class='to-do-list-block'])[2]")
    public WebElement toDolist;
    @FindBy(xpath = "//p[@class='card-description']")
    protected WebElement factOfTheDay;
    @FindBy(xpath = "//div[@class='rate']//p")
    protected WebElement userRating;
    @FindBy(css = ".left-column .name")
    protected WebElement userName;
    @FindBy(css = ".right-column .item")
    protected List<WebElement> todoList;
    @FindBy(xpath = "//div[@role='tab']")
    protected List<WebElement> tabList;
    @FindBy(xpath = "//div[@role='tab' and @aria-selected='true']")
    protected WebElement activeTab;
    @FindBy(xpath = "//a[@class='edit-icon ng-star-inserted']")
    protected WebElement root;
    @FindBy(css = ".main-content.app-container")
    protected WebElement editProfile;
    By toDoItemslocator = By.xpath("(//div[@class='items-count'])[2]");

    public MySpaceBasePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open() {

    }

    @Override
    public boolean isPageOpened() {
        return isVisible(root);
    }

    public ProfilePanelComponent getProfilePanel() {
        return new ProfilePanelComponent(driver, profilePanel);
    }

    public String getFactOfTheDay() {
        wait.until(ExpectedConditions.visibilityOf(factOfTheDay));
        return factOfTheDay.getText();
    }

    public String getUserRating() {
        wait.until(ExpectedConditions.visibilityOf(userRating));
        return userRating.getText();
    }

    public String getUserName() {
        wait.until(ExpectedConditions.visibilityOf(userName));
        return userName.getText();
    }

    public void openProfile() {
        WebElement clickableEditProfile = wait.until(ExpectedConditions.elementToBeClickable(editProfile));
        clickableEditProfile.click();
    }

    public List<String> getToDoItems() {
        String itemCount = wait.until(ExpectedConditions.visibilityOfElementLocated(toDoItemslocator)).getText();
        int total = Integer.parseInt(itemCount.replaceAll("\\D+", ""));
        if (total == 0) {
            return Collections.emptyList();
        }
        return todoList.stream().map(WebElement::getText).toList();
    }

    public List<String> getTabList() {
        List<String> tabsList = new ArrayList<>();
        for (WebElement tab : tabList) {
            wait.until(ExpectedConditions.visibilityOf(tab));
            String text = tab.getText();
            tabsList.add(text);
        }
        return tabsList;
    }

    public String getActiveTab() {
        wait.until(ExpectedConditions.visibilityOf(activeTab));
        return activeTab.getText();
    }

    public void switchTo(String tabName) {
        for (WebElement tab : tabList) {
            wait.until(ExpectedConditions.visibilityOf(tab));
            if (tab.getText().equalsIgnoreCase(tabName)) {
                tab.click();
                return;
            }
        }
        throw new NoSuchElementException("Tab not found: " + tabName);
    }
}
