package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsListItemComponent extends BaseComponent {
    @FindBy(css = "a.link")
    protected WebElement newsLink;

    public NewsListItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public int getNewsId() {
        String href = newsLink.getAttribute("href");
        String[] parts = href.split("/news/");

        if (parts.length != 2) {
            throw new IllegalStateException("Cannot extract news id from href: " + href);
        }

        return Integer.parseInt(parts[1].replaceAll("\\D.*", ""));
    }

    public void click() {
        newsLink.click();
    }
}
