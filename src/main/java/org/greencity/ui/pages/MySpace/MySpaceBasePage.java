package org.greencity.ui.pages.MySpace;

import io.qameta.allure.Step;
import org.greencity.ui.components.MySpace.ProfilePanelComponent;
import org.greencity.ui.enums.MySpaceTab;
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
    @FindBy(css = ".mat-mdc-tab-labels")
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

    @Step("Open My Space Page")
    @Override
    public MySpaceBasePage open() {
        driver.get(getBaseHost() + "/profile");
        return this;
    }

    @Step("Check if My Space Page is opened")
    @Override
    public boolean isPageOpened() {
        return isVisible(profilePanel);
    }

    @Step("Wait until My Space Page is loaded")
    @Override
    public MySpaceBasePage waitUntilOpened() {
        waitUntilVisible(profilePanel);
        return this;
    }

    @Step("Create Profile Component")
    public ProfilePanelComponent getProfilePanel() {
        return new ProfilePanelComponent(driver, profilePanel);
    }

    @Step("Get fact of the day")
    public String getFactOfTheDay() {
        waitUntilVisible(factOfTheDay);
        return factOfTheDay.getText();
    }

    @Step("Get user rating")
    public String getUserRating() {
        waitUntilVisible(userRating);
        return userRating.getText();
    }

    @Step("Get user name")
    public String getUserName() {
        waitUntilVisible(userName);
        return userName.getText();
    }

    @Step("Open edit profile page")
    public void openProfile() {
        waitUntilClickable(editProfile);
        editProfile.click();
    }

    @Step("Get list of to-do items")
    public List<String> getToDoItems() {
        String itemCount = wait.until(ExpectedConditions.visibilityOfElementLocated(toDoItemslocator)).getText();
        int total = Integer.parseInt(itemCount.replaceAll("\\D+", ""));
        if (total == 0) {
            return Collections.emptyList();
        }
        return todoList.stream().map(WebElement::getText).toList();
    }

    @Step("Get list of tabs")
    public List<String> getTabList() {
        List<String> tabsList = new ArrayList<>();
        for (WebElement tab : tabList) {
            wait.until(ExpectedConditions.visibilityOf(tab));
            String text = tab.getText();
            tabsList.add(text);
        }
        return tabsList;
    }

    @Step("Get active tab")
    public String getActiveTab() {
        waitUntilVisible(activeTab);
        return activeTab.getText();
    }

    @Step("Switch to {tab}")
    public MySpaceBasePage switchTo(MySpaceTab tab) {
        waitUntilVisible(tabList);
        for (WebElement el : tabList) {
            if (tab.matches(el.getText())) {
                el.click();
                return this;
            }
        }
        throw new NoSuchElementException("Tab not found: " + tab);
    }
}
