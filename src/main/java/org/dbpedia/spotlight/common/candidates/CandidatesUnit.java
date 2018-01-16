package org.dbpedia.spotlight.common.candidates;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CandidatesUnit {

    @JsonProperty("annotation")
    @SerializedName("annotation")
    private CandidatesAnnotation candidatesAnnotation;

}
