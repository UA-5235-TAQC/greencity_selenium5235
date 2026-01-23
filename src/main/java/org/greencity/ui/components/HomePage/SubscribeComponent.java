package org.greencity.ui.components.HomePage;

import org.greencity.ui.components.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the newsletter subscription section on the HomePage.
 * The component contains a QR code, a title, a description, an email input field,
 * a subscribe button, and a validation error message.
 */
public class SubscribeComponent extends BaseComponent {

    /**
     * QR code image element for accessing the mobile app
     */
    @FindBy(css = "#qr-code-wrapper img")
    private WebElement qrCodeImg;

    /**
     * Title of the subscription section (h2 element)
     */
    @FindBy(css = "#form-wrapper h2")
    private WebElement title;

    /**
     * Description paragraph under the title
     */
    @FindBy(css = "#form-wrapper p")
    private WebElement description;

    /**
     * Input field where the user enters their email
     */
    @FindBy(css = "#form-wrapper input[type='email']")
    private WebElement emailInput;

    /**
     * Button to submit the subscription form
     */
    @FindBy(css = "#form-wrapper button.primary-global-button")
    private WebElement subscribeButton;

    /**
     * Paragraph element showing validation error message
     */
    @FindBy(id = "validation-error")
    private WebElement validationError;

    public SubscribeComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    /**
     * Returns the src attribute of the QR code image
     */
    public String getQrCode() {
        return qrCodeImg.getAttribute("src");
    }

    /**
     * Returns the text of the subscription title
     */
    public String getTitleText() {
        return title.getText();
    }

    /**
     * Returns the text of the subscription description
     */
    public String getDescriptionText() {
        return description.getText();
    }

    /**
     * Enters the given email into the email input field
     */
    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    /**
     * Clicks the subscribe button to submit the form
     */
    public void clickSubscribe() {
        subscribeButton.click();
    }

    /**
     * Checks whether the validation error is visible
     */
    public boolean isValidationErrorDisplayed() {
        return validationError.isDisplayed();
    }

    /**
     * Returns the text of the validation error message
     */
    public String getValidationError() {
        return validationError.getText();
    }

    /**
     * Checks whether the subscription component is visible and present in the DOM
     */
    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }
}
