package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.pages.CreateEditNews.CreateNewsPage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

@Tag("Create News")
@Epic("EcoNews Management")
@Feature("Create News")
@Story("Image upload validation for oversized files")
@Severity(SeverityLevel.NORMAL)
@Issue("17")
public class UploadImageNegativeTest extends BaseTestRunner {
    CreateNewsPage createNewsPage;
    String filePath = new File("src/test/resources/images/Andromeda_Galaxy.jpg").getAbsolutePath();


    @BeforeMethod
    public void navigateToCreateNewsPage() {
        LoginUser();
        createNewsPage = new CreateNewsPage(driver).open();
    }

    @Description("The test checks validation when loading an image that is too large")
    @Test
    public void imgUploadNegative() {
        createNewsPage.getImageComponent().uploadImage(filePath);

        Assert.assertTrue(createNewsPage.getImageComponent().isImageErrorMsg());
        Assert.assertFalse(createNewsPage.getImageComponent().isPreviewImage());
    }
}
