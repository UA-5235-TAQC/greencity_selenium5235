package org.greencity.ui.components.MySpace;

import lombok.Getter;
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

    @Getter
    @FindBy(css = ".news-image")
    private WebElement imageElement;

    @Getter
    @FindBy(css = ".tags .tag-btn")
    private List<WebElement> tagElements;

    @Getter
    @FindBy(css = ".title h3")
    private WebElement titleElement;

    @Getter
    @FindBy(css = ".user-info-date p")
    private WebElement creationDateElement;

    @Getter
    @FindBy(css = ".user-info-icon p")
    private WebElement authorNameElement;

    @Getter
    private final Long newsId;

    public MySpaceNewsItemComponent(WebDriver driver, WebElement rootElement, long newsId) {
        super(driver, rootElement);
        this.newsId = newsId;
    }

    public String getImageSrc() {
        return imageElement.getAttribute("src");
    }

    public List<String> getTags() {
        return tagElements.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public String getTitle() {
        return titleElement.getText().trim();
    }

    public String getCreationDate() {
        return creationDateElement.getText().trim();
    }

    public String getAuthorName() {
        return authorNameElement.getText().trim();
    }

    public NewsDetailsPage click() {
        newsContainer.click();
        return new NewsDetailsPage(driver, newsId);
    }
}
