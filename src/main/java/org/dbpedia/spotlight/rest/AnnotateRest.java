package org.dbpedia.spotlight.rest;

import de.l3s.boilerpipe.extractors.ArticleExtractor;
import feign.Feign;
import feign.form.FormEncoder;
import lombok.RequiredArgsConstructor;
import org.dbpedia.spotlight.approach.Model;
import org.dbpedia.spotlight.common.AnnotationUnit;
import org.dbpedia.spotlight.formats.NIFWrapper;
import org.dbpedia.spotlight.services.SpotlightConfiguration;
import org.dbpedia.spotlight.services.SpotlightLanguageDetector;
import org.dbpedia.spotlight.services.TextExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;
import java.util.Optional;

import static org.dbpedia.spotlight.common.Constants.EMPTY;
import static org.dbpedia.spotlight.common.SemanticMediaType.APPLICATION_LD_JSON;
import static org.dbpedia.spotlight.common.SemanticMediaType.APPLICATION_N_TRIPLES;
import static org.dbpedia.spotlight.common.SemanticMediaType.TEXT_TURTLE;
import static org.dbpedia.spotlight.formats.JSON.to;

@CrossOrigin(origins = "*")
@Controller(value = "/annotate")
@RequiredArgsConstructor
public class AnnotateRest implements AnnotateResource {

    private final SpotlightLanguageDetector languageDetector;

    private final TextExtractor textExtractor;

    private final SpotlightConfiguration configuration;

    private String serviceRequest(Optional<String> text,
                                  Optional<String> inUrl,
                                  Optional<Double> confidence,
                                  Optional<String> dbpediaTypes,
                                  String outputFormat) {

        Optional<String> currentText = Optional.empty();

        if (text.isPresent()) {
            try {
                currentText = Optional.of(ArticleExtractor.INSTANCE.getText(text.get()));
            } catch (Exception e) {

            }

        }

        if (inUrl.isPresent()) {
            currentText = Optional.of(textExtractor.extract(inUrl.get()));
        }

        String language = languageDetector.language(currentText.get());

        Model model = Feign.builder().encoder(new FormEncoder()).target(Model.class,
                String.format(configuration.getURL(), configuration.getSpotlightURL(), language));


        if (MediaType.TEXT_HTML.equalsIgnoreCase(outputFormat)) {
            return model.html(currentText.get(), dbpediaTypes.orElse(EMPTY),
                    confidence.orElse(configuration.getDefaultConfidence()));


        }

        return model.annotate(currentText.get(), dbpediaTypes.orElse(EMPTY),
                confidence.orElse(configuration.getDefaultConfidence()));

    }

    private String getSemanticFormats(Optional<String> text,
                                      Optional<String> inUrl,
                                      Optional<Double> confidence,
                                      Optional<String> dbpediaTypes,
                                      Optional<String> prefix,
                                      String outputFormat) {
        NIFWrapper nif;

        if (prefix.isPresent()) {
            nif = new NIFWrapper(configuration, prefix.get());
        } else {
            nif = new NIFWrapper(configuration);
        }

        AnnotationUnit annotationUnit = to(serviceRequest(text, inUrl, confidence, dbpediaTypes, outputFormat));
        nif.entity(annotationUnit);

        return nif.getNIF(outputFormat);
    }


    @Override
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
                                       @RequestBody  String fileContent) {

        if (!text.isPresent() && fileContent != null) {
            text = Optional.of(fileContent);
        }

        String result = serviceRequest(text, inUrl, confidence, dbpediaTypes, MediaType.TEXT_HTML);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @Override
    @ResponseBody
    public AnnotationUnit json(@RequestParam("text") Optional<String> text,
                               @RequestParam("url") Optional<String> inUrl,
                               @RequestParam("confidence") Optional<Double> confidence,
                               @RequestParam("support") Optional<Integer> support,
                               @RequestParam("types") Optional<String> dbpediaTypes,
                               @RequestParam("sparql") Optional<String> sparqlQuery,
                               @RequestParam("policy") Optional<String> policy,
                               @RequestParam("coreferenceResolution") Optional<Boolean> coreferenceResolution,
                               @RequestParam("spotter") Optional<String> spotter,
                               @RequestParam("disambiguator") Optional<String> disambiguatorName,
                               @RequestBody  String fileContent) {

        if (!text.isPresent() && fileContent != null) {
            text = Optional.of(fileContent);
        }

        return to(serviceRequest(text, inUrl, confidence, dbpediaTypes, MediaType.APPLICATION_JSON));


    }

    @Override
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
                                      @RequestBody  String fileContent) {

        if (!text.isPresent() && fileContent != null) {
            text = Optional.of(fileContent);
        }

        String result = getSemanticFormats(text, inUrl, confidence, dbpediaTypes, prefix, TEXT_TURTLE);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }


    @Override
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
                                          @RequestBody  String fileContent) {
        if (!text.isPresent() && fileContent != null) {
            text = Optional.of(fileContent);
        }

        String result = getSemanticFormats(text, inUrl, confidence, dbpediaTypes, prefix, APPLICATION_N_TRIPLES);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @Override
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
                                         @RequestBody  String fileContent) {

        if (!text.isPresent() && fileContent != null) {
            text = Optional.of(fileContent);
        }

        String result = getSemanticFormats(text, inUrl, confidence, dbpediaTypes, prefix, APPLICATION_LD_JSON);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

}
