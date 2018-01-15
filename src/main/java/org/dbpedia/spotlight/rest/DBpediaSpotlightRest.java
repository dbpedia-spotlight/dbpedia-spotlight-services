package org.dbpedia.spotlight.rest;


import de.l3s.boilerpipe.extractors.ArticleExtractor;
import org.dbpedia.spotlight.formats.NIFWrapper;
import org.dbpedia.spotlight.services.SpotlightConfiguration;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public abstract class DBpediaSpotlightRest {


    Optional<String> getCurrentText(Optional<String> text) {
        Optional<String> result = Optional.empty();
        if (text.isPresent()) {
            try {

                String extractedText = ArticleExtractor.INSTANCE.getText(text.get());
                if (extractedText != null && !extractedText.isEmpty()) {
                    result = Optional.of(extractedText);
                } else {
                    result = text;
                }

            } catch (Exception e) {

            }

        }
        return result;
    }


    @NotNull
    NIFWrapper getNifWrapper(SpotlightConfiguration configuration, Optional<String> prefix) {
        NIFWrapper nif;
        if (prefix.isPresent()) {
            nif = new NIFWrapper(configuration, prefix.get());
        } else {
            nif = new NIFWrapper(configuration);
        }
        return nif;
    }


    abstract ResponseEntity<String> html(@RequestParam("text") Optional<String> text,
                                         @RequestParam("url") Optional<String> inUrl,
                                         @RequestParam("confidence") Optional<Double> confidence,
                                         @RequestParam("support") Optional<Integer> support,
                                         @RequestParam("types") Optional<String> dbpediaTypes,
                                         @RequestParam("sparql") Optional<String> sparqlQuery,
                                         @RequestParam("policy") Optional<String> policy,
                                         @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                                         @RequestParam("spotter") Optional<String> spotter,
                                         @RequestParam("disambiguator") Optional<String> disambiguatorName,
                                         @RequestBody Optional<String> fileContent);


    abstract ResponseEntity<String> nif(@RequestParam("text") Optional<String> text,
                                        @RequestParam("url") Optional<String> inUrl,
                                        @RequestParam("confidence") Optional<Double> confidence,
                                        @RequestParam("support") Optional<Integer> support,
                                        @RequestParam("types") Optional<String> dbpediaTypes,
                                        @RequestParam("sparql") Optional<String> sparqlQuery,
                                        @RequestParam("policy") Optional<String> policy,
                                        @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                                        @RequestParam("spotter") Optional<String> spotter,
                                        @RequestParam("disambiguator") Optional<String> disambiguatorName,
                                        @RequestParam("prefix") Optional<String> prefix,
                                        @RequestBody Optional<String> fileContent);


    abstract ResponseEntity<String> triples(@RequestParam("text") Optional<String> text,
                                            @RequestParam("url") Optional<String> inUrl,
                                            @RequestParam("confidence") Optional<Double> confidence,
                                            @RequestParam("support") Optional<Integer> support,
                                            @RequestParam("types") Optional<String> dbpediaTypes,
                                            @RequestParam("sparql") Optional<String> sparqlQuery,
                                            @RequestParam("policy") Optional<String> policy,
                                            @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                                            @RequestParam("spotter") Optional<String> spotter,
                                            @RequestParam("disambiguator") Optional<String> disambiguatorName,
                                            @RequestParam("prefix") Optional<String> prefix,
                                            @RequestBody Optional<String> fileContent);


    abstract ResponseEntity<String> jsonld(@RequestParam("text") Optional<String> text,
                                           @RequestParam("url") Optional<String> inUrl,
                                           @RequestParam("confidence") Optional<Double> confidence,
                                           @RequestParam("support") Optional<Integer> support,
                                           @RequestParam("types") Optional<String> dbpediaTypes,
                                           @RequestParam("sparql") Optional<String> sparqlQuery,
                                           @RequestParam("policy") Optional<String> policy,
                                           @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                                           @RequestParam("spotter") Optional<String> spotter,
                                           @RequestParam("disambiguator") Optional<String> disambiguatorName,
                                           @RequestParam("prefix") Optional<String> prefix,
                                           @RequestBody Optional<String> fileContent);

}
