package org.dbpedia.spotlight.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SpotlightConfiguration {

    @Value("${spotlight.url}")
    private String spotlightURL;

}
