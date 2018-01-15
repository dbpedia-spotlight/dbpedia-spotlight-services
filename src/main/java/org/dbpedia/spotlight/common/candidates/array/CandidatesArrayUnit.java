package org.dbpedia.spotlight.common.candidates.array;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dbpedia.spotlight.common.candidates.CandidatesUnit;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CandidatesArrayUnit {

    @JsonProperty("annotation")
    @SerializedName("annotation")
    private CandidatesArrayAnnotation candidatesArrayAnnotation;


    public void parse(CandidatesUnit candidatesUnit) {

        if (candidatesUnit == null) {
            return;
        }

        this.candidatesArrayAnnotation = new CandidatesArrayAnnotation();

        this.candidatesArrayAnnotation.setText(candidatesUnit.getCandidatesAnnotation().getText());
        List<SurfaceArrayForm> surfaceArrayForms = new ArrayList<>(candidatesUnit.getCandidatesAnnotation().getSurfaceForms().size());
        this.candidatesArrayAnnotation.setSurfaceForms(surfaceArrayForms);

        candidatesUnit.getCandidatesAnnotation().getSurfaceForms().stream().forEach(surfaceForm -> {

            SurfaceArrayForm surfaceArrayForm = new SurfaceArrayForm();

            surfaceArrayForm.setName(surfaceForm.getName());
            surfaceArrayForm.setOffSet(surfaceForm.getOffSet());

            List<CandidateArrayResourceItem> candidateArrayResourceItems = new ArrayList<>();
            CandidateArrayResourceItem candidateArrayResourceItem = new CandidateArrayResourceItem();

            candidateArrayResourceItem.setContextualScore(surfaceForm.getCandidateResourceItem().getContextualScore());
            candidateArrayResourceItem.setFinalScore(surfaceForm.getCandidateResourceItem().getFinalScore());
            candidateArrayResourceItem.setLabel(surfaceForm.getCandidateResourceItem().getLabel());
            candidateArrayResourceItem.setPercentageOfSecondRank(surfaceForm.getCandidateResourceItem().getPercentageOfSecondRank());
            candidateArrayResourceItem.setPriorScore(surfaceForm.getCandidateResourceItem().getPriorScore());
            candidateArrayResourceItem.setSupport(surfaceForm.getCandidateResourceItem().getSupport());
            candidateArrayResourceItem.setTypes(surfaceForm.getCandidateResourceItem().getTypes());
            candidateArrayResourceItem.setUri(surfaceForm.getCandidateResourceItem().getUri());

            candidateArrayResourceItems.add(candidateArrayResourceItem);

            surfaceArrayForm.setCandidateArrayResourceItem(candidateArrayResourceItems);

            surfaceArrayForms.add(surfaceArrayForm);

        });
    }
}
