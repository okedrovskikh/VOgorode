package ru.tinkoff.academy.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"ru.tinkoff.academy.garden", "ru.tinkoff.academy.mongo"})
public class MongoConfiguration {
}
