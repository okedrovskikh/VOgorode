package ru.tinkoff.academy;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import ru.tinkoff.academy.field.Field;
import ru.tinkoff.academy.gardener.Gardener;
import ru.tinkoff.academy.garden.Garden;
import ru.tinkoff.academy.work.WorkEnum;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Containers implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    public static final String MONGO_DATABASE = "test";

    public static final Network network = Network.newNetwork();

    public static final MongoDBContainer mongo = new MongoDBContainer("mongo")
            .withNetwork(network)
            .withNetworkAliases("mongo")
            .withExposedPorts(27017)
            .withStartupTimeout(Duration.ofMinutes(3))
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(MongoDBContainer.class)));

    @Override
    public void beforeAll(ExtensionContext context) {
        mongo.start();
        try (MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongo.getConnectionString()))
                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .build())) {
            MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DATABASE);
            GardenTestData.addTestData(mongoTemplate);
            FieldTestData.addTestData(mongoTemplate);
            GardenerTestData.addTestData(mongoTemplate);
        }
    }

    @Override
    public void close() {
        mongo.close();
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

    public static class FieldTestData {
        public static final List<Field> fieldTestData = List.of(
                Field.builder()
                        .id("id1")
                        .address("addr1")
                        .latitude(800D)
                        .longitude(800D)
                        .area(polygon(List.of(0D, 0D, 0D, 5D, 5D, 5D, 5D, 0D, 0D, 0D)))
                        .gardener(Gardener.builder().id("id2").build())
                        .build(),
                Field.builder()
                        .id("id2")
                        .address("addr3")
                        .latitude(800D)
                        .longitude(800D)
                        .area(polygon(List.of(0D, 0D, 0D, 5D, 5D, 5D, 5D, 0D, 0D, 0D)))
                        .gardener(Gardener.builder().id("id3").build())
                        .build(),
                Field.builder()
                        .id("id3")
                        .address("addr1")
                        .latitude(800D)
                        .longitude(800D)
                        .area(polygon(List.of(0D, 0D, 0D, 5D, 5D, 5D, 5D, 0D, 0D, 0D)))
                        .gardener(Gardener.builder().id("id1").build())
                        .build(),
                Field.builder()
                        .id("id4")
                        .address("addr1")
                        .latitude(800D)
                        .longitude(800D)
                        .area(polygon(List.of(0D, 0D, 0D, 5D, 5D, 5D, 5D, 0D, 0D, 0D)))
                        .gardener(Gardener.builder().id("id1").build())
                        .build(),
                Field.builder()
                        .id("id5")
                        .address("addr5")
                        .latitude(800D)
                        .longitude(800D)
                        .area(polygon(List.of(0D, 0D, 0D, 5D, 5D, 5D, 5D, 0D, 0D, 0D)))
                        .gardener(Gardener.builder().id("id1").build())
                        .build()
        );

        public static void addTestData(MongoTemplate mongoTemplate) {
            mongoTemplate.insertAll(fieldTestData);
        }

        private static Polygon polygon(List<Double> coords) {
            List<Point> points = new ArrayList<>();

            for (int i = 0; i < coords.size(); i += 2) {
                points.add(new Point(coords.get(i), coords.get(i + 1)));
            }

            return new Polygon(points);
        }
    }

    public static class GardenerTestData {
        public static List<Gardener> gardenTestData = List.of(
                Gardener.builder()
                        .id("id1")
                        .name("name1")
                        .surname("surname1")
                        .email("email1@email.com")
                        .fields(List.of(
                                FieldTestData.fieldTestData.get(2),
                                FieldTestData.fieldTestData.get(3),
                                FieldTestData.fieldTestData.get(4)
                        ))
                        .build(),
                Gardener.builder()
                        .id("id2")
                        .name("name2")
                        .surname("surname2")
                        .email("email2@email.com")
                        .fields(List.of(
                                FieldTestData.fieldTestData.get(0)
                        ))
                        .build(),
                Gardener.builder()
                        .id("id3")
                        .name("name3")
                        .surname("surname3")
                        .email("email3@email.com")
                        .telephone("800-800-800")
                        .fields(List.of(
                                FieldTestData.fieldTestData.get(1)
                        ))
                        .build(),
                Gardener.builder()
                        .id("id4")
                        .name("name4")
                        .surname("surname4")
                        .email("email4@email.com")
                        .fields(List.of())
                        .build(),
                Gardener.builder()
                        .id("id5")
                        .name("name5")
                        .surname("surname5")
                        .email("email5@email.com")
                        .fields(List.of())
                        .build(),
                Gardener.builder()
                        .id("id6")
                        .name("name6")
                        .surname("surname6")
                        .email("email6@email.com")
                        .telephone("890-900-678")
                        .fields(List.of())
                        .build()
        );

        public static void addTestData(MongoTemplate mongoTemplate) {
            mongoTemplate.insertAll(gardenTestData);
        }
    }
}
