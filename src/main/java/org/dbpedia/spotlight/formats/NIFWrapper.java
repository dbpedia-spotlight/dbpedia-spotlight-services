package org.dbpedia.spotlight.formats;

import org.dbpedia.spotlight.common.AnnotationUnit;
import org.dbpedia.spotlight.common.SemanticMediaType;
import org.dbpedia.spotlight.services.SpotlightConfiguration;
import org.nlp2rdf.NIF;
import org.nlp2rdf.bean.NIFBean;
import org.nlp2rdf.bean.NIFType;
import org.nlp2rdf.nif21.impl.NIF21;

import java.util.ArrayList;
import java.util.List;

import static org.dbpedia.spotlight.common.Constants.SLASH;

public class NIFWrapper {

    private SpotlightConfiguration configuration;

    private List<NIFBean> entities = new ArrayList<>();

    private NIFBean beanContext;

    private String baseURI;


    public NIFWrapper(SpotlightConfiguration configuration) {

        this.configuration = configuration;

        this.baseURI = configuration.getSpotlightURL();

        formatBaseURI();
    }

    public NIFWrapper(SpotlightConfiguration configuration, String baseURI) {

        this.configuration = configuration;

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

    public void entity(AnnotationUnit annotationUnit) {

        NIFBean.NIFBeanBuilder entity = new NIFBean.NIFBeanBuilder();
        this.context(annotationUnit.getText());

        if (annotationUnit.getResources() != null && !annotationUnit.getResources().isEmpty()) {

            annotationUnit.getResources().stream().forEach(resourceItem -> {
                entity.mention(resourceItem.getSurfaceForm());
                entity.beginIndex(resourceItem.beginIndex());
                entity.endIndex(resourceItem.endIndex());
                entity.annotator(configuration.getSpotlightURL());
                entity.taIdentRef(resourceItem.getUri());
                entity.types(resourceItem.typesList());
                entity.score(resourceItem.score());
                entity.context(baseURI, resourceItem.beginIndex(), resourceItem.getSurfaceForm().length());
                entities.add(new NIFBean(entity));
            });
        }


    }

    public String getNIF(String outputFormat) {

        List<NIFBean> entitiesToProcess = new ArrayList<>(entities.size());

        entitiesToProcess.add(beanContext);
        entitiesToProcess.addAll(entities);

        NIF nif = new NIF21(entitiesToProcess);

        return process(nif, outputFormat);

    }

    private String process(NIF nif, String outputFormat) {

        if (outputFormat != null && SemanticMediaType.TEXT_TURTLE.equalsIgnoreCase(outputFormat)) {
            return nif.getTurtle();
        } else if (outputFormat != null && SemanticMediaType.APPLICATION_LD_JSON.equalsIgnoreCase(outputFormat)) {
            return nif.getJSONLD(configuration.getJsonContext());
        } else if (outputFormat != null && SemanticMediaType.APPLICATION_N_TRIPLES.equalsIgnoreCase(outputFormat)) {
            return nif.getNTriples();
        }

        return nif.getTurtle();

    }
}
