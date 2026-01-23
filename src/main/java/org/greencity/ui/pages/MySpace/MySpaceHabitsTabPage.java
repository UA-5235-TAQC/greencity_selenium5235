package org.greencity.ui.pages.MySpace;

import org.greencity.ui.components.MySpace.ProfileCardsComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MySpaceHabitsTabPage extends MySpaceBasePage {

    @FindBy(id = "create-button-add-new-habit")
    private WebElement addHabitButton;

    @FindBy(css = "div.no-data")
    private WebElement noDataRoot;


    private ProfileCardsComponent.NoDataComponent noDataComponent;


    public MySpaceHabitsTabPage(WebDriver driver) {
        super(driver);
        this.noDataComponent = new ProfileCardsComponent.NoDataComponent(driver, noDataRoot);
    }

    
    public boolean hasHabits() {
        return !noDataComponent.isVisible();
    }

    public void clickAddHabit() {
        wait.until(ExpectedConditions.elementToBeClickable(addHabitButton));
        addHabitButton.click();
    }

    public ProfileCardsComponent.NoDataComponent getNoDataComponent() {
        return noDataComponent;
    }
}
