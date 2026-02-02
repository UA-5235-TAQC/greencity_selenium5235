package org.greencity.ui.pages.CreateEditNews;

import org.greencity.ui.components.CreateEditNewsPage.CancelModalComponent;
import org.greencity.ui.components.CreateEditNewsPage.ContentComponent;
import org.greencity.ui.components.CreateEditNewsPage.ImageComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.pages.BasePage;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.util.List;
import java.util.stream.Collectors;

public class CreateEditNewsPage extends BasePage {

    @FindBy(css = "div.main-content")
    private WebElement root;
    @FindBy(css = "textarea[formcontrolname='title']")
    private WebElement titleInput;
    @FindBy(css = "div.title h2.title-header")
    protected WebElement pageTitleHeader;
    @FindBy(css = "div.tags-box button.tag-button")
    private List<WebElement> tagRootElements;
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
    public boolean isPageOpened() {
        return isVisible(titleInput);
    }

    public boolean isPageOpenedSafe() {
        try {
            return isVisible(titleInput);
        } catch (UnreachableBrowserException e) {
            return false;
        }
    }

    public boolean isPageOpenedAfterPreviewClickBack() {
        return isVisible(pageTitleHeader);
    }

    public CreateEditNewsPage enterTitle(String title) {
        titleInput.clear();
        titleInput.sendKeys(title);
        return this;
    }

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

    public CreateEditNewsPage clickTagByName(String tagName) {
        getTagByName(tagName).click();
        return this;
    }

    public CreateEditNewsPage selectTags(List<String> tagNames) {
        tagNames.forEach(tagName -> {
            TagItem tag = getTagByName(tagName);

            if(!tag.isSelected()) {
                tag.click();
            }
        });

        return this;
    }

    public List<String> getSelectedTags() {
        return getTagItems().stream()
                .filter(TagItem::isSelected)
                .map(TagItem::getName)
                .toList();
    }

    public List<String> getAllTags() {
        return getTagItems().stream().map(TagItem::getName).toList();
    }

    public CreateEditNewsPage removeTag(String tagName) {
        TagItem tag = getTagByName(tagName);
        if (tag.isSelected()) {
            tag.click();
        }
        return this;
    }

    public boolean areTagsVisible() {
        return areVisible(tagRootElements);
    }

    public CreateEditNewsPage clearAllSelectedTags() {
        getSelectedTags().forEach(this::removeTag);
        return this;
    }

    public CreateEditNewsPage enterSource(String url) {
        sourceInput.clear();
        sourceInput.sendKeys(url);
        return this;
    }

    public ImageComponent getImageComponent() {
        return new ImageComponent(driver, imageRoot);
    }

    public ContentComponent getContentComponent() {
        return new ContentComponent(driver, contentRoot);
    }

    public boolean isTitleInvalid() {
        String classAttribute = titleInput.getAttribute("class");
        return classAttribute != null && classAttribute.contains("ng-invalid");
    }

    public String getTitleValue() {
        return titleInput.getAttribute("value");
    }

    public WebElement getTitleInput() {
        return titleInput;
    }

    public boolean isSourceVisible() {
        return isVisible(sourceInput);
    }

    public WebElement getSourceInput() {
        return sourceInput;
    }

    public String getSourceMessage() {
        return sourceMessage.getText().trim();
    }

    public boolean isCancelButtonVisible() {
        return isVisible(cancelBtn);
    }

    public CreateEditNewsPage clickCancel() {
        cancelBtn.click();
        return this;
    }

    public boolean isPreviewButtonVisible() {
        return isVisible(previewBtn);
    }

    public NewsPreviewPage clickPreview() {
        previewBtn.click();
        return new NewsPreviewPage(driver);
    }

    public String getTitleCounterText() {
        return titleCharacterCounter.getText();
    }

    public boolean isTitleCounterWarningDisplayed() {
        String classAttribute = titleInput.getAttribute("class");
        return classAttribute != null && classAttribute.contains("ng-invalid");
    }

    public int getTitleLength() {
        return getTitleValue().length();
    }

    public CancelModalComponent getCancelModal() {
        wait.until(ExpectedConditions.visibilityOf(cancelModalContainer));
        return new CancelModalComponent(driver, cancelModalContainer);
    }

    public boolean isCancelModalDisplayed() {
        try {
            waitUntilVisible(cancelModalContainer);
            return cancelModalContainer.isDisplayed();
        } catch (NoSuchElementException | TimeoutException | UnreachableBrowserException e) {
            return false;
        }
    }

    public boolean isAuthorVisible() {
        return isVisible(authorName);
    }

    public String getAuthor() {
        return authorName.getText().trim();
    }

    public boolean isPostDateVisible() {
        return isVisible(postDate);
    }

    public String getPostDate() {
        return postDate.getText().trim();
    }

    public String getSource() {
        return sourceInput.getAttribute("value").trim();
    }

    public String getSourcePlaceholder() {
        return sourceInput.getAttribute("placeholder").trim();
    }

    public CreateEditNewsPage clearSourceField() {
        getSourceInput().clear();
        return this;
    }

    public CreateEditNewsPage reload() {
        driver.navigate().refresh();
        wait.until(driver -> isPageOpenedSafe());
        return this;
    }

    public String getCancelButtonText() {
        waitUntilVisible(cancelBtn);
        return cancelBtn.getText().trim();
    }

    public String getPreviewButtonText() {
        waitUntilVisible(previewBtn);
        return previewBtn.getText().trim();
    }

    public CreateEditNewsPage appendTitle(String additionalText) {
        getTitleInput().sendKeys(additionalText);
        return this;
    }

    public CreateEditNewsPage prependTitle(String textToAdd) {
        WebElement title = getTitleInput();
        String currentValue = title.getAttribute("value");
        String newValue = textToAdd + (currentValue != null ? currentValue : "");
        title.clear();
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
            title.clear();
            title.sendKeys(newValue);
        }
        return this;
    }

    public CreateEditNewsPage removeLastTitleChars(int count) {
        return removeTitleChars(count, false);
    }

    public CreateEditNewsPage removeFirstTitleChars(int count) {
        return removeTitleChars(count, true);
    }
}
