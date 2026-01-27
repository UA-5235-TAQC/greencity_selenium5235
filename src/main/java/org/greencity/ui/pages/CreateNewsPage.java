package org.greencity.ui.pages;

import org.greencity.ui.components.CancelModalComponent;
import org.greencity.ui.components.ContentComponent;
import org.greencity.ui.components.TagItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class CreateNewsPage extends BasePage {

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
        driver.get(valueProvider.get("base.ui.greencity.createNews.url"));
        return this;
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

    public CreateNewsPage clickTagByName(String tagName) {
        String xpathExpression = String.format(
                "//button[contains(@class, 'tag-button')]//span[contains(@class, 'text') and normalize-space()='%s']",
                tagName
        );

        WebElement tagButton = driver.findElement(By.xpath(xpathExpression));
        wait.until(ExpectedConditions.elementToBeClickable(tagButton)).click();

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

    public boolean areTagsVisible() {
        for (WebElement tag : tagRootElements) {
            if (!isVisible(tag)) {
                return false;
            }
        }
        return true;
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

    public boolean isTitleInvalid() {
        String classAttribute = titleInput.getAttribute("class");
        return classAttribute != null && classAttribute.contains("ng-invalid");
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

    // methods for testing title fields
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
