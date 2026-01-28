package org.greencity.ui.components;

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

    public ContentComponent enterContent(String text) {
        content.clear();
        content.sendKeys(text);
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

    public String getContent() {
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
}
