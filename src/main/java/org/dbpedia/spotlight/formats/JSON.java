package org.dbpedia.spotlight.formats;

import com.google.gson.Gson;
import org.dbpedia.spotlight.common.AnnotationUnit;

public class JSON {

    public static final AnnotationUnit to(String content) {
        Gson gson = new Gson();
        return gson.fromJson( content, AnnotationUnit.class );
    }

}
