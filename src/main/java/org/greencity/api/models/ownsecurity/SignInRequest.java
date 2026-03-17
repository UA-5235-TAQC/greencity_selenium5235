package org.greencity.api.models.ownsecurity;

import lombok.Data;

@Data
public class SignInRequest {
    protected String projectName;
    private String email;
    private String password;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.projectName = "GREENCITY";
    }
}
