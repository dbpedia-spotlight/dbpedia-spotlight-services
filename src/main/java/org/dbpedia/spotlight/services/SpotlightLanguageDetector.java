package org.dbpedia.spotlight.services;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpotlightLanguageDetector {

    private final String ENGLISH = "en";

    private LanguageDetector languageDetector = null;


    public SpotlightLanguageDetector() {

        try {
            List<LanguageProfile> profiles = new LanguageProfileReader().readAllBuiltIn();

            this.languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                    .withProfiles(profiles)
                    .build();

        } catch (Exception e) {
        }

    }


    public String language(String text) {

        if (languageDetector == null || text == null) return ENGLISH;

        TextObjectFactory objectFactory = CommonTextObjectFactories.forDetectingShortCleanText();

        TextObject textObject = objectFactory.forText(text);

        Optional<LdLocale> lang = languageDetector.detect(textObject);

        if (lang.isPresent()) {
            return lang.get().getLanguage();
        }

        return ENGLISH;

    }


}
