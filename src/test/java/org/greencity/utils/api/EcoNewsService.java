package org.greencity.utils.api;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.greencity.api.clients.EcoNewsClient;
import org.greencity.api.models.econews.EcoNewsPageResponse;
import org.greencity.api.models.econews.EcoNewsQuery;

import static org.greencity.utils.api.ApiTestAssertions.assertOk;

@RequiredArgsConstructor
public class EcoNewsService {

    private final EcoNewsClient client;

    public EcoNewsPageResponse getEcoNewsPage(
            Integer authorId,
            Boolean favorite,
            Integer page,
            Integer size
    ) {
        EcoNewsQuery query = EcoNewsQuery.builder()
                .authorId(authorId)
                .favorite(favorite)
                .page(page)
                .size(size)
                .build();

        Response response = client.getEcoNews(query);
        assertOk(response);

        return response.as(EcoNewsPageResponse.class);
    }

    public EcoNewsPageResponse getFirstPageForAuthor(Integer authorId) {
        return getEcoNewsPage(authorId, false, 0, 20);
    }
}
