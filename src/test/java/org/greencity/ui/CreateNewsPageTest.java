package org.greencity.ui;

import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class CreateNewsPageTest extends BaseTestRunner {
    CreateNewsPage createNewsPage;
    String filePath;

    @BeforeClass
    public void LoginUser() {
        createNewsPage = new CreateNewsPage(driver);
        filePath = new File("src/test/resources/images/Andromeda_Galaxy.jpg").getAbsolutePath();
        loginUser(createNewsPage);
    }

    @BeforeMethod
    public void beforeMethod() {
        new CreateNewsPage(driver).open();
    }

    @Test
    public void imgUploadNegative() {
        createNewsPage
                .uploadImage(filePath);

        Assert.assertTrue(createNewsPage.isImageErrorMsg());
        Assert.assertFalse(createNewsPage.isPreviewImage());
    }
}
