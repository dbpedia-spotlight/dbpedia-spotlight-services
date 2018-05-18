package org.dbpedia.spotlight;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@ConfigurationProperties
@SpringBootApplication
public class Services {

    public static void main(String[] args) {
        SpringApplication.run(Services.class, args);
    }

}
