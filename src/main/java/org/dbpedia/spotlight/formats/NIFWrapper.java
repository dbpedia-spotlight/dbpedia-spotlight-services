package org.dbpedia.spotlight.formats;

import org.dbpedia.spotlight.common.AnnotationUnit;
import org.dbpedia.spotlight.common.SemanticMediaType;
import org.dbpedia.spotlight.services.SpotlightConfiguration;
import org.nlp2rdf.NIF;
import org.nlp2rdf.bean.NIFBean;
import org.nlp2rdf.bean.NIFType;
import org.nlp2rdf.nif21.impl.NIF21;
import org.nlp2rdf.parser.NIFParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.dbpedia.spotlight.common.Constants.SLASH;

public class NIFWrapper {

    @Autowired
    private SpotlightConfiguration configuration;

    private List<NIFBean> entities = new ArrayList<>();

    private NIFBean beanContext;

    private String baseURI;

    public NIFWrapper(String baseURI) {

        this.baseURI = baseURI;

        formatBaseURI();
    }

    public void context(String mention) {

        int beginIndex = 0;
        int endIndex = mention.length();

        NIFBean.NIFBeanBuilder contextBuilder = new NIFBean.NIFBeanBuilder();

        contextBuilder.context(baseURI, beginIndex, endIndex).mention(mention).nifType(NIFType.CONTEXT);

        beanContext = new NIFBean(contextBuilder);

    }

    private void formatBaseURI() {
        if (baseURI != null && !baseURI.isEmpty() &&
                !SLASH.equals(baseURI.substring(baseURI.length() - 1))) {
            baseURI = baseURI.concat(SLASH);
        }
    }

    public void entity(AnnotationUnit result) {

        NIFBean.NIFBeanBuilder entity = new NIFBean.NIFBeanBuilder();

        entity.annotator(configuration.getSpotlightURL()).beginIndex(result.beginIndex()).endIndex(result.endIndex())
                .mention(result.getText()).context(baseURI, result.beginIndex(), result.endIndex());


        entities.add(new NIFBean(entity));

    }

    public String getNIF(String outputFormat, NIFParser parser) {

        List<NIFBean> entitiesToProcess = new ArrayList<>(entities.size());

        entitiesToProcess.add(beanContext);
        entitiesToProcess.addAll(entities);

        NIF nif = new NIF21(entitiesToProcess, parser);

        return process(nif, outputFormat);
    }

    public String getNIF(String outputFormat) {

        List<NIFBean> entitiesToProcess = new ArrayList<>(entities.size());

        entitiesToProcess.add(beanContext);
        entitiesToProcess.addAll(entities);

        NIF nif = new NIF21(entitiesToProcess);

        return process(nif, outputFormat);

    }

    private String process(NIF nif, String outputFormat) {

        /*if (outputFormat != null && SemanticMediaType.TURTLE.equalsIgnoreCase(outputFormat)) {
            return nif.getTurtle();
        } else if (outputFormat != null && SemanticMediaType.JSON_LD.equalsIgnoreCase(outputFormat)) {
            return nif.getJSONLD(configuration.getJsonContext());
        } else if (outputFormat != null && SemanticMediaType.NTRIPLES.equalsIgnoreCase(outputFormat)) {
            return nif.getNTriples();
        }

        return nif.getTurtle();
        */

        return "";

    }
}
