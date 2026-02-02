package org.greencity.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TagItem extends BaseComponent {

    @FindBy(css = "a.global-tag .text")
    protected WebElement name;

    @FindBy(css = "a.global-tag div")
    protected WebElement closeIcon;

    public TagItem(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Get tag name")
    public String getName() {
        return name.getText();
    }

    @Step("Verify if tag is selected")
    public boolean isSelected() {
        String classes = closeIcon.getAttribute("class");
        return classes != null && classes.contains("global-tag-close-icon");
    }

    @Step("Click on tag")
    public void click() {
        name.click();
    }
}
