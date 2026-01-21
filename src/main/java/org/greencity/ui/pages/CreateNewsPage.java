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

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    public void enterTitle(String title) {
        titleInput.clear();
        titleInput.sendKeys(title);
    }

    private List<TagItem> getTagItems() {
        return tagRootElements.stream()
                .map(root -> new TagItem(driver, root))
                .collect(Collectors.toList());
    }

    public void selectTags(List<String> tags) {
        List<TagItem> tagItems = getTagItems();
        for (String tagName : tags) {
            for (TagItem tag : tagItems) {
                if (tag.getName().equalsIgnoreCase(tagName)) {
                    tag.select();
                    break;
                }
            }
        }
    }

    public void removeTag(String tagName) {
        for (TagItem tag : getTagItems()) {
            if (tag.getName().equalsIgnoreCase(tagName)) {
                tag.remove();
                break;
            }
        }
    }

    public void enterSource(String url) {
        sourceInput.clear();
        sourceInput.sendKeys(url);
    }

    public void uploadImage(String filePath) {
        imageUploadInput.sendKeys(filePath);
    }

    public void cropImage() {
        imageCropper.click();
    }

    public void enterContent(String text) {
        contentEditor.clear();
        contentEditor.sendKeys(text);
    }

    public void createNews(String title, List<String> tags, String source, String content, String imagePath) {
        if (title != null) enterTitle(title);
        if (tags != null) selectTags(tags);
        if (source != null) enterSource(source);
        if (content != null) enterContent(content);
        if (imagePath != null) {
            uploadImage(imagePath);
            cropImage();
        }
    }

    public String getUploadedImageInfo() {
        return imageUploadInput.getAttribute("value");
    }

    public String getContent() {
        return contentEditor.getText();
    }

    public boolean isTitleInvalid() {
        return Objects.requireNonNull(titleInput.getAttribute("class")).contains("ng-invalid");
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

    public void clickPublish() {
        publishBtn.click();
    }

    public void clickCancel() {
        cancelBtn.click();
    }

    public NewsPreviewComponent clickPreview() {
        previewBtn.click();
        return new NewsPreviewComponent(driver);
    }

    public List<String> getAllTags() {
        return getTagItems().stream().map(TagItem::getName).collect(Collectors.toList());
    }
}
