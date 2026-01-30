package org.greencity.ui.pages.CreateEditNews;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CreateNewsPage extends CreateEditNewsPage {

    @FindBy(xpath = "//button[@type='submit' and contains(@class,'primary-global-button')]")
    private WebElement publishBtn;

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CreateNewsPage open() {
        super.open();
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
}
