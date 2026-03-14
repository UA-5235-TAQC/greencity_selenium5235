package org.greencity.api.testrunners;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.utils.api.EcoNewsDtoFactory;
import org.testng.annotations.BeforeClass;

import java.io.File;

import static org.greencity.utils.api.ApiTestAssertions.assertCreated;

public class CreateNewsBeforeTestRunner extends FirstUserRunner {
    protected long ecoNewsId;
    protected String imagePath;
    protected EcoNewsResponse createdNews;

    protected void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @BeforeClass
    @Description("Create a new EcoNews before each test")
    public void createEcoNews() {
        EcoNewsRequest request = EcoNewsDtoFactory.createNewsEn();

        Response response;
        if (imagePath != null) {
            File image = new File(imagePath);
            if (!image.exists()) {
                throw new RuntimeException("Image file not found: " + image.getAbsolutePath());
            }
            response = ecoNewsClient.postEcoNews(request, imagePath);
        } else {
            response = ecoNewsClient.postEcoNews(request);
        }

        assertCreated(response);

        createdNews = response.as(EcoNewsResponse.class);
        ecoNewsId = createdNews.getId();
    }
}
