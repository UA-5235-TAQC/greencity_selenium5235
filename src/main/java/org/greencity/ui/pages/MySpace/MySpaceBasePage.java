package org.greencity.ui.pages.MySpace;

import org.greencity.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;

public class MySpaceBasePage extends BasePage {
    public MySpaceBasePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open() {

    }

    @Override
    public boolean isPageOpened() {
        return false;
    }
}
