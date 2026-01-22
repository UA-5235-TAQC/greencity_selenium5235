package org.greencity.ui.pages;

import org.greencity.ui.components.CommentItemComponent;
import org.greencity.ui.components.NewsDetailsContentComponent;
import org.greencity.ui.components.NewsListItemComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewsDetailsPage extends BasePage {

    protected CommentItemComponent commentItemComponent;
    protected NewsDetailsContentComponent newsDetailsContentComponent;
    protected NewsListItemComponent newsListItemComponent;

    public NewsDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "main-content app-container")
    protected WebElement root;

    @FindBy(css = ".button-link")
    protected WebElement backToNewsButton;

    @FindBy(css = ".secondary-global-button.delete-news-button")
    protected WebElement deleteButton;

    @FindBy(css = "a .edit-news")
    protected WebElement editButton;

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

    public void clickBackToNewsButton() {
        click(backToNewsButton);
    }

    public void clickDeleteButton() {
        click(deleteButton);
    }

    public void clickEditButton() {
        click(editButton);
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

    public void addComment(String text, CommentItemComponent commentItemComponent) {
        if (isCommentsFormVisible()) {
            commentItemComponent.setCommentText(text);
            commentItemComponent.clickSubmitButton();
        } else {
            throw new IllegalStateException("Comments form is not visible!");
        }
    }


}
