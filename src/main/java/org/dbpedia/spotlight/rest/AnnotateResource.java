package org.dbpedia.spotlight.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.core.MediaType;
import java.util.Optional;

public interface AnnotateResource {

    @RequestMapping(value = "/annotate", method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.TEXT_HTML, MediaType.APPLICATION_FORM_URLENCODED},
            produces = MediaType.APPLICATION_JSON)
    String getJSON(@RequestParam("text") Optional<String> text,
                   @RequestParam("url") Optional<String> inUrl,
                   @RequestParam("confidence") Optional<Double> confidence,
                   @RequestParam("support") Optional<Integer> support,
                   @RequestParam("types") Optional<String> dbpediaTypes,
                   @RequestParam("sparql") Optional<String> sparqlQuery,
                   @RequestParam("policy") Optional<String> policy,
                   @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                   @RequestParam("spotter") Optional<String> spotter,
                   @RequestParam("disambiguator") Optional<String> disambiguatorName);

}
