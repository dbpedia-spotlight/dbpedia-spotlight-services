package org.dbpedia.spotlight.approach;


import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface Candidates {

    @RequestLine("POST /candidates")
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept: application/json"})
    String candidates(@Param("text") String text,
                      @Param("types") String dbpediaTypes,
                      @Param("confidence") Double confidence);


    @RequestLine("POST /candidates")
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept: text/html"})
    String html(@Param("text") String text,
                @Param("types") String dbpediaTypes,
                @Param("confidence") Double confidence);

}
