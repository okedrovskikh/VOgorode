package ru.tinkoff.academy;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;
import ru.tinkoff.academy.garden.Garden;

import java.time.Duration;
import java.util.List;

public class Containers implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    private static final DockerImageName POSTGIS = DockerImageName.parse("postgis/postgis:15-3.3-alpine")
            .asCompatibleSubstituteFor("postgres");

    public static final String POSTGRES_USER = "postgres";
    public static final String POSTGRES_PASSWORD = "123";
    public static final String POSTGRES_DATABASE = "vogorode";

    public static final String MONOGO_DATABASE = "test";

    public static final Network network = Network.newNetwork();

    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(POSTGIS)
            .withNetwork(network)
            .withNetworkAliases("postgres")
            .withDatabaseName(POSTGRES_DATABASE)
            .withUsername(POSTGRES_USER)
            .withPassword(POSTGRES_PASSWORD)
            .withExposedPorts(5432)
            .withStartupTimeout(Duration.ofMinutes(3))
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(PostgreSQLContainer.class)));

    public static final MongoDBContainer mongo = new MongoDBContainer("mongo")
            .withNetwork(network)
            .withNetworkAliases("mongo")
            .withExposedPorts(27017)
            .withStartupTimeout(Duration.ofMinutes(3))
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(MongoDBContainer.class)));

    @Override
    public void beforeAll(ExtensionContext context) {
        postgres.start();
        mongo.start();
        try (MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongo.getConnectionString()))
                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .build())) {
            MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONOGO_DATABASE);
            GardenTestData.addTestData(mongoTemplate);
        }
    }

    @Override
    public void close() {
        postgres.close();
        network.close();
    }

    public static class GardenTestData {
        private static final List<Garden> testGarden = List.of();

        public static void addTestData(MongoTemplate mongoTemplate) {
            mongoTemplate.insertAll(testGarden);
        }
    }
}
