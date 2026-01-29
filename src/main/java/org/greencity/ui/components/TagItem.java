package org.greencity.ui.components;

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

    public String getName() {
        return name.getText();
    }

    public boolean isSelected() {
        String classes = closeIcon.getAttribute("class");
        return classes != null && classes.contains("global-tag-close-icon");
    }

    public void click() {
        name.click();
    }
}
