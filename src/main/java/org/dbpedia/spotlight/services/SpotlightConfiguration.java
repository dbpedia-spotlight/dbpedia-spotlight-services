package org.dbpedia.spotlight.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
public class SpotlightConfiguration {

    private final String URL = "%s/%s/";

    private final Double defaultConfidence = 0.5d;

    @Value("${spotlight.url}")
    private String spotlightURL;

    @Value("${spotlight.languages}")
    private String enabledLanguages;

    public boolean hasLanguage(String language) {
        if ( language != null) {
            return enabledLanguages.contains(language);
        }

        return false;
    }

    @Value("${json-ld.context}")
    private String jsonContext;
}
