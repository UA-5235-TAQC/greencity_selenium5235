package org.greencity.api.models;

import lombok.Data;

@Data
public class AuthorResponse {
    private int id;
    private String name;
    private String profilePicturePath = "";
}
