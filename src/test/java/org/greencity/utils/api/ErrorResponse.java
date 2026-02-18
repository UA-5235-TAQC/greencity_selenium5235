package org.greencity.utils.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private String name;
    private String message;
    private String error;
    private Integer status;
    private String path;
    private String timestamp;
}
