package org.dbpedia.spotlight.common.candidates.array;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CandidatesArrayAnnotation {

    @JsonProperty("@text")
    @SerializedName("@text")
    private String text;

    @JsonProperty("surfaceForm")
    @SerializedName("surfaceForm")
    private List<SurfaceArrayForm> surfaceForms;

    public Boolean hasSurfaceForms() {
        return surfaceForms != null && !surfaceForms.isEmpty();
    }
}
