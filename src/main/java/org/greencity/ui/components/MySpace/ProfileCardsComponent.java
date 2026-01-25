package org.greencity.ui.components.MySpace;

import org.greencity.ui.components.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Component representing the profile cards section on the MySpace page.
 * Each card displays information such as "Fact of the Day" and may include a title, description, and decorative image.
 */
public class ProfileCardsComponent extends BaseComponent {

    /** Root container element of the profile cards section */
    @FindBy(css = ".right-cards")
    private WebElement cardsContainer;

    /** List of all individual profile card elements */
    @FindBy(css = ".right-cards .card")
    private List<WebElement> cards;

    /** List of titles of all profile cards */
    @FindBy(css = ".right-cards .card .cart-title")
    private List<WebElement> cardTitles;

    /** List of description texts of all profile cards */
    @FindBy(css = ".right-cards .card .card-description")
    private List<WebElement> cardDescriptions;

    /** List of decorative/background images of all profile cards */
    @FindBy(css = ".right-cards .card .shape-img img")
    private List<WebElement> cardImages;

    public ProfileCardsComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    /** Returns titles of all visible profile cards */
    public List<String> getCardTitles() {
        return cardTitles.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /** Returns descriptions of all visible profile cards */
    public List<String> getCardDescriptions() {
        return cardDescriptions.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /** Returns title of a profile card by index */
    public String getCardTitle(int index) {
        List<String> titles = getCardTitles();
        if (index < 0 || index >= titles.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid index " + index + " for card titles. Number of cards: " + titles.size()
            );
        }
        return titles.get(index);
    }

    /** Returns description of a profile card by index */
    public String getCardDescription(int index) {
        List<String> descriptions = getCardDescriptions();
        if (index < 0 || index >= descriptions.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid index " + index + " for card descriptions. Number of cards: " + descriptions.size()
            );
        }
        return descriptions.get(index);
    }

    /** Returns number of visible profile cards */
    public int getCardsCount() {
        return cards.size();
    }

    /** Checks whether the profile cards component is displayed */
    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }
}
