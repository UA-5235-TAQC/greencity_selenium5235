package org.greencity.utils.api;

import lombok.Data;

@Data
public class ValidationErrorResponse {
    private String name;
    private String message;
}
