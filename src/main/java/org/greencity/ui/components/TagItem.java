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

    protected boolean isSelected() {
        String classes = this.closeIcon.getAttribute("class");

        return classes != null && classes.contains("global-tag-close-icon");
    }

    public void select() {
        if (!this.isSelected()) {
            this.rootElement.click();
        } else {
            throw new IllegalStateException("Tag is already selected.");
        }
    }

    public void remove() {
        if (this.isSelected()) {
            this.rootElement.click();
        } else {
            throw new IllegalStateException("Can't remove a tag that's not selected.");
        }
    }
}
