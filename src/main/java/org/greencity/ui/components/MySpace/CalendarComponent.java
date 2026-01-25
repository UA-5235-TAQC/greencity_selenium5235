package org.greencity.ui.components.MySpace;

import org.greencity.ui.components.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UI component that represents the calendar widget in the "My Space" section.
 */
public class CalendarComponent extends BaseComponent {

    /** Button used to navigate to the previous month */
    @FindBy(css = "img.arrow-previous")
    private WebElement previousMonthButton;

    /** Button used to navigate to the next month */
    @FindBy(css = "img.arrow-next")
    private WebElement nextMonthButton;

    /** Label that displays the current month and year (e.g. "March 2025") */
    @FindBy(css = "button.monthAndYear")
    private WebElement monthAndYearLabel;

    /** List of elements representing the names of the days of the week */
    @FindBy(css = ".days-name")
    private List<WebElement> daysOfWeek;

    /** List of elements representing all visible calendar days */
    @FindBy(css = ".calendar-grid-day")
    private List<WebElement> calendarDays;

    /** Element representing the currently selected day in the calendar */
    @FindBy(css = ".calendar-grid-day.current-day span")
    private WebElement currentDay;

    /** Element representing the name of the current day of the week */
    @FindBy(css = ".days-name.current-day-name")
    private WebElement currentDayOfWeek;

    public CalendarComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    /**
     * Clicks the button to navigate to the previous month.
     */
    public void clickPreviousMonth() {
        previousMonthButton.click();
    }

    /**
     * Clicks the button to navigate to the next month.
     */
    public void clickNextMonth() {
        nextMonthButton.click();
    }

    /**
     * Returns the text representing the current month and year.
     *
     * @return month and year text (e.g. "March 2025")
     */
    public String getMonthAndYear() {
        return monthAndYearLabel.getText().trim();
    }

    /**
     * Extracts and returns the current month name.
     *
     * @return current month name
     */
    public String getMonth() {
        return getMonthAndYear().split(" ")[0];
    }

    /**
     * Extracts and returns the current year.
     *
     * @return current year as an integer
     */
    public int getYear() {
        return Integer.parseInt(getMonthAndYear().split(" ")[1]);
    }

    /**
     * Returns a list of all visible day numbers displayed in the calendar.
     *
     * @return list of visible days as integers
     */
    public List<Integer> getVisibleDays() {
        return calendarDays.stream()
                .map(day -> day.findElement(By.tagName("span")).getText().trim())
                .filter(text -> !text.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    /**
     * Returns the currently selected day of the month.
     *
     * @return current day as an integer
     */
    public int getCurrentDay() {
        return Integer.parseInt(currentDay.getText().trim());
    }

    /**
     * Selects a specific day in the calendar.
     *
     * @param day day of the month to select
     * @throws RuntimeException if the specified day is not found
     */
    public void selectDay(int day) {
        calendarDays.stream()
                .filter(d -> d.getText().trim().equals(String.valueOf(day)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Day not found: " + day))
                .click();
    }

    /**
     * Returns the names of the days of the week displayed in the calendar.
     *
     * @return list of day names
     */
    public List<String> getDaysOfWeek() {
        return daysOfWeek.stream()
                .map(el -> el.getText().trim())
                .collect(Collectors.toList());
    }

    /**
     * Returns the name of the current day of the week.
     *
     * @return current day of week name
     */
    public String getDayOfWeek() {
        return currentDayOfWeek.getText().trim();
    }

    /**
     * Checks whether the calendar component is displayed.
     *
     * @return {@code true} if the calendar is visible, otherwise {@code false}
     */
    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }
}
