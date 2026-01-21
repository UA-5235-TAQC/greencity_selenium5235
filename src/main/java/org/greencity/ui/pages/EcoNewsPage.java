package org.greencity.ui.pages;

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
}
