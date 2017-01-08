package org.dbpedia.spotlight.common;

import lombok.Getter;
import lombok.Setter;
import org.dbpedia.spotlight.io.TextExtractor;

import java.util.List;

@Getter
@Setter
public class ContentQuery {

    private List<String> datasets;

    private String language;

    private List<String> types;

    private String text;

    private Integer topN = 1;

    private String url;

    public void init() {
        if (this.getUrl() != null)
            this.setText(TextExtractor.extract(this.getUrl()));
    }

}
