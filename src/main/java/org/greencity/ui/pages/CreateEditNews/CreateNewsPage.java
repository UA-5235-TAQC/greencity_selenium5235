package org.greencity.ui.pages.CreateEditNews;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object representing the specific "Create News" page.
 * Inherits common field interactions from {@link CreateEditNewsPage}
 * and adds functionality for publishing news items.
 */
public class CreateNewsPage extends CreateEditNewsPage {

    @FindBy(xpath = "//button[@type='submit' and contains(@class,'primary-global-button')]")
    private WebElement publishBtn;

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Opens the Create News page by calling the parent open method.
     * @return Current instance of CreateNewsPage.
     */
    @Override
    @Step("Open Create News page")
    public CreateNewsPage open() {
        super.open();
        return this;
    }

    /**
     * Checks if the Publish button is visible on the page.
     * @return true if the Publish button is displayed.
     */
    @Step("Check if Publish button is visible")
    public boolean isPublishButtonVisible() {
        return isVisible(publishBtn);
    }

    /**
     * Checks if the Publish button is enabled (clickable).
     * Usually used to verify if all mandatory fields are filled correctly.
     * @return true if the Publish button is enabled.
     */
    @Step("Check if Publish button is enabled")
    public boolean isPublishButtonEnabled() {
        waitUntilVisible(publishBtn);
        return publishBtn.isEnabled();
    }

    /**
     * Waits until the Publish button is clickable and then performs a click action.
     */
    @Step("Click Publish button")
    public void clickPublish() {
        waitUntilClickable(publishBtn);
        publishBtn.click();
    }

    /**
     * Retrieves the text label of the Publish button.
     * @return Trimmed text of the Publish button.
     */
    @Step("Get Publish button text")
    public String getPublishButtonText() {
        waitUntilVisible(publishBtn);
        return publishBtn.getText().trim();
    }

    /**
     * Reloads the current page and waits for its initialization.
     * @return Current instance of CreateNewsPage.
     */
    @Override
    @Step("Reload CreateNewsPage")
    public CreateNewsPage reload() {
        super.reload();
        return this;
    }

    /**
     * Performs a full sequence of actions to fill out the news creation form.
     * Elements are only interacted with if the corresponding parameter is not null.
     * * @param title     Title of the news.
     * @param tags      List of tags to be selected.
     * @param source    Source URL or text.
     * @param content   Main content/text of the news.
     * @param imagePath System path to the image file to be uploaded.
     * @return Current instance of CreateNewsPage.
     */
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