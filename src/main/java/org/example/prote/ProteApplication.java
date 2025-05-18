package org.example.prote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ProteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProteApplication.class, args);
    }

}
