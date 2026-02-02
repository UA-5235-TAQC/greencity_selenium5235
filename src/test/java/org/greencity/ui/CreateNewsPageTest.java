package org.greencity.ui;


import io.qameta.allure.*;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class CreateNewsPageTest extends BaseTestRunner {
    CreateNewsPage createNewsPage;
    String filePath;

    @Epic("Authentication")
    @Story("User login")
    @Description("A test that allows a given user to log in to the system")
    @Severity(SeverityLevel.CRITICAL)
    @BeforeClass
    public void loginUser() {
        createNewsPage = new CreateNewsPage(driver);
        filePath = new File("src/test/resources/images/Andromeda_Galaxy.jpg").getAbsolutePath();
        loginUser(createNewsPage);
    }

    @BeforeMethod
    public void beforeMethod() {
        new CreateNewsPage(driver).open();
    }

    @Epic("Smoke test")
    @Feature("Create news page")
    @Story("Uploading to large image")
    @Description("The test checks validation when loading an image that is too large")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://github.com/UA-5235-TAQC/greencity_selenium5235/issues/17")
    @Test
    public void imgUploadNegative() {
        createNewsPage.getImageComponent().uploadImage(filePath);

        Assert.assertTrue(createNewsPage.isImageErrorMsg());
        Assert.assertFalse(createNewsPage.isPreviewImage());
    }
}
