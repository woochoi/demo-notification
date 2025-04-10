package com.notification.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories( // 아래 basePackages 하위에 있는 몽고레파지토리는 아래 mongoTemplate 를 사용하게 해라
        basePackages = "com.notification",
        mongoTemplateRef = MongoTemplateConfig.MONGO_TEMPLATE
)
public class MongoTemplateConfig {
    // 아래 Bean 이름!
    public static final String MONGO_TEMPLATE = "notificationMongoTemplate";

    @Bean(name = MONGO_TEMPLATE)
    public MongoTemplate notificationMongoTemplate(
            MongoDatabaseFactory notificationMongoFactory, MongoConverter mongoConverter
    ) {
        return new MongoTemplate(notificationMongoFactory, mongoConverter); // mongoConverter
    }
}