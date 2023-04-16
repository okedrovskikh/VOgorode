package ru.tinkoff.academy;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.time.Duration;

public class Containers implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    public static final String POSTGRES_USER = "postgres";
    public static final String POSTGRES_PASSWORD = "123";
    public static final String POSTGRES_DATABASE = "vogorode";

    public static final Network network = Network.newNetwork();

    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
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
    public void beforeAll(ExtensionContext context) throws Exception {
        postgres.start();
    }

    @Override
    public void close() throws Throwable {
        postgres.close();
        network.close();
    }
}
