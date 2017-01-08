package org.dbpedia.spotlight.io;

import de.l3s.boilerpipe.extractors.ArticleExtractor;
import org.xml.sax.InputSource;

import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TextExtractor {

    public static final String extract(String url) {

        try {

            URL site = new URL(url);
            InputSource is = new InputSource();

            is.setEncoding(StandardCharsets.UTF_8.name());
            is.setByteStream(site.openStream());

            return ArticleExtractor.INSTANCE.getText(site);
        } catch (Exception e) {
            return "";
        }
    }
}
