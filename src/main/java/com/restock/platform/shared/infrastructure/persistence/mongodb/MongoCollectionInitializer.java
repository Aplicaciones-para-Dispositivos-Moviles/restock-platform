package com.restock.platform.shared.infrastructure.persistence.mongodb;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Configuration
public class MongoCollectionInitializer {

    private static final List<String> COLLECTION_NAMES = List.of(
            "users",
            "roles",
            "recipes",
            "batches",
            "custom_supplies",
            "order_batches",
            "orders",
            "supplies",
            "database_sequences",
            "sales",
            "alerts"
    );

    @Bean
    public ApplicationRunner collectionCreator(MongoTemplate mongoTemplate) {
        return args -> COLLECTION_NAMES.stream()
                .filter(collectionName -> !mongoTemplate.collectionExists(collectionName))
                .forEach(mongoTemplate::createCollection);
    }
}
