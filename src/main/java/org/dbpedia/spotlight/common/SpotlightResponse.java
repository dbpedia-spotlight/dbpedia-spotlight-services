package org.dbpedia.spotlight.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpotlightResponse {

    private String context;

    private List<ResourceItem> resourceItems;

}
