package org.greencity.ui.pages.CreateEditNews;

import io.qameta.allure.Step;
import org.greencity.ui.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class NewsPreviewPage extends BasePage {

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

    @FindBy(css = ".news-text-content p")
    private WebElement newsText;

    @FindBy(css = ".source-text")
    private WebElement newsSource;

    @FindBy(css = ".main-content.app-container")
    private WebElement root;

    public NewsPreviewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Just a plug for the open() method")
    @Override
    public BasePage open() {
        return null;
    }

    @Step("Waiting for the PreviewPage to load")
    @Override
    public boolean isPageOpened() {
        waitUntilVisible(newsTitle);
        return newsTitle.isDisplayed();
    }

    @Step("Just a plug for waitUntilOpened() method")
    @Override
    public NewsPreviewPage waitUntilOpened() {
        return this;
    }

    @Step("Getting a list of tags")
    public List<WebElement> getTagItems() {
        return tagsRoot.findElements(By.className("tags-item"));
    }

    @Step("Getting a list of tags text")
    public List<String> getTagTexts() {
        return getTagItems().stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Step("Getting the author's name")
    public String getAuthorName() {
        String text = authorName.getText();
        return text.substring(text.indexOf(" ") + 1);
    }

    @Step("Click the 'Public' button")
    public void clickPublicNewsBtn() {
        publicNewsBtn.click();
    }

    @Step("Click the 'BackToCreateNews' button")
    public CreateNewsPage clickBackToCreateNewsBtn() {
        waitUntilClickable(backToCreateNewsBtn);
        backToCreateNewsBtn.click();
        return new CreateNewsPage(driver);
    }

    @Step("Return to creating news with ID: {newsId}")
    public EditNewsPage backToEditing(long newsId) {
        waitUntilClickable(backToCreateNewsBtn);
        backToCreateNewsBtn.click();
        return new EditNewsPage(driver, newsId);
    }

    //getters
    @Step("Getting the 'Public' button element")
    public WebElement getPublicNewsBtnElement() {
        return publicNewsBtn;
    }

    @Step("Getting the 'BackToCreateNews' button element")
    public WebElement getBackToCreateNewsBtnElement() {
        return backToCreateNewsBtn;
    }

    @Step("Getting a news title element")
    public WebElement getNewsTitleElement() {
        return newsTitle;
    }

    @Step("Getting a news title text")
    public String getNewsTitle() {
        return newsTitle.getText();
    }

    @Step("Getting a news creating date element")
    public WebElement getNewsCreatingDateElement() {
        return newsCreatingDate;
    }

    @Step("Getting a news creating date text")
    public String getNewsCreatingDate() {
        return newsCreatingDate.getText();
    }

    @Step("Getting a news image element")
    public WebElement getNewsImageElement() {
        return newsImage;
    }

    @Step("Checking the visibility of an uploaded image")
    public boolean isImageUploadInputVisible() {
        return isVisible(newsImage.findElement(By.xpath("..")));
    }

    @Step("Getting a news content element")
    public WebElement getNewsTextElement() {
        return newsText;
    }

    @Step("Getting a news content text")
    public String getNewsText() {
        return newsText.getText();
    }

    @Step("Getting a news source element")
    public WebElement getNewsSourceElement() {
        return newsSource;
    }

    @Step("Getting a news content text")
    public String getNewsSource() {
        return newsSource.getText();
    }

    @Step("Getting the source of an image from news")
    public String getPreviewImageSrc() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(driver -> {
            String src = newsImage.getAttribute("src");
            return src != null && !src.isEmpty();
        });
        return newsImage.getAttribute("src");
    }
}
