package org.dbpedia.spotlight;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;

@ComponentScan
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@IntegrationComponentScan
@EnableAutoConfiguration
@SuppressWarnings("checkstyle:hideutilityclassconstructor")
public class Services {
    public static void main(String[] args) {
        SpringApplication.run(Services.class, args);
    }
}
