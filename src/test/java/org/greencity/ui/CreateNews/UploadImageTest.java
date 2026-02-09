package org.greencity.ui.CreateNews;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.greencity.ui.components.CreateEditNewsPage.ImageComponent;
import org.greencity.ui.testrunners.CreateNews.CreateNewsENTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;

@Tag("Create News")
@Epic("EcoNews Management")
@Feature("Create News")
@Story("Image upload validation for oversized files")
@Severity(SeverityLevel.NORMAL)

public class UploadImageTest extends CreateNewsENTestRunner {

    String tooLargeImagePath = new File("src/test/resources/images/UploadImageTest/Andromeda_Galaxy.jpg").getAbsolutePath();
    String smallPNGPath = new File("src/test/resources/images/UploadImageTest/Small PNG.png").getAbsolutePath();
    String gifPath = new File("src/test/resources/images/UploadImageTest/cactus.gif").getAbsolutePath();

    @Issue("6")
    @Description("The test checks successful validation when uploading a valid PNG image")
    @Test
    public void imgUploadPositive() {
        ImageComponent imageComponent = createNewsPage.getImageComponent();
        imageComponent = imageComponent.uploadImage(smallPNGPath);
        Assert.assertFalse(imageComponent.isImageErrorMsg());
        Assert.assertTrue(imageComponent.isImageVisible(),
                "Image should be visible");
        Assert.assertFalse(imageComponent.getUploadedImageSrc().isEmpty(),
                "Image upload field should not be empty");
        Assert.assertTrue(imageComponent.isUploadedImagePresent(),
                "Loaded image should be present");
        Assert.assertTrue(imageComponent.isCancelCropperButtonVisible(),
                "Cancel button on cropper should be visible");
        Assert.assertTrue(imageComponent.isSubmitCropperButtonVisible(),
                "Submit button on cropper should be visible");
        SoftAssert softAssert = new SoftAssert();
        Assert.assertEquals(
                imageComponent.getImageError(),
                "Upload only PNG or JPG. File size must be less than 10MB",
                "Error message should match"
        );
        softAssert.assertEquals(
                imageComponent.getCancelCropperText(),
                "Cancel",
                "Cancel cropper button text should match"
        );
        softAssert.assertEquals(
                imageComponent.getSubmitCropperText(),
                "Submit",
                "Submit cropper button text should match"
        );
        softAssert.assertAll();
    }

    @Issue("6")
    @Description("The test checks validation error when uploading a GIF image (unsupported format)")
    @Test
    public void imgUploadGIFNegative() {
        ImageComponent imageComponent = createNewsPage.getImageComponent();
        imageComponent = imageComponent.uploadImage(gifPath);
        Assert.assertTrue(imageComponent.isImageErrorMsg());
        Assert.assertFalse(imageComponent.isPreviewImage());
    }

    @Issue("17")
    @Description("The test checks validation when loading an image that is too large")
    @Test
    public void imgUploadTooLargeNegative() {
        ImageComponent imageComponent = createNewsPage.getImageComponent();
        imageComponent = imageComponent.uploadImage(tooLargeImagePath);
        Assert.assertTrue(imageComponent.isImageErrorMsg());
        Assert.assertFalse(imageComponent.isPreviewImage());
    }
}
