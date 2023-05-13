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
import ru.tinkoff.academy.work.WorkEnum;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

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
        private static final List<Garden> testGarden = List.of(
                Garden.builder()
                        .id("id1")
                        .ownerId(UUID.fromString("30a5d2e9-f0e9-4e39-aca2-71a4d3752e56"))
                        .siteId(UUID.fromString("08995a30-784e-491d-aad5-ec92ca8d1f5e"))
                        .x1(30D)
                        .y1(30D)
                        .x2(60D)
                        .y2(60D)
                        .works(List.of(WorkEnum.sow))
                        .build(),
                Garden.builder()
                        .id("id2")
                        .ownerId(UUID.fromString("30a5d2e9-f0e9-4e39-aca2-71a4d3752e56"))
                        .siteId(UUID.fromString("e2bfdf03-12a0-486b-9a28-9a8873dc5994"))
                        .x1(30D)
                        .y1(30D)
                        .x2(60D)
                        .y2(60D)
                        .works(List.of(WorkEnum.plant))
                        .build(),
                Garden.builder()
                        .id("id3")
                        .ownerId(UUID.fromString("30a5d2e9-f0e9-4e39-aca2-71a4d3752e56"))
                        .siteId(UUID.fromString("c6334fe8-2951-4e99-9d93-f1255900a426"))
                        .x1(30D)
                        .y1(30D)
                        .x2(60D)
                        .y2(60D)
                        .works(List.of(WorkEnum.shovel))
                        .build(),
                Garden.builder()
                        .id("id4")
                        .ownerId(UUID.fromString("30a5d2e9-f0e9-4e39-aca2-71a4d3752e56"))
                        .siteId(UUID.fromString("8c95b569-9eb6-4d9a-a27e-1c5cdde4f5a1"))
                        .x1(30D)
                        .y1(30D)
                        .x2(60D)
                        .y2(60D)
                        .works(List.of(WorkEnum.water))
                        .build()
        );

        public static void addTestData(MongoTemplate mongoTemplate) {
            mongoTemplate.insertAll(testGarden);
        }
    }
}
