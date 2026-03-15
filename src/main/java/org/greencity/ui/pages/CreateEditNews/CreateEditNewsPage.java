package org.greencity.ui.pages.CreateEditNews;

import io.qameta.allure.Step;
import lombok.Getter;
import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.components.CreateEditNewsPage.ContentComponent;
import org.greencity.ui.components.CreateEditNewsPage.ImageComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateEditNewsPage extends BasePage {

    @FindBy(css = "div.main-content")
    private WebElement root;
    @Getter
    @FindBy(css = "textarea[formcontrolname='title']")
    private WebElement titleInput;
    @FindBy(css = "div.title h2.title-header")
    protected WebElement pageTitleHeader;
    @FindBy(css = "div.tags-box button.tag-button")
    private List<WebElement> tagRootElements;
    @Getter
    @FindBy(css = "input[formcontrolname='source']")
    private WebElement sourceInput;
    @FindBy(css = "div.image-block")
    private WebElement imageRoot;
    @FindBy(css = "div.source-block")
    private WebElement sourceMessage;
    @FindBy(css = ".submit-buttons button.tertiary-global-button")
    private WebElement cancelBtn;
    @FindBy(css = ".submit-buttons button.secondary-global-button")
    private WebElement previewBtn;
    @FindBy(css = ".title-block div span.field-info")
    private WebElement titleCharacterCounter;
    @FindBy(css = "div.date p:nth-of-type(1) span:last-child")
    private WebElement postDate;
    @FindBy(css = "div.date p:nth-of-type(2) span:last-child")
    private WebElement authorName;
    @FindBy(css = "div.textarea-wrapper")
    private WebElement contentRoot;
    @FindBy(css = "mat-dialog-container app-warning-pop-up")
    private WebElement cancelModalContainer;

    public CreateEditNewsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CreateEditNewsPage open() {
        driver.get(getBaseHost() + "/news/create-news");
        return this;
    }

    @Override
    @Step("Check if Create/Edit News page is opened")
    public boolean isPageOpened() {
        return isVisible(titleInput);
    }

    @Override
    @Step("Wait until Create/Edit News page is opened")
    public CreateEditNewsPage waitUntilOpened() {
        waitUntilVisible(titleInput);
        return this;
    }

    @Step("Safely check if Create/Edit News page is opened (catching browser exceptions)")
    public boolean isPageOpenedSafe() {
        try {
            return isVisible(titleInput);
        } catch (UnreachableBrowserException e) {
            return false;
        }
    }

    @Step("Check if page header is visible after clicking Back to creating/editing in NewsPreviewPage")
    public boolean isPageOpenedAfterPreviewClickBack() {
        return isVisible(pageTitleHeader);
    }

    @Step("Enter news title: {title}")
    public CreateEditNewsPage enterTitle(String title) {
        waitUntilVisible(titleInput);
        clearField(titleInput);
        titleInput.sendKeys(title);
        return this;
    }

    @Step("Get all tag items on page")
    public List<TagItem> getTagItems() {
        return tagRootElements.stream().map(root -> new TagItem(driver, root)).collect(Collectors.toList());
    }

    private TagItem getTagByName(String tagName) {
        return getTagItems().stream()
                .filter(tag -> tag.getName().equalsIgnoreCase(tagName))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Tag not found: " + tagName)
                );
    }

    @Step("Click tag by name: {tagName}")
    public CreateEditNewsPage clickTagByName(String tagName) {
        getTagByName(tagName).click();
        return this;
    }

    @Step("Select multiple tags: {tagNames}")
    public CreateEditNewsPage selectTags(List<String> tagNames) {
        tagNames.forEach(tagName -> {
            TagItem tag = getTagByName(tagName);

            if(!tag.isSelected()) {
                tag.click();
            }
        });

        return this;
    }

    @Step("Get list of selected tags")
    public List<String> getSelectedTags() {
        return getTagItems().stream()
                .filter(TagItem::isSelected)
                .map(TagItem::getName)
                .toList();
    }

    @Step("Get list of all tags")
    public List<String> getAllTags() {
        return getTagItems().stream().map(TagItem::getName).toList();
    }

    @Step("Remove tag: {tagName}")
    public CreateEditNewsPage removeTag(String tagName) {
        TagItem tag = getTagByName(tagName);
        if (tag.isSelected()) {
            tag.click();
        }
        return this;
    }

    @Step("Check if tags are visible on page")
    public boolean areTagsVisible() {
        return areVisible(tagRootElements);
    }

    @Step("Clear all selected tags")
    public CreateEditNewsPage clearAllSelectedTags() {
        getSelectedTags().forEach(this::removeTag);
        return this;
    }

    @Step("Enter news source URL: {url}")
    public CreateEditNewsPage enterSource(String url) {
        waitUntilVisible(sourceInput);
        clearSourceField();
        sourceInput.sendKeys(url);
        return this;
    }

    @Step("Get Image component")
    public ImageComponent getImageComponent() {
        return new ImageComponent(driver, imageRoot);
    }

    @Step("Get Content component")
    public ContentComponent getContentComponent() {
        return new ContentComponent(driver, contentRoot);
    }

    @Step("Check if title field is highlighted in red because it's empty")
    public boolean isTitleInvalid() {
        String classAttribute = titleInput.getAttribute("class");
        return classAttribute != null && classAttribute.contains("ng-invalid");
    }

    @Step("Get title text")
    public String getTitleValue() {
        return titleInput.getAttribute("value");
    }

    @Step("Check if source input is visible")
    public boolean isSourceVisible() {
        return isVisible(sourceInput);
    }

    @Step("Get source validation message text")
    public String getSourceMessage() {
        return sourceMessage.getText().trim();
    }

    @Step("Check if Cancel button is visible")
    public boolean isCancelButtonVisible() {
        return isVisible(cancelBtn);
    }

    @Step("Click Cancel button")
    public CreateEditNewsPage clickCancel() {
        cancelBtn.click();
        return this;
    }

    @Step("Check if Preview button is visible")
    public boolean isPreviewButtonVisible() {
        return isVisible(previewBtn);
    }

    @Step("Click Preview button")
    public NewsPreviewPage clickPreview() {
        previewBtn.click();
        return new NewsPreviewPage(driver);
    }

    @Step("Get title character counter text (shows number of characters in title)")
    public String getTitleCounterText() {
        return titleCharacterCounter.getText();
    }

    @Step("Get length of title text")
    public int getTitleLength() {
        return getTitleValue().length();
    }

    @Step("Get Cancel modal component")
    public CancelModalComponent getCancelModal() {
        waitUntilVisible(cancelModalContainer);
        return new CancelModalComponent(driver, cancelModalContainer);
    }

    @Step("Check if Cancel modal is displayed")
    public boolean isCancelModalDisplayed() {
        try {
            waitUntilVisible(cancelModalContainer);
            return cancelModalContainer.isDisplayed();
        } catch (NoSuchElementException | TimeoutException | UnreachableBrowserException e) {
            return false;
        }
    }

    @Step("Check if author is visible")
    public boolean isAuthorVisible() {
        return isVisible(authorName);
    }

    @Step("Get author name")
    public String getAuthor() {
        return authorName.getText().trim();
    }

    @Step("Check if post date is visible")
    public boolean isPostDateVisible() {
        return isVisible(postDate);
    }

    @Step("Get post date")
    public String getPostDate() {
        return postDate.getText().trim();
    }

    @Step("Get news source text")
    public String getSource() {
        return Objects.requireNonNull(sourceInput.getAttribute("value")).trim();
    }

    @Step("Get placeholder text of source input")
    public String getSourcePlaceholder() {
        return sourceInput.getAttribute("placeholder").trim();
    }

    @Step("Clear source text")
    public CreateEditNewsPage clearSourceField() {
        clearField(sourceInput);
        return this;
    }

    public CreateEditNewsPage reload() {
        driver.navigate().refresh();
        wait.until(driver -> isPageOpenedSafe());
        return this;
    }

    @Step("Get Cancel button text")
    public String getCancelButtonText() {
        waitUntilVisible(cancelBtn);
        return cancelBtn.getText().trim();
    }

    @Step("Get Preview button text")
    public String getPreviewButtonText() {
        waitUntilVisible(previewBtn);
        return previewBtn.getText().trim();
    }

    @Step("Append text to title: {additionalText}")
    public CreateEditNewsPage appendTitle(String additionalText) {
        getTitleInput().sendKeys(additionalText);
        return this;
    }

    @Step("Prepend text to title: {textToAdd}")
    public CreateEditNewsPage prependTitle(String textToAdd) {
        WebElement title = getTitleInput();
        String currentValue = title.getAttribute("value");
        String newValue = textToAdd + (currentValue != null ? currentValue : "");
        clearField(title);
        title.sendKeys(newValue);
        return this;
    }

    private CreateEditNewsPage removeTitleChars(int count, boolean fromStart) {
        WebElement title = getTitleInput();
        String currentValue = title.getAttribute("value");
        if (currentValue != null && !currentValue.isEmpty()) {
            String newValue;
            if (fromStart) {
                newValue = currentValue.length() > count ? currentValue.substring(count) : "";
            } else {
                newValue = currentValue.length() > count ? currentValue.substring(0, currentValue.length() - count) : "";
            }
            clearField(title);
            title.sendKeys(newValue);
        }
        return this;
    }

    @Step("Remove last {count} characters from title")
    public CreateEditNewsPage removeLastTitleChars(int count) {
        return removeTitleChars(count, false);
    }

    @Step("Remove first {count} characters from title")
    public CreateEditNewsPage removeFirstTitleChars(int count) {
        return removeTitleChars(count, true);
    }
}
