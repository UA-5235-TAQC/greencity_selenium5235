package org.greencity.ui.components.CreateEditNewsPage;

import io.qameta.allure.Step;
import org.greencity.ui.components.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

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

    @FindBy(css = "div.image-block p.warning")
    private WebElement imageMessage;

    @FindBy(css = "image-cropper.cropper")
    private WebElement cropper;

    @FindBy(css = "div.cropper-buttons button.secondary-global-button")
    private WebElement cancelCropperBtn;

    @FindBy(css = "div.cropper-buttons button.primary-global-button")
    private WebElement submitCropperBtn;

    public ImageComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Check if Cancel button in image cropper is visible")
    public boolean isCancelCropperButtonVisible() {
        return isVisible(cancelCropperBtn);
    }

    @Step("Check if Submit button in image cropper is visible")
    public boolean isSubmitCropperButtonVisible() {
        return isVisible(submitCropperBtn);
    }

    @Step("Get image upload error message text")
    public String getImageError() {
        return imageMessage.getText().trim();
    }

    @Step("Upload image from file path: {filePath}")
    public ImageComponent uploadImage(String filePath) {
        uploadInput.sendKeys(filePath);
        return this;
    }

    @Step("Get image input field value when no image is uploaded")
    public String getImageInputInfo() {
        return uploadInput.getAttribute("value");
    }

    @Step("Check if image upload field (drop zone) is visible when no image is uploaded")
    public boolean isImageFieldVisible() {
        return isVisible(dropZone);
    }

    @Step("Check if uploaded image is visible")
    public boolean isImageVisible() {
        return isVisible(uploadedImage);
    }

    @Step("Check if changed image is visible")
    public boolean isPreviewImageVisible() {
        return isVisible(previewImage);
    }

    private String getImageSrc(WebElement image) {
        waitUntilVisible(image);
        return image.getAttribute("src");
    }

    @Step("Get source URL of the uploaded image")
    public String getUploadedImageSrc() {
        return getImageSrc(uploadedImage);
    }

    @Step("Get source URL of the changed image")
    public String getPreviewImageSrc() {
        return getImageSrc(previewImage);
    }

    private boolean hasImageSrcPrefix(WebElement image, String prefix) {
        String src = getImageSrc(image);
        return src != null && src.startsWith(prefix);
    }

    @Step("Check if placeholder is displayed in image field")
    public boolean isPlaceholderImagePresent() {
        return hasImageSrcPrefix(uploadedImage, "data:image");
    }

    @Step("Check if uploaded image (blob) is displayed in image field")
    public boolean isUploadedImagePresent() {
        return hasImageSrcPrefix(uploadedImage, "blob:");
    }

    @Step("Check if the changed image is displayed in image field")
    public boolean isPreviewPlaceholderImagePresent() {
        return hasImageSrcPrefix(previewImage, "data:image");
    }

    @Step("Click Submit image")
    public ImageComponent submitCrop() {
        submitCropperBtn.click();
        return this;
    }

    @Step("Click Cancel image")
    public ImageComponent cancelCrop() {
        cancelCropperBtn.click();
        return this;
    }

    @Step("Get text displayed in image drop zone (without browse link)")
    public String getDropZoneText() {
        String fullText = dropZone.getText();
        String browseText = getBrowseText();
        return fullText.replace(browseText, "").trim();
    }

    @Step("Get 'Browse' link text in image drop zone")
    public String getBrowseText() {
        return browseLink.getText().trim();
    }

    @Step("Get Cancel button text in image cropper")
    public String getCancelCropperText() {
        return cancelCropperBtn.getText().trim();
    }

    @Step("Get Submit button text in image cropper")
    public String getSubmitCropperText() {
        return submitCropperBtn.getText().trim();
    }

    @Step("Change image by uploading new file and submitting crop")
    public ImageComponent changeImage(String filePath) {
        cancelCrop();
        uploadImage(filePath);
        waitUntilVisible(cropper);
        submitCrop();
        waitUntilVisible(previewImage);
        return this;
    }

    @Step("Check if image error message is displayed")
    public boolean isImageErrorMsg() {
        return Objects.requireNonNull(imageMessage.getAttribute("class"))
                .contains("warning-color");
    }

    @Step("Check if image preview is displayed")
    public boolean isPreviewImage() {
        return previewImage == null;
    }
}
