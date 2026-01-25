package org.greencity.ui;

import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.testrunners.AuthenticatedTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TagSelectionTest extends AuthenticatedTestRunner {

    @Test
    public void checkTagSelection() {
        EcoNewsPage ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.open();
        Assert.assertTrue(ecoNewsPage.isPageOpened(), "Eco News page should be opened");

        System.out.println(ecoNewsPage.getCurrentUrl());

        CreateNewsPage createNewsPage = ecoNewsPage.clickCreateNews();
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page should be opened");
    }
}
