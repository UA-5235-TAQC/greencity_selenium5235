package org.greencity.api.testrunners;

import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.utils.api.EcoNewsDtoFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.io.File;

public class CreateNewsBeforeTestRunner extends EcoNewsWithTokenRunner {
    protected long ecoNewsId = 1L;
    protected String imagePath;

    protected void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @BeforeMethod(description = "Create a new EcoNews before each test")
    public void createEcoNews() {
        EcoNewsDtoFactory dtoFactory = new EcoNewsDtoFactory(0);
        var dto = dtoFactory.createDefaultDtoEn();

        EcoNewsRequest request = EcoNewsRequest.builder()
                .title(dto.getTitle())
                .text(dto.getContent())
                .shortInfo(dto.getShortInfo())
                .source(dto.getSource())
                .tags(dto.getTags())
                .build();

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

        Assert.assertEquals(response.getStatusCode(), 201, "New EcoNews should be created");

        EcoNewsResponse created = response.as(EcoNewsResponse.class);
        ecoNewsId = created.getId();
    }
}
