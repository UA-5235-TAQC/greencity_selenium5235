package org.greencity.ui.pages.CreateEditNews;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditNewsPage extends CreateEditNewsPage {

    @FindBy(xpath = "//button[@type='submit' and contains(@class,'primary-global-button')]")
    private WebElement editButton;

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
}
