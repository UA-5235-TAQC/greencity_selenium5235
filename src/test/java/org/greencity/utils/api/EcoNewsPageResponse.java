package org.greencity.utils.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcoNewsPageResponse extends org.greencity.api.models.econews.EcoNewsPageResponse {
}