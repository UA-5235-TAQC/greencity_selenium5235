package org.greencity.ui.pages;

import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.enums.EcoNewsTag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public void enterSearch(String text) {
        if (!searchInput.isDisplayed()) {
            searchBtn.click();
        }
        searchInput.sendKeys(text);
    }

    public void closeSearch() {
        if (searchInput.isDisplayed()) {
            closeSearchIcon.click();
        }
    }

    public void clickBookmark() {
        bookmarkBtn.click();
    }

    public void clickMyEvents() {
        myEventsBtn.click();
    }

    public void switchToGridView() {
        gridViewBtn.click();
    }

    public void switchToListView() {
        listViewBtn.click();
    }

    public int getRemainingNewsCount() {
        String digits = remainingCountText.getText().replaceAll("[^0-9]", "");
        if (digits == null || digits.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(digits);
    }

    public void clickCreateNews() {
        createNewsBtn.click();
    }

    public TagItem[] getAllTags() {
        return tags.findElements(By.cssSelector("button.tag-button")).stream()
                .map(tag -> new TagItem(driver, tag))
                .toArray(TagItem[]::new);
    }

    public void clickTag(EcoNewsTag tag) {
        TagItem[] tagItems = getAllTags();

        for (TagItem item : tagItems) {
            if (item.getName().equals(tag.getTagName())) {
                item.click();
                return;
            }
        }
    }

    public NewsListItemComponent[] getNewsCards() {
        return cards.findElements(By.cssSelector("li")).stream()
                .map(card -> new NewsListItemComponent(driver, card))
                .toArray(NewsListItemComponent[]::new);
    }

    public NewsListItemComponent getNewsCardByIndex(int index) {
        NewsListItemComponent[] cards = getNewsCards();

        if (index < 0 || index >= cards.length) throw new IndexOutOfBoundsException();

        return cards[index];
    }

    public NewsListItemComponent getNewsCardById(int newsId) {
        NewsListItemComponent[] cards = getNewsCards();

        for (NewsListItemComponent card : cards) {
            if (card.getNewsId() == newsId) {
                return card;
            }
        }

        throw new IllegalArgumentException("News card with id " + newsId + " not found");
    }

    public void clickNewsCardByIndex(int index) {
        NewsListItemComponent card = getNewsCardByIndex(index);
        card.click();
    }

    public void clickNewsCardByNewsId(int newsId) {
        NewsListItemComponent card = getNewsCardById(newsId);
        card.click();
    }
}
