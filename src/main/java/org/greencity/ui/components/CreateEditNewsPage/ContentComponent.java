package org.greencity.ui.components.CreateEditNewsPage;

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

    public ContentComponent clearContent() {
        content.clear();
        return this;
    }

    public ContentComponent enterContent(String text) {
        clearContent();
        content.sendKeys(text);
        return this;
    }

    public ContentComponent enterContentNotClear(String text) {
        content.sendKeys(text);
        return this;
    }

    public ContentComponent prependContent(String textToAdd) {
        String currentValue = getContentText();
        String newValue = textToAdd + (currentValue != null ? currentValue : "");
        enterContent(newValue);
        return this;
    }

    public String getContentCounter() {
        return contentCounter.getText();
    }

    public boolean isContentInvalid() {
        return contentCounter.getAttribute("class").contains("warning");
    }

    public boolean isContentValid() {
        return contentCounter.getAttribute("class").contains("quill-valid");
    }

    public boolean isContentVisible() {
        return isVisible(content);
    }

    public boolean isContentToolbarVisible() {
        return isVisible(contentToolbar);
    }

    public boolean isContentCounterVisible() {
        return isVisible(contentCounter);
    }

    public boolean isContentMessageVisible() {
        return isVisible(contentMessage);
    }

    public WebElement getContent() {
        return content;
    }

    public String getContentText() {
        return content.getText();
    }

    public String getContentMessage() {
        return contentMessage.getText().trim();
    }

    public String getContentPlaceholder() {
        return content.getAttribute("data-placeholder").trim();
    }

    public boolean isContentMessageInvalid() {
        return contentMessage.getAttribute("class").contains("warning");
    }

    public boolean isContentWarningDisplayed() {
        try {
            return contentMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

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

    public ContentComponent removeLastContentChars(int count) {
        return removeContentChars(count, false);
    }

    public ContentComponent removeFirstContentChars(int count) {
        return removeContentChars(count, true);
    }
}
