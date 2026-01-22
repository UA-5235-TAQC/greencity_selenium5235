package org.greencity.ui.pages;

import org.greencity.ui.components.NewsListItemComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class EcoNewsPage extends BasePage {
    public EcoNewsPage(WebDriver driver) {
        super(driver);
    }

    public List<NewsListItemComponent> getNewsList() {
        return driver.findElements(By.cssSelector("li.gallery-view-li-active"))
                .stream()
                .map(element -> new NewsListItemComponent(driver, element))
                .collect(Collectors.toList());
    }
}