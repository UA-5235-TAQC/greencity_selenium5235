package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.greencity.api.models.econews.EcoNewsPageResponse;
import org.greencity.api.models.econews.EcoNewsQuery;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.models.econews.EcoNewsRequest;

import java.util.HashMap;
import java.util.Map;

public class EcoNewsClient extends BaseApiClient {
    protected final String resourcePath = "/eco-news";

    public EcoNewsClient(String baseUrl) {
        super(baseUrl);
    }

    public EcoNewsClient(String baseApiUrl, String token) {
        super(baseApiUrl, token);
    }

    public String getPath(long ecoNewsId) {
        return resourcePath + "/" + ecoNewsId;
    }

    @Step("Post new EcoNews without image")
    public Response postEcoNews(EcoNewsRequest body) {
        return postEcoNews(body, null);
    }

    @Step("Post new EcoNews with image: {imagePath}")
    public Response postEcoNews(EcoNewsRequest body, String imagePath) {
        RequestSpecification request = prepareRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("addEcoNewsDtoRequest", body, "application/json; charset=UTF-8");

        attachFilesToRequest(request, imagePath);

        return execute(request.post(resourcePath));
    }

    @Step("Get EcoNews by ID: {id}")
    public Response getEcoNewsById(Long id) {
        return get(getPath(id));
    }

    @Step("Delete EcoNews by ID: {ecoNewsId}")
    public Response deleteEcoNewsById(long ecoNewsId) {
        return delete(getPath(ecoNewsId));
    }

    @Step("Get EcoNews count by author id: {authorId}")
    public Response getEcoNewsCountByAuthorId(int authorId) {
        return get(resourcePath + "/count", Map.of("author-id", authorId));
    }

    @Step("Get EcoNews with typed query parameters: {query}")
    public Response getEcoNews(EcoNewsQuery query) {
        Map<String, Object> params = new HashMap<>();

        if (query.getAuthorId() != null) params.put("author-id", query.getAuthorId());
        if (query.getFavorite() != null) params.put("favorite", query.getFavorite());
        if (query.getPage() != null) params.put("page", query.getPage());
        if (query.getSize() != null) params.put("size", query.getSize());

        return get(resourcePath, params);
    }

    @Step("Get tags with language: {lang}")
    public Response getTags(String lang) {
        return get(resourcePath + "/tags", Map.of("lang", lang));
    }

    @Step("Add EcoNews with id={ecoNewsId} to favorites")
    public Response addToFavorites(long ecoNewsId) {
        return post(getPath(ecoNewsId) + "/favorites");
    }

    @Step("Remove EcoNews with id={ecoNewsId} from favorites")
    public Response removeFromFavorites(long ecoNewsId) {
        return delete(getPath(ecoNewsId) + "/favorites");
    }

    @Step("Like or remove like from EcoNews by ID: {ecoNewsId}")
    public Response likeEcoNewsById(long ecoNewsId) {
        return post(getPath(ecoNewsId) + "/likes");
    }

    @Step("Count likes on EcoNews by ID: {ecoNewsId}")
    public Response countEcoNewsLikes(long ecoNewsId) {
        return get(getPath(ecoNewsId) + "/likes/count");
    }

    @Step("Update EcoNews by ID: {ecoNewsId} without image")
    public Response updateEcoNewsById(long ecoNewsId,
                                      UpdateEcoNewsDto updateDto) {
        return updateEcoNewsById(ecoNewsId, updateDto, null);
    }

    @Step("Update EcoNews by ID: {ecoNewsId} with image")
    public Response updateEcoNewsById(long ecoNewsId,
                                      UpdateEcoNewsDto updateDto,
                                      String imagePath) {

        RequestSpecification request = prepareMultipartRequest(updateDto);

        if (imagePath != null) {
            attachFilesToRequest(request, imagePath);
        }

        return execute(request.put(getPath(ecoNewsId)));
    }

    @Step("Get EcoNews by ID: {ecoNewsId} with language: {lang}")
    public Response getEcoNewsByIdWithLang(long ecoNewsId, String lang) {
        return get(getPath(ecoNewsId), Map.of("lang", lang));
    }

    @Step("Get EcoNews page")
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

        Response response = getEcoNews(query);

        return response.as(EcoNewsPageResponse.class);
    }

    @Step("Get first EcoNews page for author {authorId}")
    public EcoNewsPageResponse getFirstPageForAuthor(Integer authorId) {
        return getEcoNewsPage(authorId, false, 0, 20);
    }
}
