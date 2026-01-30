package org.greencity.ui.pages.CreateEditNews;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;

public class CreateNewsPage extends CreateEditNewsPage {

    @FindBy(xpath = "//button[@type='submit' and contains(@class,'primary-global-button')]")
    private WebElement publishBtn;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement imageUploadInput;

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CreateNewsPage open() {
        super.open();
        return this;
    }

    public CreateEditNewsPage createNews(String title, List<String> tags, String source, String content, String imagePath) {
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

    public CreateNewsPage reload() {
        driver.navigate().refresh();
        wait.until(driver -> isPageOpenedSafe());
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

    public CreateEditNewsPage uploadImage(String filePath) {
        imageUploadInput.sendKeys(filePath);
        return this;
    }

    public boolean isImageUploadInputVisible() {
        return isVisible(imageUploadInput.findElement(By.xpath("..")));
    }

    public String getUploadedImageInfo() {
        return imageUploadInput.getAttribute("value");
    }

    public File getImageFile() {
        String filePath = imageUploadInput.getAttribute("value");
        if (filePath == null || filePath.isBlank()) {
            return null;
        }
        File file = new File(filePath);
        return file.exists() ? file : null;
    }

    public long getUploadedImageSize() {
        File file = getImageFile();
        return file == null ? 0 : file.length();
    }
}
