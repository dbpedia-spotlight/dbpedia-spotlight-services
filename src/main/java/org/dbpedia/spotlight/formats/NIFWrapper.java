package org.dbpedia.spotlight.formats;

import com.google.common.hash.Hashing;
import org.dbpedia.spotlight.common.SemanticMediaType;
import org.dbpedia.spotlight.common.annotation.AnnotationUnit;
import org.dbpedia.spotlight.common.candidates.array.CandidatesArrayUnit;
import org.dbpedia.spotlight.services.SpotlightConfiguration;
import org.nlp2rdf.NIF;
import org.nlp2rdf.bean.NIFBean;
import org.nlp2rdf.bean.NIFType;
import org.nlp2rdf.nif21.impl.NIF21;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.dbpedia.spotlight.common.Prefixes.DBPEDIA_ONTOLOGY;
import static org.dbpedia.spotlight.common.Prefixes.SCHEMA_ONTOLOGY;

public class NIFWrapper {

    private SpotlightConfiguration configuration;

    private List<NIFBean> entities = new ArrayList<>();

    private NIFBean beanContext;

    private String baseURI;


    public NIFWrapper(SpotlightConfiguration configuration) {

        this.configuration = configuration;

        this.baseURI = configuration.getSpotlightURL();

    }

    public NIFWrapper(SpotlightConfiguration configuration, String baseURI) {

        this.configuration = configuration;

        this.baseURI = baseURI;

    }

    public void context(String mention) {

        int beginIndex = 0;
        int endIndex = mention.length();

        NIFBean.NIFBeanBuilder contextBuilder = new NIFBean.NIFBeanBuilder();

        contextBuilder.context(baseURI, beginIndex, endIndex).mention(mention).nifType(NIFType.CONTEXT);

        beanContext = new NIFBean(contextBuilder);

    }

    public void entity(CandidatesArrayUnit candidatesUnit) {

        NIFBean.NIFBeanBuilder entity = new NIFBean.NIFBeanBuilder();
        this.context(candidatesUnit.getCandidatesArrayAnnotation().getText());

        if (candidatesUnit.getCandidatesArrayAnnotation().hasSurfaceForms()) {

            candidatesUnit.getCandidatesArrayAnnotation().getSurfaceForms().stream().forEach(surfaceForm -> {

                surfaceForm.getCandidateArrayResourceItem().forEach(candidateArrayResourceItem -> {
                    entity.mention(surfaceForm.getName());
                    entity.beginIndex(surfaceForm.beginIndex());
                    entity.endIndex(surfaceForm.endIndex());
                    entity.annotator(configuration.getSpotlightURL());
                    entity.taIdentRef(candidateArrayResourceItem.getUri());
                    entity.types(candidateArrayResourceItem.typesList());
                    entity.score(candidateArrayResourceItem.getContextualScore());
                    entity.context(String.format("%s/uui=%s&",baseURI,
                            Hashing.sha256().hashString(candidateArrayResourceItem.getLabel(), StandardCharsets.UTF_8).toString()),
                            surfaceForm.beginIndex(), surfaceForm.endIndex());
                    entities.add(new NIFBean(entity));
                });
            });

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
                entity.context(baseURI, resourceItem.beginIndex(), resourceItem.endIndex());
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

    private String nifPrefixes(String nif) {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("@prefix schema:   <%s> .\n", SCHEMA_ONTOLOGY));
        builder.append(String.format("@prefix dbpedia:   <%s> .\n", DBPEDIA_ONTOLOGY));
        builder.append(nif);

        return builder.toString();

    }

    private String process(NIF nif, String outputFormat) {

        if (outputFormat != null && SemanticMediaType.TEXT_TURTLE.equalsIgnoreCase(outputFormat)) {
            return nifPrefixes(nif.getTurtle());
        } else if (outputFormat != null && SemanticMediaType.APPLICATION_LD_JSON.equalsIgnoreCase(outputFormat)) {
            return nif.getJSONLD(configuration.getJsonContext());
        } else if (outputFormat != null && SemanticMediaType.APPLICATION_N_TRIPLES.equalsIgnoreCase(outputFormat)) {
            return nif.getNTriples();
        }

        return nif.getTurtle();

    }
}
