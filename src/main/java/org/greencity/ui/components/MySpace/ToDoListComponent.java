package org.greencity.ui.components.MySpace;

import org.greencity.ui.components.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component representing the To-do list section on the MySpace page.
 * Provides access to to-do items, header, item count, and visibility checks.
 */
public class ToDoListComponent extends BaseComponent {

    /** Root container element of the to-do list section */
    @FindBy(css = "app-to-do-list .outer")
    private WebElement toDoListContainer;

    /** Header element displaying the to-do list title (e.g., "My To-do list") */
    @FindBy(css = "app-to-do-list .header")
    private WebElement header;

    /** Element displaying the number of to-do items */
    @FindBy(css = "app-to-do-list .items-count")
    private WebElement itemsCountLabel;

    /** List of elements representing individual to-do items */
    @FindBy(css = "app-to-do-list .to-do-list-block > div:not(.header-position)")
    private List<WebElement> toDoItems;

    public ToDoListComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    /**
     * Returns the text of the to-do list header
     *
     * @return header text
     */
    public String getHeader() {
        return header.getText().trim();
    }

    /**
     * Returns the number of to-do items displayed
     *
     * @return number of items
     */
    public int getItemsCount() {
        String text = itemsCountLabel.getText().replaceAll("\\D+", ""); // extract digits
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    /**
     * Returns a list of texts for all visible to-do items
     *
     * @return list of item texts
     */
    public List<String> getToDoItems() {
        return toDoItems.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /**
     * Returns the text of a to-do item by index.
     * Throws IndexOutOfBoundsException if index is invalid.
     *
     * @param index position of the to-do item in the list
     * @return text of the to-do item
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public String getItem(int index) {
        if (index < 0 || index >= toDoItems.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid index " + index + ". To-do list contains " + toDoItems.size() + " items."
            );
        }
        return toDoItems.get(index).getText().trim();
    }

    /**
     * Checks whether the to-do list component is displayed
     *
     * @return true if displayed, false otherwise
     */
    public boolean isDisplayed() {
        return toDoListContainer.isDisplayed();
    }
}
