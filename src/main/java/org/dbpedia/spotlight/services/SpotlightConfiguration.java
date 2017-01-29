package org.dbpedia.spotlight.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SpotlightConfiguration {

    public final String URL = "%s/%s/";

    public final Double DEFAULT_CONFIDENCE = 0.5d;

    @Value("${spotlight.url}")
    private String spotlightURL;

    @Value("${json-ld.context}")
    private String jsonContext;
}
