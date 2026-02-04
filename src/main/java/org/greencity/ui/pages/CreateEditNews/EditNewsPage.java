package org.greencity.ui.pages.CreateEditNews;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EditNewsPage extends CreateEditNewsPage {

    @FindBy(xpath = "//button[@type='submit' and contains(@class,'primary-global-button')]")
    private WebElement editButton;

    private final long newsId;

    public EditNewsPage(WebDriver driver, long newsId) {
        super(driver);
        this.newsId = newsId;
    }

    @Override
    @Step("Open Edit News page")
    public EditNewsPage open() {
        driver.get(getBaseHost() + "/news/create-news?id=" + newsId);
        return this;
    }

    @Step("Check if Edit button is visible")
    public boolean isEditButtonVisible() {
        return isVisible(editButton);
    }

    @Step("Check if Edit button is enabled")
    public boolean isEditButtonEnabled() {
        return editButton.isEnabled();
    }

    @Step("Click Edit button")
    public void clickEdit() {
        waitUntilClickable(editButton);
        editButton.click();
    }

    @Step("Get Edit button text")
    public String getEditButtonText() {
        waitUntilVisible(editButton);
        return editButton.getText().trim();
    }

    @Override
    @Step("Reload EditNewsPage")
    public EditNewsPage reload() {
        super.reload();
        return this;
    }

    @Step("Edit news with title: {title}, tags: {tags}, source: {source}, content: [hidden], image: {imagePath}")
    public EditNewsPage editNews(String title, List<String> tags, String source, String content, String imagePath) {
        if (title != null) enterTitle(title);
        if (tags != null) {
            clearAllSelectedTags();
            selectTags(tags);
        }
        if (source != null) enterSource(source);
        if (content != null) {
            getContentComponent().enterContent(content);
        }

        if (imagePath != null) {
            getImageComponent().changeImage(imagePath);
        }
        return this;
    }

    @Step("Get news ID")
    public long getId() {
        return newsId;
    }
}
