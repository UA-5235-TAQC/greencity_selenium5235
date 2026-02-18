package org.greencity.api.models.common;

import lombok.Data;

@Data
public class ErrorResponse {

    private String message;
    private String error;
    private int status;

}
