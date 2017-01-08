package org.dbpedia.spotlight.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnnotatedItem {

    private String uri;

    private String dataset;

    private String language;

    private String uriCount;

    private Double provenance;

    private List<String> types;

}
