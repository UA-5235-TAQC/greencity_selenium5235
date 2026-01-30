package org.greencity.ui.components.CreateEditNewsPage;

import org.greencity.ui.components.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ImageComponent extends BaseComponent {

    @FindBy(css = "input[type='file']")
    private WebElement uploadInput;

    @FindBy(css = "div.dropzone")
    private WebElement dropZone;

    @FindBy(css = "div.centered")
    private WebElement dropZoneText;

    @FindBy(css = "div.centered label span")
    private WebElement browseLink;

    @FindBy(css = "img.ngx-ic-source-image")
    private WebElement uploadedImage;

    @FindBy(css = "div.image-preview img")
    private WebElement previewImage;

    @FindBy(css = "image-cropper.cropper")
    private WebElement cropper;

    @FindBy(css = "p.warning")
    private WebElement errorMessage;

    @FindBy(css = "div.cropper-buttons button.secondary-global-button")
    private WebElement cancelCropperBtn;

    @FindBy(css = "div.cropper-buttons button.primary-global-button")
    private WebElement submitCropperBtn;

    public ImageComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public boolean isCancelCropperButtonVisible() {
        return isVisible(cancelCropperBtn);
    }

    public boolean isSubmitCropperButtonVisible() {
        return isVisible(submitCropperBtn);
    }

    public String getImageError() {
        return errorMessage.getText().trim();
    }

    public ImageComponent uploadImage(String filePath) {
        uploadInput.sendKeys(filePath);
        return this;
    }

    public String getImageInputInfo() {
        return uploadInput.getAttribute("value");
    }

    public boolean isImageFieldVisible() {
        return isVisible(dropZone);
    }

    public boolean isImageVisible() {
        return isVisible(uploadedImage);
    }

    public String getUploadedImageSrc() {
        waitUntilVisible(uploadedImage);
        return uploadedImage.getAttribute("src");
    }

    public boolean isPreviewImageVisible() {
        return isVisible(previewImage);
    }

    public String getPreviewImageSrc() {
        waitUntilVisible(previewImage);
        return previewImage.getAttribute("src");
    }

    private boolean hasImageSrcPrefix(String prefix) {
        String src = getUploadedImageSrc();
        return src != null && src.startsWith(prefix);
    }

    public boolean isPlaceholderImagePresent() {
        return hasImageSrcPrefix("data:image");
    }

    public boolean isUploadedImagePresent() {
        return hasImageSrcPrefix("blob:");
    }

    private boolean hasPreviewImageSrcPrefix(String prefix) {
        String src = getPreviewImageSrc();
        return src != null && src.startsWith(prefix);
    }

    public boolean isPreviewPlaceholderImagePresent() {
        return hasPreviewImageSrcPrefix("data:image");
    }

    public boolean isPreviewUploadedImagePresent() {
        return hasPreviewImageSrcPrefix("blob:");
    }

    public ImageComponent submitCrop() {
        submitCropperBtn.click();
        return this;
    }

    public ImageComponent cancelCrop() {
        cancelCropperBtn.click();
        return this;
    }

    public String getDropZoneText() {
        String fullText = dropZone.getText();
        String browseText = getBrowseText();
        return fullText.replace(browseText, "").trim();
    }

    public String getBrowseText() {
        return browseLink.getText().trim();
    }

    public String getCancelCropperText() {
        return cancelCropperBtn.getText().trim();
    }

    public String getSubmitCropperText() {
        return submitCropperBtn.getText().trim();
    }

    public ImageComponent changeImage(String filePath) {
        cancelCrop();
        uploadImage(filePath);
        waitUntilVisible(cropper);
        submitCrop();
        waitUntilVisible(previewImage);
        return this;
    }
}
