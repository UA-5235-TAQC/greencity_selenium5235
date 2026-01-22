package org.greencity.ui.pages;

import org.greencity.ui.components.CommentItemComponent;
import org.greencity.ui.components.NewsDetailsContentComponent;
import org.greencity.ui.components.NewsListItemComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class NewsDetailsPage extends BasePage {

    protected List<CommentItemComponent> componentsList;
    protected NewsDetailsContentComponent newsDetailsContentComponent;
    protected List<NewsListItemComponent> newsList;

    public NewsDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "main-content app-container")
    protected WebElement root;

    @FindBy(css = ".button-link")
    protected WebElement backToNewsButton;

    @FindBy(css = ".secondary-global-button.delete-news-button")
    protected WebElement deleteButton;

    @FindBy(css = "a.edit-news")
    protected WebElement editButton;

    @FindBy(css = "img.news_like")
    protected WebElement likeButton;

    @FindBy(css = ".like_wr .numerosity_likes")
    protected WebElement likesCount;

    @FindBy(css = ".news-links-images img")
    protected List<WebElement> socialLinks;

    @FindBy(css = ".tags .tags-item")
    protected List<WebElement> tags;

    @FindBy(xpath = "(//app-comments-container)[1]")
    protected WebElement commentsContainer;

    @FindBy(css = ".app-add-comment form")
    protected WebElement commentsForm;

    @FindBy(css = "p.total-count.total-count span")
    protected WebElement commentsCount;

    @FindBy(css = ".app-eco-news-widget")
    protected WebElement recommendedNews;

    @FindBy(css = ".news-title-container .news-title")
    protected WebElement newsTitleText;

    public void open(String newsId) {
        driver.get("https://www.greencity.cx.ua/#/greenCity/news/" + newsId);
    }

    @Override
    public boolean isPageOpened() {
        return isVisible(root);
    }

    public boolean checkNewsTitle(String expectedTitle) {
        return newsTitleText.getText().trim().equalsIgnoreCase(expectedTitle.trim());
    }

    public void clickBackToNewsButton() {
        click(backToNewsButton);
    }

    public NewsDetailsPage clickDeleteButton() {
        click(deleteButton);
        return this;
    }


    public NewsDetailsPage clickEditButton() {
        click(editButton);
        return this;
    }

    public NewsDetailsPage clickLikeButton() {
        click(likeButton);
        return this;
    }

    public boolean isLikeActive() {
        String src = likeButton.getAttribute("src");
        return src != null && src.contains("liked.png");
    }

    public NewsDetailsPage addLike() {
        if (!isLikeActive()) {
            int initialCount = getLikesCount();
            click(likeButton);
            waitForLikesToChange(initialCount + 1);
        }
        return this;
    }

    public NewsDetailsPage deleteLike() {
        if (isLikeActive()) {
            int initialCount = getLikesCount();
            click(likeButton);
            waitForLikesToChange(initialCount - 1);
        }
        return this;
    }


    private void waitForLikesToChange(int expectedCount) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> getLikesCount() == expectedCount);
    }
    // --- Getters ---

    public int getLikesCount() {
        return Integer.parseInt(getText(likesCount).trim());
    }

    public List<WebElement> getSocialLinks() {
        return socialLinks;
    }

    public List<String> getSocialIconNames() {
        return socialLinks.stream()
                .map(icon -> icon.getAttribute("alt"))
                .collect(Collectors.toList());
    }

    public List<String> getTags() {
        return tags.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getCommentsCount() {
        return Integer.parseInt(getText(commentsCount).trim());
    }


    public String getTagByIndex(int index) {
        return tags.get(index).getText();
    }

    public boolean isPageVisible() {
        return isVisible(root);
    }

    public boolean isBackToNewsButtonVisible() {
        return isVisible(backToNewsButton);
    }

    public boolean isDeleteButtonVisible() {
        return isVisible(deleteButton);
    }

    public boolean isEditButtonVisible() {
        return isVisible(editButton);
    }

    public boolean isLikesCountVisible() {
        return isVisible(likesCount);
    }

    public boolean isTagVisibleByName(String tagName) {
        return tags.stream()
                .filter(tag -> tag.getText().trim().equalsIgnoreCase(tagName))
                .findFirst()
                .map(this::isVisible)
                .orElse(false);
    }

    public  boolean isCommentsContainerVisible() {
        return isVisible(commentsContainer);
    }

    public boolean isCommentsFormVisible() {
        return isVisible(commentsForm);
    }

    public NewsDetailsPage addComment(String text, CommentItemComponent commentItemComponent) {
        if (isCommentsFormVisible()) {
            commentItemComponent.setCommentText(text);
            commentItemComponent.clickSubmitButton();
        }
        return this;
    }
}
