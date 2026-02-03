package org.greencity.ui.pages;

import io.qameta.allure.Step;
import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.enums.EcoNewsTag;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class EcoNewsPage extends BasePage {
    @FindBy(css = "h1.main-header")
    protected WebElement pageTitle;
    @FindBy(css = "div#create-button")
    protected WebElement createNewsBtn;
    @FindBy(css = "[aria-label='filter by items']")
    protected WebElement tags;
    @FindBy(css = "h2")
    protected WebElement remainingCountText;
    @FindBy(css = "ul.list")
    protected WebElement cards;
    @FindBy(css = "[aria-label='table view']")
    protected WebElement gridViewBtn;
    @FindBy(css = "[aria-label='list view']")
    protected WebElement listViewBtn;
    @FindBy(css = "div:has(img.my-events-img)")
    protected WebElement myEventsBtn;
    @FindBy(css = "div:has(span.bookmark-img)")
    protected WebElement bookmarkBtn;
    @FindBy(css = "div:has(span.search-img)")
    protected WebElement searchBtn;
    @FindBy(css = "input.place-input")
    protected WebElement searchInput;
    @FindBy(css = "img[alt='cancel search']")
    protected WebElement closeSearchIcon;

    public EcoNewsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Eco News page")
    @Override
    public EcoNewsPage open() {
        driver.get(getBaseHost() + "/news");
        return new EcoNewsPage(driver);
    }

    @Step("Verify Eco News page is opened")
    @Override
    public boolean isPageOpened() {
        return isVisible(pageTitle);
    }

    @Step("Wait until Eco News page is opened")
    @Override
    public EcoNewsPage waitUntilOpened() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        return this;
    }

    @Step("Get Eco News page title")
    public String getPageTitle() {
        return pageTitle.getText();
    }

    @Step("Enter search text")
    public void enterSearch(String text) {
        if (!searchInput.isDisplayed()) {
            searchBtn.click();
        }
        searchInput.sendKeys(text);
    }

    @Step("Close search input")
    public void closeSearch() {
        if (searchInput.isDisplayed()) {
            closeSearchIcon.click();
        }
    }

    @Step("Click Bookmark button")
    public void clickBookmark() {
        bookmarkBtn.click();
    }

    @Step("Click My Events button ")
    public void clickMyEvents() {
        myEventsBtn.click();
    }

    @Step("Switch news list view to grid")
    public void switchToGridView() {
        gridViewBtn.click();
    }

    @Step("Switch news list view to list")
    public void switchToListView() {
        listViewBtn.click();
    }

    @Step("Get count of remaining news")
    public int getRemainingNewsCount() {
        String digits = remainingCountText.getText().replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(digits);
    }

    @Step("Click on Create News button")
    public CreateNewsPage clickCreateNews() {
        createNewsBtn.click();
        return new CreateNewsPage(driver);
    }

    @Step("Get all available tags")
    public List<TagItem> getAllTags() {
        return  tags.findElements(By.cssSelector("button.tag-button")).stream()
                .map(tag -> new TagItem(driver, tag))
                .toList();
    }

    @Step("Remove all selected tags")
    public void removeAllSelectedTags() {
        List<TagItem> tags = getAllTags();
        tags.forEach(tag -> {
            if (tag.isSelected()) {
                tag.click();
            }
        });
    }

    @Step("Click on tag by name")
    public void clickTag(EcoNewsTag tag) {
        String expectedName = tag.getByLocale(getHeader().getCurrentLocale());

        for (TagItem item : getAllTags()) {
            if (item.getName().equalsIgnoreCase(expectedName)) {
                item.click();
                return;
            }
        }

        throw new RuntimeException("Tag not found: " + expectedName);
    }

    @Step("Get all news cards")
    public List<NewsListItemComponent> getNewsCards() {
        return cards.findElements(By.cssSelector("li")).stream()
                .map(card -> new NewsListItemComponent(driver, card))
                .toList();
    }

    @Step("Get a news card by index")
    public NewsListItemComponent getNewsCardByIndex(int index) {
        List<NewsListItemComponent> cards = getNewsCards();

        if (index < 0 || index >= cards.size()) {
            throw new IndexOutOfBoundsException("Invalid news card index: " + index + ". Valid index range: 0.." + (cards.size() - 1) + " (total cards: " + cards.size() + ")");
        }

        return cards.get(index);
    }

    @Step("Get a news card by id")
    public NewsListItemComponent getNewsCardById(int newsId) {
        List<NewsListItemComponent> cards = getNewsCards();

        for (NewsListItemComponent card : cards) {
            if (card.getNewsId() == newsId) {
                return card;
            }
        }

        throw new IllegalArgumentException("News card with id " + newsId + " not found");
    }

    @Step("Click on a news card by index")
    public void clickNewsCardByIndex(int index) {
        NewsListItemComponent card = getNewsCardByIndex(index);
        card.click();
    }

    @Step("Click on a news card by id")
    public void clickNewsCardByNewsId(int newsId) {
        NewsListItemComponent card = getNewsCardById(newsId);
        card.click();
    }
}
