package org.dbpedia.spotlight.common.candidates;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.dbpedia.spotlight.common.Constants.COMMA;

@Getter
@Setter
@NoArgsConstructor
public class CandidateResourceItem {

    @JsonProperty("@uri")
    @SerializedName("@uri")
    private String uri;

    @JsonProperty("@label")
    @SerializedName("@label")
    private String label;

    @JsonProperty("@contextualScore")
    @SerializedName("@contextualScore")
    private Double contextualScore;

    @JsonProperty("@percentageOfSecondRank")
    @SerializedName("@percentageOfSecondRank")
    private Double percentageOfSecondRank;

    @JsonProperty("@support")
    @SerializedName("@support")
    private String support;

    @JsonProperty("@priorScore")
    @SerializedName("@priorScore")
    private Double priorScore;

    @JsonProperty("@finalScore")
    @SerializedName("@finalScore")
    private Double finalScore;

    @JsonProperty("@types")
    @SerializedName("@types")
    private String types;

    public List<String> typesList() {

        if (types != null && !types.isEmpty()) {
            return Arrays.asList(types.split(COMMA));
        }

        return new ArrayList<>();
    }

}
