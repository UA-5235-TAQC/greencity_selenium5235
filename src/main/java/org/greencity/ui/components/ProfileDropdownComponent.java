package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProfileDropdownComponent extends BaseComponent {

    @FindBy(css = "a")
    private List<WebElement> links;

    public ProfileDropdownComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public void openNotifications() {
        wait.until(ExpectedConditions.visibilityOfAllElements(links));
        links.getFirst().click();
    }

    public void openPersonalAccount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(links));
        links.get(1).click();
    }

    public void signOut() {
        wait.until(ExpectedConditions.visibilityOfAllElements(links));
        links.getLast().click();
    }
}
