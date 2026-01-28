package org.greencity.ui;

import org.greencity.ui.components.CancelModalComponent;
import org.greencity.ui.components.HeaderComponent;
import org.greencity.ui.pages.CreateNewsPage;
import org.greencity.ui.pages.EcoNewsPage;
import org.greencity.ui.pages.HomePage;
import org.greencity.ui.testrunners.BaseTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;


/**
 * Test Case: Cancel Button Behavior
 * 
 * Description: Verify that clicking the "Cancel" button triggers a confirmation modal 
 * and that selecting "Yes, cancel" closes the form and returns the user to the news page.
 * Also verify that "Continue editing" keeps the form open with data intact.
 */

public class CancelButtonBehaviorTest extends BaseTestRunner {

    private EcoNewsPage ecoNewsPage;
    private CreateNewsPage createNewsPage;
    private static final String TEST_TITLE = "Test";
    private static final String TEST_CONTENT = "Test content with 20 chars";
    private static final String EXPECTED_MODAL_MESSAGE = "All created content will be lost. Do you still want to cancel news creating?";

  @BeforeMethod
  public void beforeMethod() {
        HomePage homePage = new HomePage(driver);
        loginUser(homePage);
    
        driver.get(testValueProvider.getBaseUIGreenCityUrl() + "/news");
    
        ecoNewsPage = new EcoNewsPage(driver);
        ecoNewsPage.clickCreateNews();
    
        createNewsPage = new CreateNewsPage(driver);
        Assert.assertTrue(createNewsPage.isPageOpened(), "Create News page was not opened");
    }
  

    @Test
    public void testCancelButtonBehavior() {
        
        // Step 1-2: Fill form and click Cancel
        createNewsPage.enterTitle(CancelButtonBehaviorTest.TEST_TITLE);
        createNewsPage.enterContent(CancelButtonBehaviorTest.TEST_CONTENT);
        createNewsPage.clickCancel();

        // Step 3-4: Verify modal appears with correct content
        Assert.assertTrue(createNewsPage.isCancelModalDisplayed(), 
                "Confirmation modal should appear after clicking Cancel");
        
        CancelModalComponent cancelModal = createNewsPage.getCancelModal();
        Assert.assertEquals(cancelModal.getMessage(), EXPECTED_MODAL_MESSAGE,
                "Modal message should match expected text");
        Assert.assertTrue(cancelModal.isCancelButtonVisible(),
                "'Yes, cancel' button should be visible");
        Assert.assertTrue(cancelModal.isContinueEditingButtonVisible(),
                "'Continue editing' button should be visible");

        // Step 5: Click "Yes, cancel" and verify redirect
        cancelModal.clickYesCancel();
        
        ecoNewsPage = new EcoNewsPage(driver);
        Assert.assertTrue(ecoNewsPage.isPageOpened(), 
                "User should be redirected to EcoNewsPage");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/news") && !currentUrl.contains("/create-news"),
                "URL should be news page, not create-news page");

        // --- Scenario 2: Click "Continue editing" button ---
        
        // Step 6-7: Navigate back and fill form again
        ecoNewsPage.clickCreateNews();
        createNewsPage = new CreateNewsPage(driver);
        Assert.assertTrue(createNewsPage.isPageOpened(), "CreateNewsPage should be opened again");
        
        createNewsPage.enterTitle(TEST_TITLE);
        createNewsPage.enterContent(TEST_CONTENT);

        // Step 8: Click Cancel button
        createNewsPage.clickCancel();
        Assert.assertTrue(createNewsPage.isCancelModalDisplayed(), "Modal should be visible");

        // Step 9: Click "Continue editing"
        cancelModal = createNewsPage.getCancelModal();
        cancelModal.clickContinueEditing();

        // Step 10: Verify modal is closed and form is still open with data intact
        Assert.assertFalse(createNewsPage.isCancelModalDisplayed(),
                "Modal should be closed after clicking 'Continue editing'");
        Assert.assertTrue(createNewsPage.isPageOpened(), 
                "CreateNewsPage should still be opened");

        // Step 11: Verify form data is preserved
        Assert.assertEquals(createNewsPage.getTitleValue(), TEST_TITLE, 
                "Title should be preserved");
        Assert.assertTrue(createNewsPage.getContent().contains(TEST_CONTENT),
                "Content should be preserved");  
    }
}