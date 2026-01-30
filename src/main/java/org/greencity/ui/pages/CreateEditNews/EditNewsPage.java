package org.greencity.ui.pages.CreateEditNews;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

public class EditNewsPage extends CreateEditNewsPage {

    @FindBy(xpath = "//button[@type='submit' and contains(@class,'primary-global-button')]")
    private WebElement editButton;

    @FindBy(css = "img.ngx-ic-source-image")
    private WebElement newsImage;

    private final long newsId;

    public EditNewsPage(WebDriver driver, long newsId) {
        super(driver);
        this.newsId = newsId;
    }

    @Override
    public EditNewsPage open() {
        driver.get(getBaseHost() + "/news/create-news?id=" + newsId);
        return this;
    }

    public boolean isEditButtonVisible() {
        return isVisible(editButton);
    }

    public boolean isEditButtonEnabled() {
        return editButton.isEnabled();
    }

    public void clickEdit() {
        waitUntilClickable(editButton);
        editButton.click();
    }

    public CreateEditNewsPage uploadImage(String filePath) {
        newsImage.sendKeys(filePath);
        return this;
    }

    public boolean isImageVisible() {
        return isVisible(newsImage);
    }

    public String getEditImageSrc() {
        waitUntilVisible(newsImage);
        return newsImage.getAttribute("src");
    }
}
