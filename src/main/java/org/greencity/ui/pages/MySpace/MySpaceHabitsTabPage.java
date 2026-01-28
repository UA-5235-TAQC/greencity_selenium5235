package org.greencity.ui.pages.MySpace;

import org.greencity.ui.components.MySpace.NoDataComponent;
import org.greencity.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MySpaceHabitsTabPage extends MySpaceBasePage {

    @FindBy(id = "create-button-add-new-habit")
    private WebElement addHabitButton;

    @FindBy(css = "div.no-data")
    private WebElement noDataRoot;

    private NoDataComponent noDataComponent;


    public MySpaceHabitsTabPage(WebDriver driver) {
        super(driver);
        this.noDataComponent = new NoDataComponent(driver, noDataRoot);
    }


    public boolean hasHabits() {
        return !noDataComponent.isDisplayed();
    }

    public void clickAddHabit() {
        wait.until(ExpectedConditions.elementToBeClickable(addHabitButton));
        addHabitButton.click();
    }

    public NoDataComponent getNoDataComponent() {
        return noDataComponent;
    }

    @Override
    public boolean isPageOpened() {
        return isVisible(addHabitButton);
    }

    @Override
    public BasePage waitUntilOpened() {
        wait.until(ExpectedConditions.visibilityOf(addHabitButton));
        return this;
    }
}
