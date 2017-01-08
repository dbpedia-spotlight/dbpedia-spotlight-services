package org.dbpedia.spotlight.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResourceItem {

    @JsonIgnore
    private String context;

    private String surfaceForm;

    private Integer startOffset;

    private Integer endOffset;

    private List<AnnotatedItem> annotatedItems;

}
