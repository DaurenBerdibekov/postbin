package berdibekov.de.backend;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.checkerframework.checker.nullness.qual.NonNull;
import org.testcontainers.utility.DockerImageName;

public class AllContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

    @SuppressWarnings("rawtypes")
    private static final PostgreSQLContainer postgresContainer =
            new PostgreSQLContainer(DockerImageName.parse("postgres:15.5"));

    @Override
    public void initialize(@NonNull @NotNull ConfigurableApplicationContext applicationContext) {

        System.out.println("starting containers...");

        postgresContainer
                .withDatabaseName("postbin")
                .withUsername("postgres")
                .withPassword("user")
                // .withInitScript("db/init.sql")
                .withReuse(true)
                .start();


        TestPropertyValues.of(
                "spring.datasource.driver-class-name=%s".formatted(postgresContainer.getDriverClassName()),
                "spring.datasource.url=%s".formatted(postgresContainer.getJdbcUrl()),
                "spring.datasource.username=%s".formatted(postgresContainer.getUsername()),
                "spring.datasource.password=%s".formatted(postgresContainer.getPassword())
        ).applyTo(applicationContext);

        //keep it running
        System.out.println("###################################################");
        System.out.println("Startup Testenvironment Done");
        System.out.println("###################################################");

    }
}
