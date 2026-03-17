package org.greencity.ui.pages;

import io.qameta.allure.Step;
import lombok.Getter;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @FindBy(css = "a > div.edit-news")
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

    @FindBy(css = ".news-info-date")
    private WebElement postDate;

    @FindBy(css = ".news-info-author")
    private WebElement authorName;

    @FindBy(css = ".ql-editor")
    private WebElement content;

    @FindBy(css = "img.news-image-img")
    private WebElement newsImage;

    @Getter
    private final long newsId;

    public NewsDetailsPage(WebDriver driver, long newsId) {
        super(driver);
        this.newsId = newsId;
    }

    @Step("Open news details page")
    @Override
    public NewsDetailsPage open() {
        driver.get(getBaseHost() + "/news/" + newsId);
        return this;
    }

    @Step("Check that news details page is opened")
    @Override
    public boolean isPageOpened() {
        return isVisible(newsTitleText);
    }

    @Step("Wait until news details page is loaded")
    @Override
    public NewsDetailsPage waitUntilOpened() {
        waitUntilVisible(newsTitleText);
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

    @Step("Check if Edit button is enabled")
    public boolean isEditButtonEnabled() {
        return editButton.isEnabled();
    }

    @Step("Get Edit button text")
    public String getEditButtonText() {
        waitUntilVisible(editButton);
        return editButton.getText().trim();
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
                .toList();
    }

    @Step("Get comments count")
    public int getCommentsCount() {
        return Integer.parseInt(getText(commentsCount).trim());
    }

    @Step("Get tag by index: {index}")
    public String getTagByIndex(int index) {
        return tags.get(index).getText();
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

    @Step("Get title text")
    public String getTitleValue() {
        return newsTitleText.getText().trim();
    }

    @Step("Check if tags are visible on page")
    public boolean areTagsVisible() {
        return areVisible(tags);
    }

    @Step("Check if post date is visible")
    public boolean isPostDateVisible() {
        return isVisible(authorName);
    }

    @Step("Get post date")
    public String getPostDate() {
        return postDate.getText().trim();
    }

    @Step("Check if author is visible")
    public boolean isAuthorVisible() {
        return isVisible(authorName);
    }

    @Step("Get author name")
    public String getAuthor() {
        return authorName.getText().substring(3).trim();
    }

    @Step("Check if content is visible")
    public boolean isContentVisible() {
        return isVisible(content);
    }

    @Step("Get content text")
    public String getContentText() {
        return content.getText();
    }

    @Step("Check if news image is visible")
    public boolean isNewsImageVisible() {
        return isVisible(newsImage);
    }

    @Step("Get news image src")
    public String getNewsImageSrc() {
        return newsImage.getAttribute("src");
    }

    @Step("Check if image is present")
    public boolean isNewsImagePresent() {
        String src = getNewsImageSrc();
        return src != null && src.startsWith("https://");
    }

    @Step("Get news id from URL")
    public long getNewsIdFromUrl() {
        String url = driver.getCurrentUrl();

        Pattern pattern = Pattern.compile("(?:id=|/news/)(\\d+)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }

        throw new RuntimeException("News ID not found in URL: " + url);
    }
}
