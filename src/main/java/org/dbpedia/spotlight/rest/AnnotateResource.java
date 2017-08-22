package org.dbpedia.spotlight.rest;


import org.dbpedia.spotlight.common.AnnotationUnit;
import org.dbpedia.spotlight.common.SemanticMediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.ws.rs.core.MediaType;
import java.util.Optional;

public interface AnnotateResource {


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.TEXT_HTML, MediaType.APPLICATION_FORM_URLENCODED},
            produces = MediaType.TEXT_HTML)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> html(@RequestParam("text") Optional<String> text,
                                @RequestParam("url") Optional<String> inUrl,
                                @RequestParam("confidence") Optional<Double> confidence,
                                @RequestParam("support") Optional<Integer> support,
                                @RequestParam("types") Optional<String> dbpediaTypes,
                                @RequestParam("sparql") Optional<String> sparqlQuery,
                                @RequestParam("policy") Optional<String> policy,
                                @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                                @RequestParam("spotter") Optional<String> spotter,
                                @RequestParam("disambiguator") Optional<String> disambiguatorName);


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.TEXT_HTML, MediaType.APPLICATION_FORM_URLENCODED},
            produces = MediaType.APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    AnnotationUnit json(@RequestParam("text") Optional<String> text,
                        @RequestParam("url") Optional<String> inUrl,
                        @RequestParam("confidence") Optional<Double> confidence,
                        @RequestParam("support") Optional<Integer> support,
                        @RequestParam("types") Optional<String> dbpediaTypes,
                        @RequestParam("sparql") Optional<String> sparqlQuery,
                        @RequestParam("policy") Optional<String> policy,
                        @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                        @RequestParam("spotter") Optional<String> spotter,
                        @RequestParam("disambiguator") Optional<String> disambiguatorName);


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.TEXT_HTML, MediaType.APPLICATION_FORM_URLENCODED},
            produces = SemanticMediaType.TEXT_TURTLE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> nif(@RequestParam("text") Optional<String> text,
                               @RequestParam("url") Optional<String> inUrl,
                               @RequestParam("confidence") Optional<Double> confidence,
                               @RequestParam("support") Optional<Integer> support,
                               @RequestParam("types") Optional<String> dbpediaTypes,
                               @RequestParam("sparql") Optional<String> sparqlQuery,
                               @RequestParam("policy") Optional<String> policy,
                               @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                               @RequestParam("spotter") Optional<String> spotter,
                               @RequestParam("disambiguator") Optional<String> disambiguatorName,
                               @RequestParam("prefix") Optional<String> prefix);


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.TEXT_HTML, MediaType.APPLICATION_FORM_URLENCODED},
            produces = SemanticMediaType.APPLICATION_N_TRIPLES)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> triples(@RequestParam("text") Optional<String> text,
                                   @RequestParam("url") Optional<String> inUrl,
                                   @RequestParam("confidence") Optional<Double> confidence,
                                   @RequestParam("support") Optional<Integer> support,
                                   @RequestParam("types") Optional<String> dbpediaTypes,
                                   @RequestParam("sparql") Optional<String> sparqlQuery,
                                   @RequestParam("policy") Optional<String> policy,
                                   @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                                   @RequestParam("spotter") Optional<String> spotter,
                                   @RequestParam("disambiguator") Optional<String> disambiguatorName,
                                   @RequestParam("prefix") Optional<String> prefix);

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.TEXT_HTML, MediaType.APPLICATION_FORM_URLENCODED},
            produces = SemanticMediaType.APPLICATION_LD_JSON)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> jsonld(@RequestParam("text") Optional<String> text,
                                  @RequestParam("url") Optional<String> inUrl,
                                  @RequestParam("confidence") Optional<Double> confidence,
                                  @RequestParam("support") Optional<Integer> support,
                                  @RequestParam("types") Optional<String> dbpediaTypes,
                                  @RequestParam("sparql") Optional<String> sparqlQuery,
                                  @RequestParam("policy") Optional<String> policy,
                                  @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                                  @RequestParam("spotter") Optional<String> spotter,
                                  @RequestParam("disambiguator") Optional<String> disambiguatorName,
                                  @RequestParam("prefix") Optional<String> prefix);

}
