package org.greencity.ui.pages.MySpace;

import org.greencity.ui.components.BaseComponent;
import org.greencity.ui.pages.NewsDetailsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MySpaceNewsListItemComponent extends BaseComponent {
    public MySpaceNewsListItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public WebElement getRoot() {
        return rootElement;
    }

    public NewsDetailsPage open() {
        rootElement.click();
        return new NewsDetailsPage(driver);
    }
}
