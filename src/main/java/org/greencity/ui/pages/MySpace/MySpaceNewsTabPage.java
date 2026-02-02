package org.greencity.ui.pages.MySpace;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.greencity.ui.components.MySpace.MySpaceNewsItemComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public List<MySpaceNewsItemComponent> getNewsList() {
        waitUntilVisible(newsList);

        List<MySpaceNewsItemComponent> components = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            WebElement item = newsList.get(i);
            long newsId = i + 1;
            components.add(new MySpaceNewsItemComponent(driver, item, newsId));
        }
        return components;
    }

    public MySpaceNewsItemComponent getFirstNews() {
        List<MySpaceNewsItemComponent> news = getNewsList();
        if (news.isEmpty()) {
            throw new NoSuchElementException("No news found on the page");
        }
        return news.getFirst();
    }

    public MySpaceNewsItemComponent getNewsById(Long newsId) {
        List<MySpaceNewsItemComponent> news = getNewsList();
        return news.stream()
                .filter(n -> n.getId().equals(newsId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("News with ID " + newsId + " not found"));
    }

    public void filterByTag(String tagName) {
        WebElement tag = tags.stream()
                .filter(t -> t.getText().trim().equalsIgnoreCase(tagName))
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
        waitUntilClickable(addNewsButton);
        addNewsButton.click();
    }

    public int getNewsCount() {
        waitUntilVisible(newsCountLabel);
        String text = newsCountLabel.getText().trim();
        String numberOnly = text.split(" ")[0];
        return Integer.parseInt(numberOnly);
    }

    public void clickFavourites() {
        waitUntilClickable(favouritesButton);
        favouritesButton.click();
    }
}
