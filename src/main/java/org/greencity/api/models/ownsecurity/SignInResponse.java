package org.greencity.api.models.ownsecurity;

import lombok.Data;

@Data
public class SignInResponse {
    private int userId;
    private String accessToken;
    private String refreshToken;
    private String name;
    private boolean ownRegistrations;
}

