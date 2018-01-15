package org.dbpedia.spotlight.common.candidates;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SurfaceForm {

    @JsonProperty("@name")
    @SerializedName("@name")
    private String name;

    @JsonProperty("@offset")
    @SerializedName("@offset")
    private String offSet;

    @JsonProperty("resource")
    @SerializedName("resource")
    private CandidateResourceItem candidateResourceItem;

    public Integer beginIndex() {
        try {
            return Integer.valueOf(offSet);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Integer endIndex() {
        if (name != null) {
            return beginIndex() + name.length();
        }

        return 0;
    }
}
