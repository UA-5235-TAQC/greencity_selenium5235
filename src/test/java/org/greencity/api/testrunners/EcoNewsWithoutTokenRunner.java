package org.greencity.api.testrunners;

import io.qameta.allure.Description;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.models.econews.EcoNewsPageResponse;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.utils.api.EcoNewsService;
import org.testng.annotations.BeforeClass;

public class EcoNewsWithoutTokenRunner extends ApiTestRunner {
    protected EcoNewsClient ecoNewsClient;
    protected EcoNewsService ecoNewsService;
    protected long ecoNewsId;
    protected EcoNewsPageResponse pageResponse;
    protected EcoNewsResponse firstNews;

    @BeforeClass
    @Description("Get first EcoNews before each test")
    public void setUpEcoNewsClient() {
        ecoNewsClient = new EcoNewsClient(testValueProvider.getGreencityAPIUrl());
        ecoNewsService = new EcoNewsService(ecoNewsClient);

        pageResponse = ecoNewsService
                .getFirstPageForAuthor(testValueProvider.getUserId());

        firstNews = pageResponse.getPage().getFirst();
        ecoNewsId = firstNews.getId();
    }
}
