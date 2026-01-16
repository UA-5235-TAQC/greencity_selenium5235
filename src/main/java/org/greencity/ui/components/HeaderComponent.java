package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BaseComponent {
    @FindBy(xpath = "//a[contains(@href, '#/greenCity/news')]")
    protected WebElement ecoNewsLink;

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public void clickEcoNewsLink() {
        ecoNewsLink.click();
    }
}
