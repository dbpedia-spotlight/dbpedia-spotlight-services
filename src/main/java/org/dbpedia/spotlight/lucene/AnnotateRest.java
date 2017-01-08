package org.dbpedia.spotlight.lucene;

import lombok.RequiredArgsConstructor;
import org.dbpedia.spotlight.common.ContentQuery;
import org.dbpedia.spotlight.common.SpotlightResponse;
import org.dbpedia.spotlight.core.FeignConfiguration;
import org.dbpedia.spotlight.services.Modules;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
class AnnotateRest implements AnnotateResource {

    private final AnnotateClient annotateClient;
    private final FeignConfiguration configuration;

    @Override
    public SpotlightResponse annotate(@RequestBody @Valid ContentQuery query) {
        query.init();
        return annotateClient.annotate(query);
    }
}
