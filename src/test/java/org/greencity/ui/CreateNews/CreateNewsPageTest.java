package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class CreateNewsPageTest extends BaseTestRunner {
    CreateNewsPage createNewsPage;
    String filePath = new File("src/test/resources/images/Andromeda_Galaxy.jpg").getAbsolutePath();


    @BeforeMethod
    public void navigateTocreateNewsPage() {
        LoginUser();
        createNewsPage = new CreateNewsPage(driver).open();
    }

    @Epic("Smoke test")
    @Feature("Create news page")
    @Story("Uploading to large image")
    @Description("The test checks validation when loading an image that is too large")
    @Severity(SeverityLevel.NORMAL)
    @Issue("17")
    @Test
    public void imgUploadNegative() {
        createNewsPage.getImageComponent().uploadImage(filePath);

        Assert.assertTrue(createNewsPage.getImageComponent().isImageErrorMsg());
        Assert.assertFalse(createNewsPage.getImageComponent().isPreviewImage());
    }
}
