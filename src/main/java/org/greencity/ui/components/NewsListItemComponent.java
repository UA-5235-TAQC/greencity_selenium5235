package org.greencity.ui.components;

import lombok.Getter;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.NewsDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class NewsListItemComponent extends BaseComponent {
    @Getter
    @FindBy(css = ".list-image-content")
    private WebElement image;

    @FindBy(css = ".favourite-button")
    @Getter
    private WebElement bookmarkBtn;

    @Getter
    @FindBy(css = ".filter-tag div")
    private List<WebElement> tagElements;

    @Getter
    @FindBy(css = ".title-list")
    private WebElement titleElement;

    @Getter
    @FindBy(css = ".list-text")
    private WebElement newsTextElement;

    @Getter
    @FindBy(css = ".text-nowrap>span")
    private WebElement creationDateElement;

    @Getter
    @FindBy(css = ".mw")
    private WebElement authorNameElement;

    @Getter
    @FindBy(xpath = ".//img[contains(@alt, 'comment')]/parent::*/span")
    private WebElement commentsCountElement;

    @Getter
    @FindBy(xpath = ".//img[contains(@alt, 'likes')]/parent::*/span")
    private WebElement likesCountElement;

    @Getter
    private final long newsId;

    public NewsListItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
        this.newsId = 0;
    }

    public EcoNewsPage clickBookmark() {
        bookmarkBtn.click();
        return new EcoNewsPage(driver);
    }

    public boolean hasTags(List<String> tagNames) {
        List<String> displayedTags = tagElements.stream()
                .map(tag -> tag.getText().replace("|", "").trim())
                .toList();

        // NOTE: tag names are capitalized, but in NewsListItemComponent they are displayed in uppercase
        List<String> expectedTags = tagNames.stream().map(String::toUpperCase).toList();

        return displayedTags.size() == expectedTags.size()
                && expectedTags.stream().allMatch(displayedTags::contains);
    }

    public String getTitle() {
        return titleElement.getText();
    }

    public String getNews() {
        return newsTextElement.getText();
    }

    public String getCreationDate() {
        return creationDateElement.getText();
    }

    public String getAuthorName() {
        return authorNameElement.getText();
    }

    public int getCommentsCount() {
        return Integer.parseInt(commentsCountElement.getText());
    }

    public int getLikesCount() {
        return Integer.parseInt(likesCountElement.getText());
    }

    public NewsDetailsPage click() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".cdk-overlay-backdrop-showing")
        ));
        image.click();
        return new NewsDetailsPage(driver, getNewsId());
    }
}
