package org.greencity.ui.pages.CreateEditNews;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CreateNewsPage extends CreateEditNewsPage {

    @FindBy(xpath = "//button[@type='submit' and contains(@class,'primary-global-button')]")
    private WebElement publishBtn;

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    @Step("Open Create News page")
    public CreateNewsPage open() {
        super.open();
        return this;
    }

    @Step("Check if Publish button is visible")
    public boolean isPublishButtonVisible() {
        return isVisible(publishBtn);
    }

    @Step("Check if Publish button is enabled")
    public boolean isPublishButtonEnabled() {
        waitUntilVisible(publishBtn);
        return publishBtn.isEnabled();
    }

    @Step("Click Publish button")
    public void clickPublish() {
        waitUntilClickable(publishBtn);
        publishBtn.click();
    }

    @Step("Get Publish button text")
    public String getPublishButtonText() {
        waitUntilVisible(publishBtn);
        return publishBtn.getText().trim();
    }

    @Override
    @Step("Reload CreateNewsPage")
    public CreateNewsPage reload() {
        super.reload();
        return this;
    }

    @Step("Fill out and create news with title: {title}, tags: {tags}, source: {source}, content: [hidden], image: {imagePath}")
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
}
