package pl.generatoropinii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point.
 * Run locally: mvn spring-boot:run
 * Run from the built jar: java -jar generator-opinii.jar
 * Once started, the app is available at http://localhost:8080
 */
@SpringBootApplication
public class GeneratorOpiniiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorOpiniiApplication.class, args);
    }
}
