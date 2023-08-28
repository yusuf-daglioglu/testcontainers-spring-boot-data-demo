import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.util.List;

public class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
                .withDatabaseName("integration-tests-db-1")
                .withUsername("dbuser1")
                .withPassword("dbpassword1");

        postgreSQLContainer.start();

        try {
            Resource resource = configurableApplicationContext.getResource("classpath:application-test.yml");
            YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
            List<PropertySource<?>> yamlTestProperties = sourceLoader.load("yaml_test_properties", resource);
            configurableApplicationContext.getEnvironment().getPropertySources().addFirst(yamlTestProperties.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TestPropertyValues.of(
                "spring.jpa.hibernate.ddl-auto=" + "create-drop",
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}