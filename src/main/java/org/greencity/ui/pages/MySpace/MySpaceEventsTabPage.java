package org.greencity.ui.pages.MySpace;

import java.util.List;

import io.qameta.allure.Step;
import org.greencity.ui.components.MySpace.NoDataComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MySpaceEventsTabPage extends MySpaceBasePage {

    @FindBy(css = "ul.events-list > li")
    private List<WebElement> eventsList;

    @FindBy(xpath = "//mat-checkbox[.//label[contains(text(),'Online')]]//input[@type='checkbox']")
    private WebElement onlineCheckbox;

    @FindBy(xpath = "//mat-checkbox[.//label[contains(text(),'Offline')]]//input[@type='checkbox']")
    private WebElement offlineCheckbox;

    @FindBy(css = "app-no-data")
    private WebElement noDataRoot;

    @FindBy(id = "create-button-event")
    private WebElement addEventButton;

    @FindBy(id = "create-button-join-event")
    private WebElement joinEventButton;

    private NoDataComponent noDataComponent;

    public MySpaceEventsTabPage(WebDriver driver) {
        super(driver);
        this.noDataComponent = new NoDataComponent(driver, noDataRoot);
    }


    @Step("Get number of events in My Space Events tab")
    public int getEventsCount() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfAllElements(eventsList),
                ExpectedConditions.visibilityOf(noDataRoot)));
        if (noDataComponent.isDisplayed()) {
            return 0;
        }
        return eventsList.size();
    }
    
    @Step("Get list of events from My Space Events tab")
    public List<WebElement> getEvents() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfAllElements(eventsList),
                ExpectedConditions.visibilityOf(noDataRoot)));
        return eventsList;
    }

    @Step("Click on Add Event button")
    public void clickAddEvent() {
        wait.until(ExpectedConditions.elementToBeClickable(addEventButton));
        addEventButton.click();
    }

    @Step("Click on Join Event button")
    public void clickJoinEvent() {
        wait.until(ExpectedConditions.elementToBeClickable(joinEventButton));
        joinEventButton.click();
    }

    @Step("Set 'Online' filter to {enabled}")
    public void setOnline(boolean enabled) {
        wait.until(ExpectedConditions.elementToBeClickable(onlineCheckbox));
        if (onlineCheckbox.isSelected() != enabled) {
            onlineCheckbox.click();
        }
    }

    @Step("Set 'Offline' filter to {enabled}")
    public void setOffline(boolean enabled) {
        wait.until(ExpectedConditions.elementToBeClickable(offlineCheckbox));
        if (offlineCheckbox.isSelected() != enabled) {
            offlineCheckbox.click();
        }
    }

    @Step("Get 'No Data' component")
    public NoDataComponent getNoDataComponent() {
        return noDataComponent;
    }
}
