package org.dbpedia.spotlight.services;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import lombok.Getter;
import lombok.Setter;
import org.dbpedia.spotlight.common.Constants;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@Component
public class TextExtractor {

    public String extract(String url) {

        try {

            URL site = new URL(url);
            InputSource is = new InputSource();

            is.setEncoding(StandardCharsets.UTF_8.name());
            is.setByteStream(site.openStream());

            return ArticleExtractor.INSTANCE.getText(site);
        } catch (IOException e) {
            return Constants.EMPTY;
        } catch (BoilerpipeProcessingException e1) {
            return Constants.EMPTY;
        }
    }
}
