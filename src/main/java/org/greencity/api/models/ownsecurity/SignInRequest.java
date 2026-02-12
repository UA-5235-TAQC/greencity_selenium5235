package org.greencity.api.models.ownsecurity;

import lombok.Data;

@Data
public class SignInRequest {
    protected String projectName;
    private String email;
    private String password;

    public SignInRequest(String username, String password) {
        this.email = username;
        this.password = password;
        this.projectName = "GREENCITY";
    }
}


