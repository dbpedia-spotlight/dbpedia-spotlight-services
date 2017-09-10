package org.dbpedia.spotlight.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static org.dbpedia.spotlight.common.Prefixes.DBPEDIA_ONTOLOGY;
import static org.dbpedia.spotlight.common.Prefixes.SCHEMA_ONTOLOGY;

@Getter
@Setter
@NoArgsConstructor
public class AnnotationUnit {

    @JsonProperty("@text")
    @SerializedName("@text")
    private String text;

    @JsonProperty("@confidence")
    @SerializedName("@confidence")
    private String confidence;

    @JsonProperty("@support")
    @SerializedName("@support")
    private String support;

    @JsonProperty("@types")
    @SerializedName("@types")
    private String types;

    @JsonProperty("@sparql")
    @SerializedName("@sparql")
    private String sparql;

    @JsonProperty("@policy")
    @SerializedName("@policy")
    private String policy;

    @JsonProperty("Resources")
    @SerializedName("Resources")
    private List<ResourceItem> resources;

    public Integer endIndex() {
        if (text != null) {
            return text.length();
        }
        return 0;
    }

    public String getTypes() {
        if (types != null && !types.isEmpty()) {
            return types.replace("Http", "http").
                         replace("DBpedia:", DBPEDIA_ONTOLOGY).
                         replace("Schema:", SCHEMA_ONTOLOGY);
        }
        return types;
    }

    public Integer beginIndex() {
        return 1;
    }
}
