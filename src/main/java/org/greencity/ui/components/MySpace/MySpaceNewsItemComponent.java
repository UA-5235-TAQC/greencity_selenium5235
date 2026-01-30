package org.greencity.ui.components.MySpace;

import org.greencity.ui.components.BaseComponent;
import org.greencity.ui.pages.NewsDetailsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class MySpaceNewsItemComponent extends BaseComponent {

    @FindBy(css = "div.news")
    private WebElement newsContainer;

    @FindBy(css = ".news-image")
    private WebElement imageElement;

    @FindBy(css = ".tags .tag-btn")
    private List<WebElement> tags;

    @FindBy(css = ".title h3")
    private WebElement titleElement;

    @FindBy(css = ".user-info-date p")
    private WebElement creationDateElement;

    @FindBy(css = ".user-info-icon p")
    private WebElement authorNameElement;

    private final long newsId;

    public MySpaceNewsItemComponent(WebDriver driver, WebElement rootElement, long newsId) {
        super(driver, rootElement);
        this.newsId = newsId;
    }

    public Long getId() {
        return newsId;
    }

    public WebElement getImageElement() {
        return imageElement;
    }

    public String getImageSrc() {
        return imageElement.getAttribute("src");
    }

    public List<WebElement> getTagElements() {
        return tags;
    }

    public List<String> getTags() {
        return tags.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public WebElement getTitleElement() {
        return titleElement;
    }

    public String getTitle() {
        return titleElement.getText().trim();
    }

    public WebElement getCreationDateElement() {
        return creationDateElement;
    }

    public String getCreationDate() {
        return creationDateElement.getText().trim();
    }

    public WebElement getAuthorNameElement() {
        return authorNameElement;
    }

    public String getAuthorName() {
        return authorNameElement.getText().trim();
    }

    public NewsDetailsPage click() {
        newsContainer.click();
        return new NewsDetailsPage(driver, newsId);
    }
}
