package org.greencity.ui.pages;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MySpaceNewsTabPage extends MySpaceBasePage {

    @FindBy(css = "ul.news-list > li")
    private List<WebElement> newsList;

    @FindBy(css = ".filters .custom-chip.global-tag")
    private List<WebElement> tags;

    @FindBy(id = "create-button-news")
    private WebElement addNewsButton;

    @FindBy(css = "app-set-count span")
    private WebElement newsCountLabel;

    @FindBy(css = "div.favourites")
    private WebElement favouritesButton;


    public MySpaceNewsTabPage(WebDriver driver) {
        super(driver);
    }


    public List<MySpaceNewsListItemComponent> getNewsList() {
        wait.until(ExpectedConditions.visibilityOfAllElements(newsList));

        return newsList.stream()
                .map(item -> new MySpaceNewsListItemComponent(driver, item))
                .toList();
    }

    public void filterByTag(String tagName) {
        WebElement tag = tags.stream()
                .filter(t -> t.getText().equalsIgnoreCase(tagName))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Tag not found: " + tagName));

        tag.click();
    }

    public List<String> getAllTags() {
        return tags.stream()
                .map(WebElement::getText)
                .toList();
    }

    public void clickAddNews() {
        waitForVisible(addNewsButton);
        addNewsButton.click();
    }

    private void waitForVisible(WebElement addNewsButton2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'waitForVisible'");
    }

    public int getNewsCount() {
        waitForVisible(newsCountLabel);
        return Integer.parseInt(newsCountLabel.getText());
    }

    public void clickFavourites() {
        waitForVisible(favouritesButton);
        favouritesButton.click();
    }
}
