package org.dbpedia.spotlight.rest;

import feign.Feign;
import feign.form.FormEncoder;
import lombok.RequiredArgsConstructor;
import org.dbpedia.spotlight.approach.Model;
import org.dbpedia.spotlight.services.SpotlightConfiguration;
import org.dbpedia.spotlight.services.SpotlightLanguageDetector;
import org.dbpedia.spotlight.services.TextExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

import static org.dbpedia.spotlight.common.Constants.EMPTY;


@Controller(value = "/annotate")
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

        Model model = Feign.builder().encoder(new FormEncoder()).target(Model.class, String.format(configuration.URL, configuration.getSpotlightURL(), language));

        return model.annotate(text.get(), dbpediaTypes.orElse(EMPTY), confidence.orElse(configuration.DEFAULT_CONFIDENCE));

    }


    @Override
    public
    @ResponseBody
    String getJSON(@RequestParam("text") Optional<String> text,
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

    @Override
    public
    @ResponseBody
    String getNIF(@RequestParam("text") Optional<String> text,
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
