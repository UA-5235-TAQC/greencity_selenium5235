package org.greencity.ui.pages;

import org.greencity.ui.components.NewsListItemComponent;
import org.greencity.ui.components.TagItem;
import org.greencity.ui.enums.EcoNewsTag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    protected List<WebElement> getCardElements() {
        return cards.findElements(By.cssSelector("li"));
    }

    public NewsListItemComponent[] getCards() {
        List<WebElement> cardElements = getCardElements();

        return cardElements.stream()
                .map(card -> new NewsListItemComponent(driver, card))
                .toArray(NewsListItemComponent[]::new);
    }

    public NewsListItemComponent getCardByIndex(int index) {
        NewsListItemComponent[] cards = getCards();

        if (index < 0 || index >= cards.length) throw new IndexOutOfBoundsException();

        return cards[index];
    }

    public NewsListItemComponent getCardByNewsId(int newsId) {
        NewsListItemComponent[] cards = getCards();

        for (NewsListItemComponent card : cards) {
            if (card.getNewsId() == newsId) {
                return card;
            }
        }

        throw new IllegalArgumentException("News card with id " + newsId + " not found");
    }

    public void switchToGridView() {
        gridViewBtn.click();
    }

    public void switchToListView() {
        listViewBtn.click();
    }

    public int getRemainingNewsCount() {
        return Integer.parseInt(remainingCountText.getText().replaceAll("[^0-9]", ""));
    }

    public void clickCreateNews() {
        createNewsBtn.click();
    }

    public TagItem[] getAllTags() {
        return tags.findElements(By.cssSelector("button.tag-button")).stream()
                .map(tag -> new TagItem(driver, tag))
                .toArray(TagItem[]::new);
    }

    public void filterByTag(EcoNewsTag tag) {
        TagItem[] tagItems = getAllTags();

        for (TagItem item : tagItems) {
            if (item.getName().equals(tag.getTagName())) {
                item.click();
                return;
            }
        }
    }

    public void openNewsCardByIndex(int index) {
        List<WebElement> cardElements = getCardElements();

        if (index < 0 || index >= cardElements.size()) throw new IndexOutOfBoundsException();

        cardElements.get(index).click();
    }

    public void openNewsCardByNewsId(int newsId) {
        for (WebElement card : getCardElements()) {
            String href = card.findElement(By.cssSelector("a.link")).getAttribute("href");

            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("/news/(\\d+)").matcher(href);

            if (matcher.find() && Integer.parseInt(matcher.group(1)) == newsId) {
                card.click();
                return;
            }
        }

        throw new IllegalArgumentException("News card with id " + newsId + " not found");
    }
}
