package org.dbpedia.spotlight.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AnnotationUnit {

    @JsonProperty("@text")
    private String text;

    @JsonProperty("@confidence")
    private String confidence;

    @JsonProperty("@support")
    private String support;

    @JsonProperty("@types")
    private String types;

    @JsonProperty("@sparql")
    private String sparql;

    @JsonProperty("@policy")
    private String policy;

    @JsonProperty("Resources")
    private List<ResourceItem> resources;
}
