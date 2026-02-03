package org.greencity.ui.pages.MySpace;

import org.greencity.ui.components.MySpace.NoDataComponent;
import org.greencity.ui.enums.MySpaceTab;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;


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

    public WebElement getAddHabitButton() { return addHabitButton; }

    @Step("Check if habits exist in My Space Habits tab")
    public boolean hasHabits() {
        return !noDataComponent.isDisplayed();
    }

    @Step("Click on Add Habit button")
    public void clickAddHabit() {
        wait.until(ExpectedConditions.elementToBeClickable(addHabitButton));
        addHabitButton.click();
    }

    @Step("Get 'No Data' component")
    public NoDataComponent getNoDataComponent() {
        return noDataComponent;
    }

    @Override
    @Step("Verify that My Space Habits tab is opened")
    public boolean isPageOpened() {
        return isVisible(addHabitButton);
    }

    @Override
    @Step("Wait until My Space Habits tab is opened")
    public MySpaceHabitsTabPage waitUntilOpened() {
        wait.until(ExpectedConditions.visibilityOf(addHabitButton));
        return this;
    }

    @Step("Switch from Habits tab to '{tab}' tab")
    public MySpaceNewsTabPage switchTo(MySpaceTab tab) {
        super.switchTo(tab);
        return new MySpaceNewsTabPage(driver);
    }
}
