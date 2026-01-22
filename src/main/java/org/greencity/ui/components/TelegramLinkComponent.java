package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Component representing the Telegram chat button in the UI.
 * Provides methods to open the chat and check visibility.
 */
public class TelegramLinkComponent extends BaseComponent {

    @FindBy(css = "button.chat-pop-up")
    private WebElement chatButton;

    public TelegramLinkComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    /**
     * Opens Telegram chat by clicking the chat button
     */
    public void openChat() {
        chatButton.click();
    }

    /**
     * Checks whether chat button is displayed
     */
    public boolean isDisplayed() {
        return chatButton.isDisplayed();
    }
}
