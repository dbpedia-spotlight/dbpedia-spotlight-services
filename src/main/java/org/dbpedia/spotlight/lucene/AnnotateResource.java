package org.dbpedia.spotlight.lucene;

import org.dbpedia.spotlight.common.ContentQuery;
import org.dbpedia.spotlight.common.ResourceItem;
import org.dbpedia.spotlight.common.SpotlightResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

public interface AnnotateResource {

    @RequestMapping(value = "lucene/annotate", method = RequestMethod.POST)
    SpotlightResponse annotate(@RequestBody @Valid ContentQuery query);

}
