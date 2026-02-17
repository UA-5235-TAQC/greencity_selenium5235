package org.greencity.utils.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcoNewsPageResponse {

    private List<Page> page;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Page {
        private long id;
    }
}