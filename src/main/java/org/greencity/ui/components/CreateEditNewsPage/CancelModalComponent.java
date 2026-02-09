package org.greencity.ui.components.CreateEditNewsPage;

import io.qameta.allure.Step;
import org.greencity.ui.components.BaseComponent;
import org.greencity.ui.pages.UbsCourierPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CancelModalComponent extends BaseComponent {

    @FindBy(css = ".warning-text")
    private WebElement messageContainer;

    @FindBy(css = ".buttons-container .primary-global-button")
    private WebElement yesCancelBtn;

    @FindBy(css = ".buttons-container .secondary-global-button")
    private WebElement continueEditingBtn;

    @FindBy(css = ".close")
    private WebElement closeBtn;

    @FindBy(css = ".warning-title")
    private WebElement warningTitle;

    @FindBy(css = ".warning-subtitle")
    private WebElement warningSubtitle;

    public CancelModalComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Get cancel modal message text")
    public String getMessage() {
        return messageContainer.getText().trim();
    }

    @Step("Click 'Yes, cancel' button in cancel modal")
    public UbsCourierPage clickYesCancel() {
        yesCancelBtn.click();
        return new UbsCourierPage(driver);
    }

    @Step("Click 'Continue editing' button in cancel modal")
    public void clickContinueEditing() {
        continueEditingBtn.click();
    }

    @Step("Get 'Yes, cancel' button text")
    public String getYesCancelButtonText() {
        return yesCancelBtn.getText().trim();
    }

    @Step("Get 'Continue editing' button text")
    public String getContinueEditingButtonText() {
        return continueEditingBtn.getText().trim();
    }

    @Step("Click close (X) button in cancel modal")
    public void clickClose() {
        closeBtn.click();
    }

    @Step("Check if cancel modal is visible")
    public boolean isVisible() {
        return rootElement.isDisplayed();
    }

    @Step("Get cancel modal warning title text")
    public String getWarningTitleText() {
        return warningTitle.getText().trim();
    }

    @Step("Get cancel modal warning subtitle text")
    public String getWarningSubtitleText() {
        return warningSubtitle.getText().trim();
    }

    @Step("Check if 'Yes, cancel' button is visible")
    public boolean isCancelButtonVisible() {
        return isVisible(yesCancelBtn);
    }

    @Step("Check if 'Continue editing' button is visible")
    public boolean isContinueEditingButtonVisible() {
        return isVisible(continueEditingBtn);
    }

    @Step("Wait until cancel modal becomes visible")
    public void waitUntilVisible() {
        waitUntilVisible(rootElement);
    }

    @Step("Wait until cancel modal is closed")
    public void waitUntilClosed() {
        wait.until(ExpectedConditions.invisibilityOf(rootElement));
    }
}
