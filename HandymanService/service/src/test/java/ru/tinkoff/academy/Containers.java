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
import org.testcontainers.containers.output.Slf4jLogConsumer;
import ru.tinkoff.academy.bank.account.BankAccount;
import ru.tinkoff.academy.bank.account.payment.system.PaymentSystem;
import ru.tinkoff.academy.user.User;
import ru.tinkoff.academy.work.WorkEnum;
import ru.tinkoff.academy.worker.Worker;

import java.time.Duration;
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
            BankAccountTestData.addTestData(mongoTemplate);
            UserTestData.addTestData(mongoTemplate);
            WorkerTestData.addTestData(mongoTemplate);
        }
    }

    @Override
    public void close() {
        mongo.close();
        network.close();
    }

    public static class BankAccountTestData {
        public static final List<BankAccount> testBankAccounts = List.of(
                BankAccount.builder()
                        .id("id1")
                        .cardId("0000000000000001")
                        .user(UserTestData.userTestData.get(0))
                        .paymentSystem(PaymentSystem.mastercard)
                        .bank("bank1")
                        .build(),
                BankAccount.builder()
                        .id("id2")
                        .cardId("0000000000000002")
                        .user(UserTestData.userTestData.get(0))
                        .paymentSystem(PaymentSystem.mir)
                        .bank("bank1")
                        .build(),
                BankAccount.builder()
                        .id("id3")
                        .cardId("0000000000000003")
                        .user(UserTestData.userTestData.get(0))
                        .paymentSystem(PaymentSystem.mastercard)
                        .build(),
                BankAccount.builder()
                        .id("id4")
                        .cardId("0000000000000004")
                        .user(UserTestData.userTestData.get(0))
                        .paymentSystem(PaymentSystem.unionpay)
                        .build()
        );

        public static void addTestData(MongoTemplate mongoTemplate) {
            mongoTemplate.insertAll(testBankAccounts);
        }
    }

    public static class UserTestData {
        public static final List<User> userTestData = List.of(
                User.builder()
                        .id("id1")
                        .name("user1")
                        .surname("surname1")
                        .skills(new WorkEnum[]{WorkEnum.shovel})
                        .accounts(BankAccountTestData.testBankAccounts)
                        .email("email1@email.com")
                        .telephone("telephone")
                        .photo(new Byte[]{})
                        .build(),
                User.builder()
                        .id("id2")
                        .name("user2")
                        .surname("surname2")
                        .skills(new WorkEnum[]{WorkEnum.water})
                        .email("email2@email.com")
                        .telephone("telephone")
                        .photo(new Byte[]{})
                        .build(),
                User.builder()
                        .id("id3")
                        .name("user3")
                        .surname("surname3")
                        .skills(new WorkEnum[]{WorkEnum.plant})
                        .email("email3@email.com")
                        .telephone("telephone")
                        .photo(new Byte[]{})
                        .build(),
                User.builder()
                        .id("id4")
                        .name("user4")
                        .surname("surname4")
                        .skills(new WorkEnum[]{WorkEnum.sow})
                        .email("email4@email.com")
                        .telephone("telephone")
                        .photo(new Byte[]{})
                        .build()
        );

        public static void addTestData(MongoTemplate mongoTemplate) {
            mongoTemplate.insertAll(userTestData);
        }
    }

    public static class WorkerTestData {
        public static final List<Worker> workerTestData = List.of(
                  Worker.builder()
                          .id("id1")
                          .accountId(UUID.fromString("18481034-3765-4ba1-9640-b5f440300299"))
                          .latitude(800D)
                          .longitude(800D)
                          .services(List.of(WorkEnum.plant))
                          .build(),
                Worker.builder()
                        .id("id2")
                        .accountId(UUID.fromString("684d6f11-9b35-4906-90a2-95ce59ccc058"))
                        .latitude(800D)
                        .longitude(800D)
                        .services(List.of(WorkEnum.shovel))
                        .build(),
                Worker.builder()
                        .id("id3")
                        .accountId(UUID.fromString("4572c5f7-529b-4514-84a6-59547e6aa2c3"))
                        .latitude(800D)
                        .longitude(700D)
                        .services(List.of(WorkEnum.water))
                        .build(),
                Worker.builder()
                        .id("id4")
                        .accountId(UUID.fromString("47230361-8b73-4c19-ac27-8040e5e0a907"))
                        .latitude(800D)
                        .longitude(700D)
                        .services(List.of(WorkEnum.sow))
                        .build()
        );

        public static void addTestData(MongoTemplate mongoTemplate) {
            mongoTemplate.insertAll(workerTestData);
        }
    }
}
