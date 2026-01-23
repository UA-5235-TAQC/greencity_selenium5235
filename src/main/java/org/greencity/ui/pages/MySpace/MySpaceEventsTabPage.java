package org.greencity.ui.pages.MySpace;

import java.util.List;

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


    public int getEventsCount() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfAllElements(eventsList),
                ExpectedConditions.visibilityOf(noDataRoot)));
        if (noDataComponent.isDisplayed()) {
            return 0;
        }
        return eventsList.size();
    }

    public List<WebElement> getEvents() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfAllElements(eventsList),
                ExpectedConditions.visibilityOf(noDataRoot)));
        return eventsList;
    }

    public void clickAddEvent() {
        wait.until(ExpectedConditions.elementToBeClickable(addEventButton));
        addEventButton.click();
    }

    public void clickJoinEvent() {
        wait.until(ExpectedConditions.elementToBeClickable(joinEventButton));
        joinEventButton.click();
    }

    public void setOnline(boolean enabled) {
        wait.until(ExpectedConditions.elementToBeClickable(onlineCheckbox));
        if (onlineCheckbox.isSelected() != enabled) {
            onlineCheckbox.click();
        }
    }

    public void setOffline(boolean enabled) {
        wait.until(ExpectedConditions.elementToBeClickable(offlineCheckbox));
        if (offlineCheckbox.isSelected() != enabled) {
            offlineCheckbox.click();
        }
    }

    public NoDataComponent getNoDataComponent() {
        return noDataComponent;
    }
}
