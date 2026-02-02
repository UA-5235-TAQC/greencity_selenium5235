package org.greencity.ui.pages;

import io.qameta.allure.Step;
import org.greencity.ui.components.CommentItemComponent;
import org.greencity.ui.components.NewsDetailsContentComponent;
import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.pages.CreateEditNews.EditNewsPage;
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

    @FindBy(css = "#total-count")
    protected WebElement commentsCount;

    @FindBy(css = ".app-eco-news-widget")
    protected WebElement recommendedNews;

    @FindBy(css = ".news-title-container .news-title")
    protected WebElement newsTitleText;

    private final long newsId;

    public NewsDetailsPage(WebDriver driver, long newsId) {
        super(driver);
        this.newsId = newsId;
    }

    @Step("Open news details page by news id: {newsId}")
    @Override
    public NewsDetailsPage open() {
        driver.get(getBaseHost() + "/news/" + newsId);
        return this;
    }

    @Step("Check that news details page is opened")
    @Override
    public boolean isPageOpened() {
        return isVisible(root);
    }

    @Step("Wait until news details page is loaded")
    @Override
    public NewsDetailsPage waitUntilOpened() {
        return this;
    }

    @Step("Compare news title with expected title: \"{expectedTitle}\"")
    public boolean checkNewsTitle(String expectedTitle) {
        return newsTitleText.getText().trim().equalsIgnoreCase(expectedTitle.trim());
    }

    @Step("Click 'Back to news' button")
    public void clickBackToNewsButton() {
        click(backToNewsButton);
    }

    @Step("Click 'Delete news' button")
    public NewsDetailsPage clickDeleteButton() {
        click(deleteButton);
        return this;
    }

    @Step("Click 'Edit news' button")
    public EditNewsPage clickEditButton() {
        click(editButton);
        return new EditNewsPage(driver, newsId);
    }

    @Step("Click 'Like' button")
    public NewsDetailsPage clickLikeButton() {
        click(likeButton);
        return this;
    }

    @Step("Check if like is active on the page ")
    public boolean isLikeActive() {
        String src = likeButton.getAttribute("src");
        return src != null && src.contains("liked.png");
    }

    @Step("Add like to the news if it is not already added")
    public NewsDetailsPage addLike() {
        if (!isLikeActive()) {
            int initialCount = getLikesCount();
            click(likeButton);
            waitForLikesToChange(initialCount + 1);
        }
        return this;
    }

    @Step("Remove like from the news if it is active")
    public NewsDetailsPage deleteLike() {
        if (isLikeActive()) {
            int initialCount = getLikesCount();
            click(likeButton);
            waitForLikesToChange(initialCount - 1);
        }
        return this;
    }

    @Step("Wait until likes count changes to expected value: {expectedCount}")
    private void waitForLikesToChange(int expectedCount) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> getLikesCount() == expectedCount);
    }

    @Step("Get likes count")
    public int getLikesCount() {
        return Integer.parseInt(getText(likesCount).trim());
    }

    @Step("Get social links")
    public List<WebElement> getSocialLinks() {
        return socialLinks;
    }

    @Step("Get social icon names")
    public List<String> getSocialIconNames() {
        return socialLinks.stream()
                .map(icon -> icon.getAttribute("alt"))
                .collect(Collectors.toList());
    }

    @Step("Get news tags")
    public List<String> getTags() {
        return tags.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Get comments count")
    public int getCommentsCount() {
        return Integer.parseInt(getText(commentsCount).trim());
    }

    @Step("Get tag by index: {index}")
    public String getTagByIndex(int index) {
        return tags.get(index).getText();
    }

    @Step("Check if news details page root is visible")
    public boolean isPageVisible() {
        return isVisible(root);
    }

    @Step("Check if 'Back to news' button is visible")
    public boolean isBackToNewsButtonVisible() {
        return isVisible(backToNewsButton);
    }

    @Step("Check if 'Delete news' button is visible")
    public boolean isDeleteButtonVisible() {
        return isVisible(deleteButton);
    }

    @Step("Check if 'Edit news' button is visible")
    public boolean isEditButtonVisible() {
        return isVisible(editButton);
    }

    @Step("Check if likes count is visible")
    public boolean isLikesCountVisible() {
        return isVisible(likesCount);
    }

    @Step("Check if tag with name \"{tagName}\" is visible")
    public boolean isTagVisibleByName(String tagName) {
        return tags.stream()
                .filter(tag -> tag.getText().trim().equalsIgnoreCase(tagName))
                .findFirst()
                .map(this::isVisible)
                .orElse(false);
    }

    @Step("Check if comments container is visible")
    public boolean isCommentsContainerVisible() {
        return isVisible(commentsContainer);
    }

    @Step("Check if comments form is visible")
    public boolean isCommentsFormVisible() {
        return isVisible(commentsForm);
    }
}
