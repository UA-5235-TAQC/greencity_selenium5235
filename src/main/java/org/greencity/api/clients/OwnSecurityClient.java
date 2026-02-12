package org.greencity.api.clients;

import io.restassured.response.Response;
import org.greencity.api.models.ownsecurity.SignInRequest;

public class OwnSecurityClient extends BaseApiClient{
    protected final String resourcePath = "/ownSecurity";

    public OwnSecurityClient(String baseUrl) {
        super(baseUrl);
    }

    public  OwnSecurityClient(String baseUrl, String token) {
        super(baseUrl, token);
    }

    public Response signIn(String username, String password){
        return prepareRequest()
                .body(new SignInRequest(username, password))
                .post(this.resourcePath + "/signIn")
                .then()
//                .log().all()
                .extract()
                .response();
    }
}
