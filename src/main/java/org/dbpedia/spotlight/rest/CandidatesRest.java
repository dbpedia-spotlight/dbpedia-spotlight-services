package org.dbpedia.spotlight.rest;

import feign.Feign;
import feign.form.FormEncoder;
import lombok.RequiredArgsConstructor;
import org.dbpedia.spotlight.approach.Candidates;
import org.dbpedia.spotlight.common.SemanticMediaType;
import org.dbpedia.spotlight.common.candidates.CandidatesUnit;
import org.dbpedia.spotlight.formats.NIFWrapper;
import org.dbpedia.spotlight.services.SpotlightConfiguration;
import org.dbpedia.spotlight.services.SpotlightLanguageDetector;
import org.dbpedia.spotlight.services.TextExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.Optional;

import static org.dbpedia.spotlight.common.Constants.EMPTY;
import static org.dbpedia.spotlight.common.SemanticMediaType.*;
import static org.dbpedia.spotlight.formats.JSON.toCandidates;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("candidates")
@RequiredArgsConstructor
public class CandidatesRest extends DBpediaSpotlightRest {

    private final SpotlightLanguageDetector languageDetector;

    private final TextExtractor textExtractor;

    private final SpotlightConfiguration configuration;

    private String serviceRequest(Optional<String> text,
                                  Optional<String> inUrl,
                                  Optional<Double> confidence,
                                  Optional<String> dbpediaTypes,
                                  String outputFormat) {

        Optional<String> currentText = getCurrentText(text);

        if (inUrl.isPresent()) {
            currentText = Optional.of(textExtractor.extract(inUrl.get()));
        }

        String language = languageDetector.language(currentText.get());

        Candidates candidates = Feign.builder().encoder(new FormEncoder()).target(Candidates.class,
                String.format(configuration.getURL(), configuration.getSpotlightURL(), language));


        if (MediaType.TEXT_HTML.equalsIgnoreCase(outputFormat)) {
            return candidates.html(currentText.get(), dbpediaTypes.orElse(EMPTY),
                    confidence.orElse(configuration.getDefaultConfidence()));


        }

        return candidates.candidates(currentText.get(), dbpediaTypes.orElse(EMPTY),
                confidence.orElse(configuration.getDefaultConfidence()));

    }


    private String getSemanticFormats(Optional<String> text,
                                      Optional<String> inUrl,
                                      Optional<Double> confidence,
                                      Optional<String> dbpediaTypes,
                                      Optional<String> prefix,
                                      String outputFormat) {

        NIFWrapper nif = getNifWrapper(configuration, prefix);

        CandidatesUnit candidatesUnit = toCandidates(serviceRequest(text, inUrl, confidence, dbpediaTypes, outputFormat));
        nif.entity(candidatesUnit);

        return nif.getNIF(outputFormat);
    }


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.WILDCARD},
            produces = MediaType.TEXT_HTML)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> html(@RequestParam("text") Optional<String> text,
                                       @RequestParam("url") Optional<String> inUrl,
                                       @RequestParam("confidence") Optional<Double> confidence,
                                       @RequestParam("support") Optional<Integer> support,
                                       @RequestParam("types") Optional<String> dbpediaTypes,
                                       @RequestParam("sparql") Optional<String> sparqlQuery,
                                       @RequestParam("policy") Optional<String> policy,
                                       @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                                       @RequestParam("spotter") Optional<String> spotter,
                                       @RequestParam("disambiguator") Optional<String> disambiguatorName,
                                       @RequestBody Optional<String> fileContent) {

        if (!text.isPresent() && fileContent.isPresent()) {
            text = fileContent;
        }

        String result = serviceRequest(text, inUrl, confidence, dbpediaTypes, MediaType.TEXT_HTML);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.WILDCARD},
            produces = MediaType.APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CandidatesUnit json(@RequestParam("text") Optional<String> text,
                               @RequestParam("url") Optional<String> inUrl,
                               @RequestParam("confidence") Optional<Double> confidence,
                               @RequestParam("support") Optional<Integer> support,
                               @RequestParam("types") Optional<String> dbpediaTypes,
                               @RequestParam("sparql") Optional<String> sparqlQuery,
                               @RequestParam("policy") Optional<String> policy,
                               @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                               @RequestParam("spotter") Optional<String> spotter,
                               @RequestParam("disambiguator") Optional<String> disambiguatorName,
                               @RequestBody Optional<String> fileContent) {

        if (!text.isPresent() && fileContent.isPresent()) {
            text = fileContent;
        }

        return toCandidates(serviceRequest(text, inUrl, confidence, dbpediaTypes, MediaType.APPLICATION_JSON));


    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.WILDCARD},
            produces = SemanticMediaType.TEXT_TURTLE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> nif(@RequestParam("text") Optional<String> text,
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
                                      @RequestBody Optional<String> fileContent) {

        if (!text.isPresent() && fileContent.isPresent()) {
            text = fileContent;
        }

        String result = getSemanticFormats(text, inUrl, confidence, dbpediaTypes, prefix, TEXT_TURTLE);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.WILDCARD},
            produces = SemanticMediaType.APPLICATION_N_TRIPLES)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> triples(@RequestParam("text") Optional<String> text,
                                          @RequestParam("url") Optional<String> inUrl,
                                          @RequestParam("confidence") Optional<Double> confidence,
                                          @RequestParam("support") Optional<Integer> support,
                                          @RequestParam("types") Optional<String> dbpediaTypes,
                                          @RequestParam("sparql") Optional<String> sparqlQuery,
                                          @RequestParam("policy") Optional<String> policy,
                                          @RequestParam("coreferenceResolution") Optional<Boolean> coreference,
                                          @RequestParam("spotter") Optional<String> spotter,
                                          @RequestParam("disambiguator") Optional<String> disambiguatorName,
                                          @RequestParam("prefix") Optional<String> prefix,
                                          @RequestBody Optional<String> fileContent) {
        if (!text.isPresent() && fileContent.isPresent()) {
            text = fileContent;
        }

        String result = getSemanticFormats(text, inUrl, confidence, dbpediaTypes, prefix, APPLICATION_N_TRIPLES);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},
            consumes = {MediaType.WILDCARD},
            produces = SemanticMediaType.APPLICATION_LD_JSON)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> jsonld(@RequestParam("text") Optional<String> text,
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
                                         @RequestBody Optional<String> fileContent) {

        if (!text.isPresent() && fileContent.isPresent()) {
            text = fileContent;
        }

        String result = getSemanticFormats(text, inUrl, confidence, dbpediaTypes, prefix, APPLICATION_LD_JSON);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

}
