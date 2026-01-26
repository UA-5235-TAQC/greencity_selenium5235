package org.greencity.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NewsPreviewPage extends BasePage {

    @FindBy(css = "main-content app-container")
    protected WebElement root;

    @FindBy(css = ".button-link")
    private WebElement backToCreateNewsBtn;

    @FindBy(css = ".submit-form")
    private WebElement publicNewsBtn;

    @FindBy(css = ".news-title")
    private WebElement newsTitle;

    @FindBy(css = ".tags")
    private WebElement tagsRoot;

    @FindBy(css = ".news-info-date")
    private WebElement newsCreatingDate;

    @FindBy(css = ".news-info-author")
    private WebElement authorName;

    @FindBy(css = ".news-image-img")
    private WebElement newsImage;

    @FindBy(css = "..news-text-content p")
    private WebElement newsText;

    @FindBy(css = ".source-text")
    private WebElement newsSource;

    public NewsPreviewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public BasePage open() {
        return this;
    }

    @Override
    public boolean isPageOpened() {
        return isVisible(root);
    }

    public List<WebElement> getTagItems() {
        return tagsRoot.findElements(By.className("tags-item"));
    }

    public String getAuthorName() {
        String text = authorName.getText();
        return text.substring(text.indexOf(" ") + 1);
    }

    public void clickPublicNewsBtn() {
        publicNewsBtn.click();
    }

    public void clickBackToCreateNewsBtn() {
        backToCreateNewsBtn.click();
    }

    //getters
    public WebElement getPublicNewsBtnElement() {
        return publicNewsBtn;
    }

    public WebElement getBackToCreateNewsBtnElement() {
        return backToCreateNewsBtn;
    }

    public WebElement getNewsTitleElement() {
        return newsTitle;
    }

    public String getNewsTitle() {
        return newsTitle.getText();
    }

    public WebElement getNewsCreatingDateElement() {
        return newsCreatingDate;
    }

    public String getNewsCreatingDate() {
        return newsCreatingDate.getText();
    }

    public WebElement getNewsImageElement() {
        return newsImage;
    }

    public WebElement getNewsTextElement() {
        return newsText;
    }

    public String getNewsText() {
        return newsText.getText();
    }

    public WebElement getNewsSourceElement() {
        return newsSource;
    }

    public String getNewsSource() {
        return newsSource.getText();
    }
}
