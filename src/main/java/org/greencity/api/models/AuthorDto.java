package org.greencity.api.models;

import lombok.Data;

@Data
public class AuthorDto {
    private int id;
    private String name;
    private String profilePicturePath;
}
