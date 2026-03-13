package org.greencity.utils.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcoNewsPageResponse extends org.greencity.api.models.econews.EcoNewsPageResponse {
}