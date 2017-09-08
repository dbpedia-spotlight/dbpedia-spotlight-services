package org.dbpedia.spotlight.formats;

import com.google.gson.Gson;
import org.dbpedia.spotlight.common.AnnotationUnit;
import org.dbpedia.spotlight.common.ResourceItem;

import java.util.List;

public final class JSON {

    private JSON() {

    }

    public static AnnotationUnit to(String content) {

        Gson gson = new Gson();

        AnnotationUnit annotationUnit =  gson.fromJson(content, AnnotationUnit.class);

        fixPrefixes(annotationUnit.getResources());


        return annotationUnit;
    }

    private static void fixPrefixes(List<ResourceItem> resources) {
        if (resources != null && !resources.isEmpty()) {
            for(ResourceItem resource: resources) {
                resource.setTypes(fixPrefixes(resource.getTypes()));
            }
        }
    }

    private static String fixPrefixes(String value) {

        if (value != null && !value.isEmpty()) {
            return value.replace("Http", "http").
                replace("DBpedia:", "dbpedia:").
                replace("Schema:", "schema:");
        }
        return value;

    }

}
