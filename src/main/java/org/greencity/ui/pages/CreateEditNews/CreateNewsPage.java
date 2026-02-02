package org.greencity.ui.pages.CreateEditNews;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CreateNewsPage extends CreateEditNewsPage {

    @FindBy(xpath = "//button[@type='submit' and contains(@class,'primary-global-button')]")
    private WebElement publishBtn;

    @FindBy(css = "div.image-block p.warning-color")
    private WebElement imageErrorMessage;

    @FindBy(css = ".image-preview")
    private WebElement imgPreviewWrap;

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CreateNewsPage open() {
        super.open();
        return this;
    }

    @Override
    public CreateNewsPage waitUntilOpened() {
        return this;
    }

    public boolean isPublishButtonVisible() {
        return isVisible(publishBtn);
    }

    public boolean isPublishButtonEnabled() {
        return publishBtn.isEnabled();
    }

    public void clickPublish() {
        waitUntilClickable(publishBtn);
        publishBtn.click();
    }

    public String getPublishButtonText() {
        waitUntilVisible(publishBtn);
        return publishBtn.getText().trim();
    }

    @Override
    public CreateNewsPage reload() {
        super.reload();
        return this;
    }

    @Override
    public CreateNewsPage clearSourceField() {
        super.clearSourceField();
        return this;
    }

    @Override
    public CreateNewsPage enterSource(String url) {
        super.enterSource(url);
        return this;
    }

    public CreateNewsPage createNews(String title, List<String> tags, String source, String content, String imagePath) {
        if (title != null) enterTitle(title);
        if (tags != null) selectTags(tags);
        if (source != null) enterSource(source);
        if (content != null) {
            getContentComponent().enterContent(content);
        }

        if (imagePath != null) {
            getImageComponent().uploadImage(imagePath)
                            .submitCrop();
        }
        return this;
    }
    public boolean isImageErrorMsg() {
        return imageErrorMessage != null;
    }

    public boolean isPreviewImage() {
        return imgPreviewWrap == null;
    }
}
