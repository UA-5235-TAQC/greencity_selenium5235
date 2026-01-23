package org.greencity.ui.components;

import org.greencity.ui.pages.*;
import org.greencity.ui.pages.MySpace.MySpaceBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * FooterComponent represents the footer section of the GreenCity application.
 * It provides access to navigation links, social media icons,
 * and informational footer text.
 */
public class FooterComponent extends BaseComponent {
    @FindBy(css = "a[href='#/greenCity'] img.logo")
    protected WebElement logoLink;
    @FindBy(xpath = "//a[contains(@href, '#/greenCity/news')]")
    protected WebElement newsLink;
    @FindBy(xpath = "//a[contains(@href, '#/greenCity/events')]")
    protected WebElement eventsLink;
    @FindBy(xpath = "//a[contains(@href, '#/greenCity/places')]")
    protected WebElement placesLink;
    @FindBy(xpath = "//a[contains(@href, '#/greenCity/about')]")
    protected WebElement aboutLink;
    @FindBy(xpath = "//a[contains(@href, '#/greenCity/profile')]")
    protected WebElement mySpaceLink;
    @FindBy(xpath = "//a[contains(@href, '#/ubs')]")
    protected WebElement ubsLink;
    @FindBy(css = ".footer_social-link img[src*='twitter']")
    protected WebElement twitterIcon;
    @FindBy(css = ".footer_social-link img[src*='linkedin']")
    protected WebElement linkedinIcon;
    @FindBy(css = ".footer_social-link img[src*='facebook']")
    protected WebElement facebookIcon;
    @FindBy(css = ".footer_social-link img[src*='instagram']")
    protected WebElement instagramIcon;
    @FindBy(css = ".footer_social-link img[src*='youtube']")
    protected WebElement youtubeIcon;
    @FindBy(css = ".footer_follow-us span")
    protected WebElement followUsText;
    @FindBy(id = "copyright-label")
    protected WebElement copyrightLabel;

    public FooterComponent(WebDriver driver) {
        super(driver);
    }

    public FooterComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    /**
     * Clicks on the footer logo.
     *
     * @return HomePage instance after navigation
     */
    public HomePage clickLogoLink() {
        logoLink.click();
        return new HomePage(driver);
    }

    /**
     * Clicks on the "Eco news" link in the footer.
     *
     * @return EcoNewsPage instance after navigation
     */
    public EcoNewsPage clickNewsLink() {
        newsLink.click();
        return new EcoNewsPage(driver);
    }

    /**
     * Clicks on the "Events" link in the footer.
     *
     * @return EventsPage instance after navigation
     */
    public EventsPage clickEventsLink() {
        eventsLink.click();
        return new EventsPage(driver);
    }

    /**
     * Clicks on the "Places" link in the footer.
     *
     * @return PlacesPage instance after navigation
     */
    public PlacesPage clickPlacesLink() {
        placesLink.click();
        return new PlacesPage(driver);
    }

    /**
     * Clicks on the "About Us" link in the footer.
     *
     * @return AboutUsPage instance after navigation
     */
    public AboutUsPage clickAboutLink() {
        aboutLink.click();
        return new AboutUsPage(driver);
    }

    /**
     * Clicks on the "My Space" link in the footer.
     *
     * @return MySpaceBasePage instance after navigation
     */
    public MySpaceBasePage clickMySpaceLink() {
        mySpaceLink.click();
        return new MySpaceBasePage(driver);
    }

    /**
     * Clicks on the "UBS Courier" link in the footer.
     *
     * @return UbsCourierPage instance after navigation
     */
    public UbsCourierPage clickUbsLink() {
        ubsLink.click();
        return new UbsCourierPage(driver);
    }

    /**
     * Clicks on the Twitter social media icon.
     */
    public void clickTwitterIcon() {
        twitterIcon.click();
    }

    /**
     * Clicks on the LinkedIn social media icon.
     */
    public void clickLinkedinIcon() {
        linkedinIcon.click();
    }

    /**
     * Clicks on the Facebook social media icon.
     */
    public void clickFacebookIcon() {
        facebookIcon.click();
    }

    /**
     * Clicks on the Instagram social media icon.
     */
    public void clickInstagramIcon() {
        instagramIcon.click();
    }

    /**
     * Clicks on the YouTube social media icon.
     */
    public void clickYouTubeIcon() {
        youtubeIcon.click();
    }

    /**
     * Retrieves the "Follow us" text displayed in the footer.
     *
     * @return trimmed "Follow us" text
     */
    public String getFollowUsText() {
        return followUsText.getText().trim();
    }

    /**
     * Retrieves the copyright text displayed in the footer.
     *
     * @return trimmed copyright text
     */
    public String getCopyrightText() {
        return copyrightLabel.getText().trim();
    }
}
