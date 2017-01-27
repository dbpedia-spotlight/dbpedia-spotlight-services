package org.dbpedia.spotlight.rest;

import feign.Feign;
import feign.form.FormEncoder;
import lombok.RequiredArgsConstructor;
import org.dbpedia.spotlight.approach.Model;
import org.dbpedia.spotlight.services.SpotlightConfiguration;
import org.dbpedia.spotlight.services.SpotlightLanguageDetector;
import org.dbpedia.spotlight.services.TextExtractor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.dbpedia.spotlight.common.Constants.DEFAULT_CONFIDENCE;
import static org.dbpedia.spotlight.common.Constants.EMPTY;
import static org.dbpedia.spotlight.common.Constants.URL;

@RestController
@RequiredArgsConstructor
public class AnnotateRest implements AnnotateResource {

    private final SpotlightLanguageDetector languageDetector;

    private final TextExtractor textExtractor;

    private final SpotlightConfiguration configuration;

    private String serviceRequest(@RequestParam("text") Optional<String> text,
                                  @RequestParam("url") Optional<String> inUrl,
                                  @RequestParam("confidence") Optional<Double> confidence,
                                  @RequestParam("support") Optional<Integer> support,
                                  @RequestParam("types") Optional<String> dbpediaTypes,
                                  @RequestParam("sparql") Optional<String> sparqlQuery,
                                  @RequestParam("policy") Optional<String> policy,
                                  @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                                  @RequestParam("spotter") Optional<String> spotter,
                                  @RequestParam("disambiguator") Optional<String> disambiguatorName) {

        if (inUrl.isPresent()) {
            text = Optional.of(textExtractor.extract(inUrl.get()));
        }

        String language = languageDetector.language(text.get());

        Model model = Feign.builder().encoder(new FormEncoder()).target(Model.class, String.format(URL, configuration.getSpotlightURL(), language));

        return model.annotate(text.get(), dbpediaTypes.orElse(EMPTY), confidence.orElse(DEFAULT_CONFIDENCE));

    }


    @Override
    public String getJSON(@RequestParam("text") Optional<String> text,
                          @RequestParam("url") Optional<String> inUrl,
                          @RequestParam("confidence") Optional<Double> confidence,
                          @RequestParam("support") Optional<Integer> support,
                          @RequestParam("types") Optional<String> dbpediaTypes,
                          @RequestParam("sparql") Optional<String> sparqlQuery,
                          @RequestParam("policy") Optional<String> policy,
                          @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                          @RequestParam("spotter") Optional<String> spotter,
                          @RequestParam("disambiguator") Optional<String> disambiguatorName) {
        return serviceRequest(text, inUrl, confidence, support, dbpediaTypes,
                sparqlQuery, policy, coreferenceResolution, spotter, disambiguatorName);
    }


}
