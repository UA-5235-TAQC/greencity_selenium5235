package org.greencity.ui.pages;

import org.greencity.ui.components.NewsPreviewComponent;
import org.greencity.ui.components.TagItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateNewsPage extends BasePage {

    @FindBy(css = "div.main-content")
    private WebElement root;

    @FindBy(css = "textarea[formcontrolname='title']")
    private WebElement titleInput;

    @FindBy(css = "button.tag-button a.global-tag")
    private List<WebElement> tagRootElements;

    @FindBy(css = "input[formcontrolname='source']")
    private WebElement sourceInput;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement imageUploadInput;

    @FindBy(css = "image-cropper.cropper")
    private WebElement imageCropper;

    @FindBy(css = "div.image-block p.warning")
    private WebElement imageErrorMessage;

    @FindBy(css = "div.source-block span.warning")
    private WebElement sourceErrorMessage;

    @FindBy(css = "div.textarea-wrapper div.title-wrapper p.field-info.warning")
    private WebElement contentErrorMessage;

    @FindBy(css = "div.textarea-wrapper p.quill-counter.warning")
    private WebElement contentCounterMessage;

    @FindBy(css = ".ql-editor")
    private WebElement contentEditor;

    @FindBy(xpath = "//button[contains(text(),'Publish')]")
    private WebElement publishBtn;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelBtn;

    @FindBy(xpath = "//button[contains(text(),'Preview')]")
    private WebElement previewBtn;

    private WebElement previewModalRoot;

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CreateNewsPage open() {
        driver.get("#/greenCity/news/create-news");
        return new CreateNewsPage(driver);
    }

    @Override
    public boolean isPageOpened() {
        return isVisible(titleInput);
    }

    public WebElement getPreviewModalRoot() {
        previewModalRoot = driver.findElement(By.cssSelector("div[role='dialog']"));
        return previewModalRoot;
    }

    public CreateNewsPage enterTitle(String title) {
        titleInput.clear();
        titleInput.sendKeys(title);
        return this;
    }

    private List<TagItem> getTagItems() {
        return tagRootElements.stream().map(root -> new TagItem(driver, root)).collect(Collectors.toList());
    }

    public CreateNewsPage selectTags(List<String> tags) {
        List<TagItem> tagItems = getTagItems();
        for (String tagName : tags) {
            for (TagItem tag : tagItems) {
                if (tag.getName().equalsIgnoreCase(tagName) && !tag.isSelected()) {
                    tag.click();
                    break;
                }
            }
        }
        return this;
    }

    public CreateNewsPage removeTag(String tagName) {
        for (TagItem tag : getTagItems()) {
            if (tag.getName().equalsIgnoreCase(tagName) && tag.isSelected()) {
                tag.click();
                break;
            }
        }
        return this;
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

    public CreateNewsPage enterContent(String text) {
        contentEditor.clear();
        contentEditor.sendKeys(text);
        return this;
    }

    public CreateNewsPage createNews(String title, List<String> tags, String source, String content, String imagePath) {
        if (title != null) enterTitle(title);
        if (tags != null) selectTags(tags);
        if (source != null) enterSource(source);
        if (content != null) enterContent(content);
        if (imagePath != null) {
            uploadImage(imagePath);
            cropImage();
        }
        return this;
    }

    public String getUploadedImageInfo() {
        return imageUploadInput.getAttribute("value");
    }

    public String getContent() {
        return contentEditor.getText();
    }

    public boolean isTitleInvalid() {
        String classAttribute = titleInput.getAttribute("class");
        return classAttribute != null && classAttribute.contains("ng-invalid");
    }

    public String getImageError() {
        return imageErrorMessage.getText();
    }

    public String getSourceError() {
        return sourceErrorMessage.getText();
    }

    public String getContentError() {
        return contentErrorMessage.getText();
    }

    public String getContentCounter() {
        return contentCounterMessage.getText();
    }

    public boolean isPublishButtonEnabled() {
        return publishBtn.isEnabled();
    }

    public CreateNewsPage clickPublish() {
        publishBtn.click();
        return this;
    }

    public CreateNewsPage clickCancel() {
        cancelBtn.click();
        return this;
    }

    public NewsPreviewComponent clickPreview() {
        previewBtn.click();
        return new NewsPreviewComponent(driver, getPreviewModalRoot());
    }

    public List<String> getAllTags() {
        return getTagItems().stream().map(TagItem::getName).collect(Collectors.toList());
    }
}
