package org.example.prote;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@OpenAPIDefinition(
        servers = {
                @Server(
                        url = "https://lazy-shaylah-guhyunwoo-777b581b.koyeb.app",
                        description = "Koyeb 배포 서버"
                )
        }
)
@ConfigurationPropertiesScan
@SpringBootApplication
public class ProteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProteApplication.class, args);
    }

}
