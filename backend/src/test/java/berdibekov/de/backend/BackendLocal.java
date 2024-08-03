package berdibekov.de.backend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BackendLocal {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BackendLocal.class);

        application.setAdditionalProfiles("local");
        application.addInitializers(new AllContainerInitializer());
        application.run(args);

    }
}
