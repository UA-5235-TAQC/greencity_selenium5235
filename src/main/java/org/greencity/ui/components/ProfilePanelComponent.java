package org.greencity.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Collections;
import java.util.List;

/**
 * Represents the Profile Panel component in the GreenCity UI.
 * Provides access to the user's avatar, name, location, rate,
 * achievements, friends count, eco places, and allows adding a friend.
 */
public class ProfilePanelComponent extends BaseComponent {

    @FindBy(css = "app-user-profile-image img.profile-avatar")
    private WebElement avatar;

    @FindBy(css = "app-profile-header p.name")
    private WebElement name;

    @FindBy(css = "p.location")
    private WebElement location;

    @FindBy(css = "div.rate p")
    private WebElement rate;

    @FindBy(css = "app-users-achievements .achievements-images img")
    private List<WebElement> achievementsList;

    @FindBy(css = "app-users-friends .text-number")
    private WebElement friendsCountLabel;

    @FindBy(css = "app-eco-places .eco-place-list li")
    private List<WebElement> placesList;

    @FindBy(css = "app-users-friends .add-friends a")
    private WebElement addFriendButton;

    public ProfilePanelComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    /** Returns the avatar WebElement */
    public WebElement getAvatar() {
        return avatar;
    }

    /** Returns the full name of the user */
    public String getName() {
        return name.getText().trim();
    }

    /** Returns the location of the user */
    public String getLocation() {
        return location.getText().trim();
    }

    /** Returns the user's rate */
    public Integer getRate() {
        return Integer.parseInt(rate.getText().replaceAll("\\D+",""));
    }

    /** Returns the list of achievement WebElements */
    public List<WebElement> getAchievements() {
        return achievementsList != null ? achievementsList : Collections.emptyList();
    }

    /**
     * Returns the number of friends as displayed in the UI.
     */
    public Integer getFriendsCount() {
        String text = friendsCountLabel.getText().trim();
        return Integer.parseInt(text.split(" ")[0]);
    }

    /** Clicks the add friend button */
    public void addFriend() {
        addFriendButton.click();
    }

    /** Returns the list of favorite eco places */
    public List<WebElement> getFavouritePlaces() {
        return placesList != null ? placesList : Collections.emptyList();
    }
}
