package org.greencity.ui.components.CreateEditNewsPage;

import io.qameta.allure.Step;
import org.greencity.ui.components.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContentComponent extends BaseComponent {
    @FindBy(css = ".ql-editor")
    private WebElement content;

    @FindBy(css = ".ql-toolbar")
    private WebElement contentToolbar;

    @FindBy(css = "p.quill-counter")
    private WebElement contentCounter;

    @FindBy(css = ".title-wrapper p.field-info")
    private WebElement contentMessage;

    public ContentComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Clear content text")
    public ContentComponent clearContent() {
        content.clear();
        return this;
    }

    @Step("Clear and enter content text")
    public ContentComponent enterContent(String text) {
        clearContent();
        content.sendKeys(text);
        return this;
    }

    @Step("Enter content text without clearing")
    public ContentComponent enterContentNotClear(String text) {
        content.sendKeys(text);
        return this;
    }

    @Step("Prepend text to existing content")
    public ContentComponent prependContent(String textToAdd) {
        String currentValue = getContentText();
        String newValue = textToAdd + (currentValue != null ? currentValue : "");
        enterContent(newValue);
        return this;
    }

    @Step("Get content character counter text (shows number of characters in content)")
    public String getContentCounter() {
        return contentCounter.getText();
    }

    @Step("Check if content is not highlighted in red because character count is at least 20")
    public boolean isContentInvalid() {
        return contentCounter.getAttribute("class").contains("warning");
    }

    @Step("Check if content is highlighted in red because character count is less than 20")
    public boolean isContentValid() {
        return contentCounter.getAttribute("class").contains("quill-valid");
    }

    @Step("Check if content editor is visible")
    public boolean isContentVisible() {
        return isVisible(content);
    }

    @Step("Check if content toolbar is visible")
    public boolean isContentToolbarVisible() {
        return isVisible(contentToolbar);
    }

    @Step("Check if content counter text (shows number of characters in content) is visible")
    public boolean isContentCounterVisible() {
        return isVisible(contentCounter);
    }

    @Step("Check if informational validation message is visible at the top-right of the content editor")
    public boolean isContentMessageVisible() {
        return isVisible(contentMessage);
    }

    @Step("Get content WebElement")
    public WebElement getContent() {
        return content;
    }

    @Step("Get content text")
    public String getContentText() {
        return content.getText();
    }

    @Step("Get informational validation message text displayed at the top-right of the content editor")
    public String getContentMessage() {
        return contentMessage.getText().trim();
    }

    @Step("Get content placeholder text")
    public String getContentPlaceholder() {
        return content.getAttribute("data-placeholder").trim();
    }

    @Step("Check if informational validation message is highlighted in red because character count is less than 20")
    public boolean isContentMessageInvalid() {
        return contentMessage.getAttribute("class").contains("warning");
    }

    @Step("Check if informational validation message displayed at the top-right of the content editor")
    public boolean isContentWarningDisplayed() {
        try {
            return contentMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get length of content text")
    public int getContentLengthNumber() {
        String text = getContentCounter();
        if (text.isEmpty()) {
            return 0;
        }
        String digits = text.replaceAll("\\D+", "");
        return Integer.parseInt(digits);
    }

    private ContentComponent removeContentChars(int count, boolean fromStart) {
        WebElement content = getContent();
        String currentValue = getContentText();
        if (currentValue != null && !currentValue.isEmpty()) {
            String newValue;
            if (fromStart) {
                newValue = currentValue.length() > count ? currentValue.substring(count) : "";
            } else {
                newValue = currentValue.length() > count ? currentValue.substring(0, currentValue.length() - count) : "";
            }
            clearContent();
            content.sendKeys(newValue);
        }
        return this;
    }

    @Step("Remove last {count} characters from content")
    public ContentComponent removeLastContentChars(int count) {
        return removeContentChars(count, false);
    }

    @Step("Remove first {count} characters from content")
    public ContentComponent removeFirstContentChars(int count) {
        return removeContentChars(count, true);
    }
}
