package org.greencity.ui.pages;

import org.greencity.ui.components.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CreateNewsPage extends BasePage {

    private final String tagButtonXpathTemplate = "//button[contains(@class, 'tag-button')]//span[contains(@class, 'text') and normalize-space()='%s']";

    @FindBy(css = "div.main-content")
    private WebElement root;

    @FindBy(css = "textarea[formcontrolname='title']")
    private WebElement titleInput;

    @FindBy(css = "div.tags-box button.tag-button")
    private List<WebElement> tagRootElements;

    @FindBy(css = "input[formcontrolname='source']")
    private WebElement sourceInput;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement imageUploadInput;

    @FindBy(css = "image-cropper.cropper")
    private WebElement imageCropper;

    @FindBy(css = "div.image-block p.warning")
    private WebElement imageErrorMessage;

    @FindBy(css = "div.source-block span")
    private WebElement sourceMessage;

    @FindBy(css = "div.source-block span.warning")
    private WebElement sourceErrorMessage;

    @FindBy(css = "div.textarea-wrapper div.title-wrapper p.field-info.warning")
    private WebElement contentErrorMessage;

    @FindBy(css = "div.textarea-wrapper p.quill-counter.warning")
    private WebElement contentCounterMessage;

    @FindBy(css = ".ql-editor")
    private WebElement contentEditor;

    @FindBy(css = ".submit-buttons button.primary-global-button")
    private WebElement publishBtn;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelBtn;

    @FindBy(xpath = "//button[contains(text(),'Preview')]")
    private WebElement previewBtn;

    @FindBy(css = ".title-block div span.field-info")
    private WebElement titleCharacterCounter;

    @FindBy(css = "div.date p:nth-of-type(1) span:last-child")
    private WebElement postDate;

    @FindBy(css = "div.date p:nth-of-type(2) span:last-child")
    private WebElement authorName;

    @FindBy(css = "div.cropper-buttons button.secondary-global-button")
    private WebElement cancelCropperBtn;

    @FindBy(css = "div.cropper-buttons button.primary-global-button")
    private WebElement submitCropperBtn;

    @FindBy(css = "div.textarea-wrapper")
    private WebElement contentRoot;

    @FindBy(css = "app-warning-pop-up .main-container")
    private WebElement cancelModalRoot;

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CreateNewsPage open() {
        driver.get(getBaseHost() + "#/greenCity/news/create-news");
        return new CreateNewsPage(driver);
    }

    @Override
    public boolean isPageOpened() {
        return isVisible(titleInput);
    }

    public CreateNewsPage enterTitle(String title) {
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

    public CreateNewsPage clickTagByName(String tagName) {
        getTagByName(tagName).click();
        return this;
    }

    public CreateNewsPage selectTags(List<String> tags) {
        tags.forEach(this::clickTagByName);
        return this;
    }

    public CreateNewsPage removeTag(String tagName) {
        TagItem tag = getTagByName(tagName);
        if (tag.isSelected()) {
            tag.click();
        }
        return this;
    }

    public boolean areTagsVisible() {
        return isVisible(tagRootElements);
    }

    public CreateNewsPage enterSource(String url) {
        sourceInput.clear();
        sourceInput.sendKeys(url);
        return this;
    }

    public CreateNewsPage uploadImage(String filePath) {
        imageUploadInput.sendKeys(filePath);
        return this;
    }

    public void cropImage() {
        imageCropper.click();
    }

    public boolean isImageUploadInputVisible() {
        return isVisible(imageUploadInput) || isVisible(imageUploadInput.findElement(By.xpath("..")));
    }

    public ContentComponent getContentComponent() {
        return new ContentComponent(driver, contentRoot);
    }

    public CancelModalComponent getCancelModalComponent() {
        return new CancelModalComponent(driver, cancelModalRoot);
    }

    public CreateNewsPage createNews(String title, List<String> tags, String source, String content, String imagePath) {
        if (title != null) enterTitle(title);
        if (tags != null) selectTags(tags);
        if (source != null) enterSource(source);
        if (content != null) {
            getContentComponent().enterContent(content);
        }
        if (imagePath != null) {
            uploadImage(imagePath);
            cropImage();
        }
        return this;
    }

    public String getUploadedImageInfo() {
        return imageUploadInput.getAttribute("value");
    }

    public boolean isCancelCropperButtonVisible() {
        return isVisible(cancelCropperBtn);
    }

    public boolean isSubmitCropperButtonVisible() {
        return isVisible(submitCropperBtn);
    }

    public String getImageError() {
        return imageErrorMessage.getText();
    }

    public boolean isSourceVisible() {
        return isVisible(sourceInput);
    }

    public String getSourceMessage() {
        return sourceMessage.getText().trim();
    }

    public boolean isPublishButtonVisible() {
        return isVisible(publishBtn);
    }

    public boolean isPublishButtonEnabled() {
        return publishBtn.isEnabled();
    }

    public CreateNewsPage clickPublish() {
        publishBtn.click();
        return this;
    }

    public boolean isCancelButtonVisible() {
        return isVisible(cancelBtn);
    }

    public CreateNewsPage clickCancel() {
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

    public List<String> getAllTags() {
        return getTagItems().stream().map(TagItem::getName).collect(Collectors.toList());
    }

    public String getTitleCounterText() {
        return titleCharacterCounter.getText();
    }

    public boolean isTitleCounterWarningDisplayed() {
        String classAttribute = titleInput.getAttribute("class");
        return classAttribute != null && classAttribute.contains("ng-invalid");
    }

    public String getTitleValue() {
        return titleInput.getAttribute("value");
    }

    public int getTitleLength() {
        return getTitleValue().length();
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

    public long getUploadedImageSize() {
        String filePath = imageUploadInput.getAttribute("value");
        File file = new File(filePath);
        return file.length();
    }
}
