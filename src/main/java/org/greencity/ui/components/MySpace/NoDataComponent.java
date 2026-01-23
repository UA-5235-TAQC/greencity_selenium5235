package org.greencity.ui.components.MySpace;

import org.greencity.ui.components.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

/**
 * Represents a "No Data" placeholder component displayed in various sections
 * of the MySpace page (e.g., no events, no habits).
 */
public class NoDataComponent extends BaseComponent {

    /** Root element of the "No Data" component */
    @FindBy(css = ".container")
    private WebElement container;

    /** Image element displayed in the placeholder (e.g., noNews.svg) */
    @FindBy(css = ".picture img")
    private WebElement image;

    /** Title text element (e.g., "Hi Eco Friend ;-)") */
    @FindBy(css = ".description__title h2")
    private WebElement title;

    /** Description text element explaining that there is no data */
    @FindBy(css = ".description__advise p")
    private WebElement description;

    public NoDataComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    /** Returns the title text of the placeholder */
    public String getTitle() {
        return title.getText().trim();
    }

    /** Returns the description text of the placeholder */
    public String getDescription() {
        return description.getText().trim();
    }

    /** Returns the src attribute of the image */
    public String getImageSrc() {
        return Objects.requireNonNull(image.getAttribute("src")).trim();
    }

    /** Checks whether the "No Data" component is visible in the DOM */
    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }
}
