package org.greencity.ui.components.HomePage;

import org.greencity.ui.components.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents a single statistics row displayed on the HomePage.
 */
public class StatRowComponent extends BaseComponent {

    // Root container of the stat row
    @FindBy(css = "div.stat-row")
    private WebElement container;

    // Icon image
    @FindBy(css = "div.stat-row-image img")
    private WebElement icon;

    // Main statistic text (e.g., "Did not take 0 bags")
    @FindBy(css = "h3")
    private WebElement statText;

    // Number inside the statistic
    @FindBy(css = "h3 span")
    private WebElement statNumber;

    // Description paragraph
    @FindBy(css = "p")
    private WebElement description;

    // Call-to-action button
    @FindBy(css = "button.primary-global-button")
    private WebElement actionButton;

    // Location icon image
    @FindBy(css = "div.location-row img")
    private WebElement locationImage;

    // Location link
    @FindBy(css = "div.location-row a")
    private WebElement locationLink;

    public StatRowComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    /** Returns the full statistic text including the number */
    public String getStatText() {
        return statText.getText().trim();
    }

    /** Returns the numeric value inside the statistic */
    public int getStatNumber() {
        String text = statNumber.getText().trim();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    /** Returns the description text */
    public String getDescription() {
        return description.getText().trim();
    }

    /** Returns the src attribute of the icon image */
    public String getIconSrc() {
        return icon.getAttribute("src");
    }

    /** Clicks the main action button */
    public void clickActionButton() {
        actionButton.click();
    }

    /** Returns the text of the action button */
    public String getActionButtonText() {
        return actionButton.getText().trim();
    }

    /** Returns the text of the location link */
    public String getLocationLinkText() {
        return locationLink.getText().trim();
    }

    /** Clicks the location link */
    public void clickLocationLink() {
        locationLink.click();
    }

    /** Returns the src attribute of the location icon */
    public String getLocationImageSrc() {
        return locationImage.getAttribute("src");
    }

    /** Checks whether the stat row component is visible in the DOM */
    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }
}
