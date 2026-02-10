package org.greencity.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UbsCourierPage extends BasePage {

    @FindBy(css = "div.our-partners-section__icons")
    private WebElement partnersSection;

    @FindBy(css = "section#events a[href*='greenCity/news']")
    private WebElement readAllNewsLink;

    public UbsCourierPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public UbsCourierPage open() {
        driver.get(getBaseHost() + "#/ubs/");
        return this;
    }

    @Override
    public boolean isPageOpened() {
        return isVisible(partnersSection);
    }

    public boolean isPageOpenedAfterCancelModalClickYesCancel() {
        return isVisible(readAllNewsLink);
    }

    @Override
    public UbsCourierPage waitUntilOpened() {
        return this;
    }
}
