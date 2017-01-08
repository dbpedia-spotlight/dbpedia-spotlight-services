package org.dbpedia.spotlight.lucene;

import org.dbpedia.spotlight.services.Modules;
import org.dbpedia.spotlight.core.FeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = Modules.SPOTLIGHT_LUCENE, configuration = FeignConfiguration.class)
public interface AnnotateClient extends AnnotateResource {

}
