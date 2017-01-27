package org.dbpedia.spotlight.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceItem {

    @JsonProperty("@URI")
    private String uri;

    @JsonProperty("@support")
    private String support;

    @JsonProperty("@types")
    private String types;

    @JsonProperty("@surfaceForm")
    private String surfaceForm;

    @JsonProperty("@offset")
    private String offSet;

    @JsonProperty("@similarityScore")
    private String similarityScore;

    @JsonProperty("@percentageOfSecondRan")
    private String percentageOfSecondRank;

}
