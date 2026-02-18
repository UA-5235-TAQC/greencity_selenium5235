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
    @FindBy(css = ".list-image-content")
    private WebElement image;

    @FindBy(css = ".favourite-button")
    @Getter
    private WebElement bookmarkBtn;

    @FindBy(css = ".filter-tag div")
    private List<WebElement> tagElements;

    @FindBy(css = ".title-list")
    private WebElement title;

    @FindBy(css = ".list-text")
    private WebElement newsText;

    @FindBy(css = ".text-nowrap>span")
    private WebElement creationDate;

    @FindBy(css = ".mw")
    private WebElement authorName;

    @FindBy(xpath = ".//img[contains(@alt, 'comment')]/parent::*/span")
    private WebElement commentsCount;

    @FindBy(xpath = ".//img[contains(@alt, 'likes')]/parent::*/span")
    private WebElement likesCount;

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

    public WebElement getImageElement() {
        return image;
    }

    public List<WebElement> getTagsList() {
        return tagElements;
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

    public WebElement getTitleElement() {
        return title;
    }

    public String getTitle() {
        return title.getText();
    }

    public WebElement getNewsTextElement() {
        return newsText;
    }

    public String getNewsText() {
        return newsText.getText();
    }

    public WebElement getCreationDateElement() {
        return creationDate;
    }

    public String getCreationDate() {
        return creationDate.getText();
    }

    public WebElement getAuthorNameElement() {
        return authorName;
    }

    public String getAuthorName() {
        return authorName.getText();
    }

    public WebElement getCommentsCountElement() {
        return commentsCount;
    }

    public int getCommentsCount() {
        return Integer.parseInt(commentsCount.getText());
    }

    public WebElement getLikesCountElement() {
        return likesCount;
    }

    public int getLikesCount() {
        return Integer.parseInt(likesCount.getText());
    }

    public NewsDetailsPage click() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".cdk-overlay-backdrop-showing")
        ));
        image.click();
        return new NewsDetailsPage(driver, getNewsId());
    }
}
