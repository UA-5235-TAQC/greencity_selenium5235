package org.greencity.api;

import io.restassured.response.Response;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.clients.OwnSecurityClient;
import org.greencity.api.models.econews.EcoNewsRequest;
import org.greencity.ui.enums.EcoNewsTag;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class CreateEcoNewsTest extends ApiTestRunner {
    private String accessToken;
    private EcoNewsClient ecoNewsClient;

    @BeforeClass
    public void prepareTokens() {
        // Використовуємо ваш існуючий OwnSecurityClient
        String userApiUrl = testValueProvider.getBaseGreencityUserAPIUrl();
        OwnSecurityClient ownSecurityClient = new OwnSecurityClient(userApiUrl);

        // Викликаємо ваш метод signIn
        Response response = ownSecurityClient.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword()
        );

        Assert.assertEquals(response.getStatusCode(), 200, "Login failed!");

        // Дістаємо токен (структура JSON у відповіді однакова)
        accessToken = response.jsonPath().getString("accessToken");

        // Далі ініціалізуємо клієнт новин
        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl(), accessToken);
    }

    @Test
    public void createEcoNewsSuccessTest() {
        // Створюємо об'єкт із плоскими полями
        EcoNewsRequest requestBody = EcoNewsRequest.builder()
                .title("Новина про екологію ") // Унікальний заголовок
                .text("Це дуже важливий текст новини, який має бути довшим за 20 символів.")
                .tags(List.of(EcoNewsTag.NEWS.getEn().toLowerCase()))
                .source("https://example.com")
                .shortInfo("Короткий опис")
                .image(null)
                .build();

        Response response = ecoNewsClient.postEcoNews(requestBody);

        Assert.assertEquals(response.getStatusCode(), 201, "Eco News was not created!");

        Integer id = response.jsonPath().get("id");
        String title = response.jsonPath().getString("title");
        String text = response.jsonPath().getString("text");

        Assert.assertNotNull(id, "Response should contain an ID");
    }
}