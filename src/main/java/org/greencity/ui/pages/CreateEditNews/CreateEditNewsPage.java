package org.greencity.ui.pages.CreateEditNews;

import io.qameta.allure.Step;
import lombok.Getter;
import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.components.CreateEditNewsPage.ContentComponent;
import org.greencity.ui.components.CreateEditNewsPage.ImageComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.pages.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Page Object class representing the "Create or Edit News" page.
 * Provides methods for interacting with title, tags, source, image, and content.
 */
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

    /**
     * Navigates to the Create News page URL.
     * @return Current instance of CreateEditNewsPage.
     */
    @Override
    public CreateEditNewsPage open() {
        driver.get(getBaseHost() + "/news/create-news");
        return this;
    }

    /**
     * Checks if the page is opened by verifying the visibility of the title input.
     * @return true if the title input is visible.
     */
    @Override
    @Step("Check if Create/Edit News page is opened")
    public boolean isPageOpened() {
        return isVisible(titleInput);
    }

    /**
     * Waits until the title input is visible on the page.
     * @return Current instance of CreateEditNewsPage.
     */
    @Override
    @Step("Wait until Create/Edit News page is opened")
    public CreateEditNewsPage waitUntilOpened() {
        waitUntilVisible(titleInput);
        return this;
    }

    /**
     * Safely checks if the page is opened, catching browser-related exceptions.
     * @return true if opened, false if browser is unreachable.
     */
    @Step("Safely check if Create/Edit News page is opened (catching browser exceptions)")
    public boolean isPageOpenedSafe() {
        try {
            return isVisible(titleInput);
        } catch (UnreachableBrowserException e) {
            return false;
        }
    }

    /**
     * Checks if the page header is visible (useful after returning from preview).
     * @return true if header is visible.
     */
    @Step("Check if page header is visible after clicking Back to creating/editing in NewsPreviewPage")
    public boolean isPageOpenedAfterPreviewClickBack() {
        return isVisible(pageTitleHeader);
    }

    /**
     * Enters the provided text into the title field after clearing it.
     * @param title The text to enter.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Enter news title: {title}")
    public CreateEditNewsPage enterTitle(String title) {
        waitUntilVisible(titleInput);
        clearField(titleInput);
        titleInput.sendKeys(title);
        return this;
    }

    /**
     * Retrieves all tag elements as a list of {@link TagItem} components.
     * @return List of TagItem objects.
     */
    @Step("Get all tag items on page")
    public List<TagItem> getTagItems() {
        return tagRootElements.stream().map(root -> new TagItem(driver, root)).collect(Collectors.toList());
    }

    /**
     * Finds a specific tag by its name.
     * @param tagName The name of the tag.
     * @return The found TagItem.
     * @throws NoSuchElementException if tag is not found.
     */
    private TagItem getTagByName(String tagName) {
        return getTagItems().stream()
                .filter(tag -> tag.getName().equalsIgnoreCase(tagName))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Tag not found: " + tagName)
                );
    }

    /**
     * Clicks on a tag with the specified name.
     * @param tagName Name of the tag to click.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Click tag by name: {tagName}")
    public CreateEditNewsPage clickTagByName(String tagName) {
        getTagByName(tagName).click();
        return this;
    }

    /**
     * Selects multiple tags by their names.
     * @param tagNames List of tag names to select.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Select multiple tags: {tagNames}")
    public CreateEditNewsPage selectTags(List<String> tagNames) {
        waitUntilVisible(tagRootElements);
        tagNames.forEach(tagName -> {
            TagItem tag = getTagByName(tagName);

            if (!tag.isSelected()) {
                tag.click();
            }
        });

        return this;
    }

    /**
     * Gets names of all currently selected tags.
     * @return List of selected tag names.
     */
    @Step("Get list of selected tags")
    public List<String> getSelectedTags() {
        return getTagItems().stream()
                .filter(TagItem::isSelected)
                .map(TagItem::getName)
                .toList();
    }

    /**
     * Gets names of all tags available on the page.
     * @return List of all tag names.
     */
    @Step("Get list of all tags")
    public List<String> getAllTags() {
        return getTagItems().stream().map(TagItem::getName).toList();
    }

    /**
     * Deselects a tag if it is currently selected.
     * @param tagName Name of the tag to remove.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Remove tag: {tagName}")
    public CreateEditNewsPage removeTag(String tagName) {
        TagItem tag = getTagByName(tagName);
        if (tag.isSelected()) {
            tag.click();
        }
        return this;
    }

    /**
     * Verifies if any tags are visible on the page.
     * @return true if tags are visible.
     */
    @Step("Check if tags are visible on page")
    public boolean areTagsVisible() {
        return areVisible(tagRootElements);
    }

    /**
     * Clears all currently selected tags.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Clear all selected tags")
    public CreateEditNewsPage clearAllSelectedTags() {
        getSelectedTags().forEach(this::removeTag);
        return this;
    }

    /**
     * Enters the news source URL.
     * @param url The source link.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Enter news source URL: {url}")
    public CreateEditNewsPage enterSource(String url) {
        waitUntilVisible(sourceInput);
        clearSourceField();
        sourceInput.sendKeys(url);
        return this;
    }

    /**
     * Returns the {@link ImageComponent} for image upload interactions.
     * @return Instance of ImageComponent.
     */
    @Step("Get Image component")
    public ImageComponent getImageComponent() {
        return new ImageComponent(driver, imageRoot);
    }

    /**
     * Returns the {@link ContentComponent} for news content interactions.
     * @return Instance of ContentComponent.
     */
    @Step("Get Content component")
    public ContentComponent getContentComponent() {
        return new ContentComponent(driver, contentRoot);
    }

    /**
     * Checks if the title field has the "ng-invalid" class (red border/highlight).
     * @return true if the title state is invalid.
     */
    @Step("Check if title field is highlighted in red because it's empty")
    public boolean isTitleInvalid() {
        String classAttribute = titleInput.getAttribute("class");
        return classAttribute != null && classAttribute.contains("ng-invalid");
    }

    /**
     * Retrieves the current value of the title input field.
     * @return Current title text.
     */
    @Step("Get title text")
    public String getTitleValue() {
        return titleInput.getAttribute("value");
    }

    /**
     * Checks if the source input field is visible.
     * @return true if visible.
     */
    @Step("Check if source input is visible")
    public boolean isSourceVisible() {
        return isVisible(sourceInput);
    }

    /**
     * Retrieves the validation message text for the source field.
     * @return Trimmed message text.
     */
    @Step("Get source validation message text")
    public String getSourceMessage() {
        return sourceMessage.getText().trim();
    }

    /**
     * Checks if the Cancel button is visible.
     * @return true if visible.
     */
    @Step("Check if Cancel button is visible")
    public boolean isCancelButtonVisible() {
        return isVisible(cancelBtn);
    }

    /**
     * Clicks the Cancel button.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Click Cancel button")
    public CreateEditNewsPage clickCancel() {
        cancelBtn.click();
        return this;
    }

    /**
     * Checks if the Preview button is visible.
     * @return true if visible.
     */
    @Step("Check if Preview button is visible")
    public boolean isPreviewButtonVisible() {
        return isVisible(previewBtn);
    }

    /**
     * Clicks the Preview button and navigates to the NewsPreviewPage.
     * @return A new instance of NewsPreviewPage.
     */
    @Step("Click Preview button")
    public NewsPreviewPage clickPreview() {
        previewBtn.click();
        return new NewsPreviewPage(driver);
    }

    /**
     * Gets the character counter text (e.g., "5/170").
     * @return Counter string.
     */
    @Step("Get title character counter text (shows number of characters in title)")
    public String getTitleCounterText() {
        return titleCharacterCounter.getText();
    }

    /**
     * Gets the current length of the text in the title field.
     * @return Number of characters in the title.
     */
    @Step("Get length of title text")
    public int getTitleLength() {
        return getTitleValue().length();
    }

    /**
     * Returns the {@link CancelModalComponent} which appears after clicking Cancel.
     * @return Instance of CancelModalComponent.
     */
    @Step("Get Cancel modal component")
    public CancelModalComponent getCancelModal() {
        waitUntilVisible(cancelModalContainer);
        return new CancelModalComponent(driver, cancelModalContainer);
    }

    /**
     * Checks if the Cancel confirmation modal is currently displayed.
     * @return true if visible.
     */
    @Step("Check if Cancel modal is displayed")
    public boolean isCancelModalDisplayed() {
        try {
            waitUntilVisible(cancelModalContainer);
            return cancelModalContainer.isDisplayed();
        } catch (NoSuchElementException | TimeoutException | UnreachableBrowserException e) {
            return false;
        }
    }

    /**
     * Verifies if the author name is visible.
     * @return true if visible.
     */
    @Step("Check if author is visible")
    public boolean isAuthorVisible() {
        return isVisible(authorName);
    }

    /**
     * Retrieves the author's name from the page.
     * @return Trimmed author name.
     */
    @Step("Get author name")
    public String getAuthor() {
        return authorName.getText().trim();
    }

    /**
     * Verifies if the post date is visible.
     * @return true if visible.
     */
    @Step("Check if post date is visible")
    public boolean isPostDateVisible() {
        return isVisible(postDate);
    }

    /**
     * Retrieves the post date text.
     * @return Trimmed date string.
     */
    @Step("Get post date")
    public String getPostDate() {
        return postDate.getText().trim();
    }

    /**
     * Retrieves the current value from the source input field.
     * @return Trimmed source text.
     */
    @Step("Get news source text")
    public String getSource() {
        return Objects.requireNonNull(sourceInput.getAttribute("value")).trim();
    }

    /**
     * Retrieves the placeholder text of the source input field.
     * @return Trimmed placeholder text.
     */
    @Step("Get placeholder text of source input")
    public String getSourcePlaceholder() {
        return sourceInput.getAttribute("placeholder").trim();
    }

    /**
     * Clears the text from the source input field.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Clear source text")
    public CreateEditNewsPage clearSourceField() {
        clearField(sourceInput);
        return this;
    }

    /**
     * Refreshes the page and waits for it to be ready.
     * @return Current instance of CreateEditNewsPage.
     */
    public CreateEditNewsPage reload() {
        driver.navigate().refresh();
        wait.until(driver -> isPageOpenedSafe());
        return this;
    }

    /**
     * Retrieves the text label of the Cancel button.
     * @return Trimmed button text.
     */
    @Step("Get Cancel button text")
    public String getCancelButtonText() {
        waitUntilVisible(cancelBtn);
        return cancelBtn.getText().trim();
    }

    /**
     * Retrieves the text label of the Preview button.
     * @return Trimmed button text.
     */
    @Step("Get Preview button text")
    public String getPreviewButtonText() {
        waitUntilVisible(previewBtn);
        return previewBtn.getText().trim();
    }

    /**
     * Adds text to the end of the existing title.
     * @param additionalText Text to append.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Append text to title: {additionalText}")
    public CreateEditNewsPage appendTitle(String additionalText) {
        getTitleInput().sendKeys(additionalText);
        return this;
    }

    /**
     * Adds text to the beginning of the existing title.
     * @param textToAdd Text to prepend.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Prepend text to title: {textToAdd}")
    public CreateEditNewsPage prependTitle(String textToAdd) {
        WebElement title = getTitleInput();
        String currentValue = title.getAttribute("value");
        String newValue = textToAdd + (currentValue != null ? currentValue : "");
        clearField(title);
        title.sendKeys(newValue);
        return this;
    }

    /**
     * Base method to remove characters from the title.
     * @param count Number of characters to remove.
     * @param fromStart if true - removes from the beginning, else - from the end.
     * @return Current instance of CreateEditNewsPage.
     */
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

    /**
     * Removes a specific number of characters from the end of the title.
     * @param count Number of characters to remove.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Remove last {count} characters from title")
    public CreateEditNewsPage removeLastTitleChars(int count) {
        return removeTitleChars(count, false);
    }

    /**
     * Removes a specific number of characters from the start of the title.
     * @param count Number of characters to remove.
     * @return Current instance of CreateEditNewsPage.
     */
    @Step("Remove first {count} characters from title")
    public CreateEditNewsPage removeFirstTitleChars(int count) {
        return removeTitleChars(count, true);
    }
}