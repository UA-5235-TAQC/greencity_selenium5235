package org.greencity.api.clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.greencity.api.models.econews.EcoNewsQuery;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.models.econews.EcoNewsRequest;

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
        String resourcePath = "/eco-news/";
        return resourcePath + ecoNewsId;
    }

    @Step("Get EcoNews by ID: {ecoNewsId}")
    public Response getEcoNewsById(long ecoNewsId) {
        return get(getPath(ecoNewsId));
    }

    @Step("Get EcoNews by ID: {ecoNewsId} with language: {lang}")
    public Response getEcoNewsByIdWithLang(long ecoNewsId, String lang) {
        return execute(
                prepareRequest()
                        .queryParam("lang", lang)
                        .get(getPath(ecoNewsId))
        );
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

    @Step("Delete EcoNews by ID: {ecoNewsId}")
    public Response deleteEcoNewsById(long ecoNewsId) {
        return delete(getPath(ecoNewsId));
    }

    @Step("Get EcoNews with query parameters: {queryParams}")
    public Response getEcoNews(Map<String, ?> queryParams) {
        return execute(prepareRequest()
                .queryParams(queryParams)
                .get(resourcePath));
    }

    @Step("Post new EcoNews without image")
    public Response postEcoNews(EcoNewsRequest body) {
        return execute(prepareRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("addEcoNewsDtoRequest", body, "application/json; charset=UTF-8")
                .post(resourcePath));
    }

    @Step("Post new EcoNews {body} with image: {imagePath}")
    public Response postEcoNews(EcoNewsRequest body, String imagePath) {
        RequestSpecification request = prepareRequest().contentType(ContentType.MULTIPART).multiPart("addEcoNewsDtoRequest", body, "application/json; charset=UTF-8");
        attachFilesToRequest(request, imagePath);
        return execute(request.post(resourcePath));
    }

    @Step("Get EcoNews count by author id: {authorId}")
    public Response getEcoNewsCountByAuthorId(int authorId) {
        return execute(prepareRequest()
                .queryParam("author-id", authorId)
                .get(resourcePath + "/count"));
    }

    @Step("Get EcoNews with typed query parameters: {query}")
    public Response getEcoNews(EcoNewsQuery query) {
        RequestSpecification request = prepareRequest();

        if (query.getAuthorId() != null) request.queryParam("author-id", query.getAuthorId());
        if (query.getFavorite() != null) request.queryParam("favorite", query.getFavorite());
        if (query.getPage() != null) request.queryParam("page", query.getPage());
        if (query.getSize() != null) request.queryParam("size", query.getSize());

        return execute(request.get(resourcePath));
    }

    public Response getTags(String lang) {
        return execute(prepareRequest()
                .queryParam("lang", lang)
                .get(resourcePath + "/tags"));
    }

    @Step("Add EcoNews with id={ecoNewsId} to favorites")
    public Response addToFavorites(long ecoNewsId) {
        return execute(prepareRequest()
                .post(resourcePath + "/" + ecoNewsId + "/favorites"));
    }

    @Step("Remove EcoNews with id={ecoNewsId} from favorites")
    public Response removeFromFavorites(long ecoNewsId) {
        return execute(prepareRequest()
                .delete(resourcePath + "/" + ecoNewsId + "/favorites"));
    }
}
