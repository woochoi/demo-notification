package com.notification.config.mongo;

import com.mongodb.ConnectionString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

//@Profile("test")
@Slf4j
@Configuration
public class LocalMongoConfig {

    @Bean(name = "notificationMongoFactory") // MongoTemplateConfig 에서 가져다 사용한다
    public MongoDatabaseFactory notificationMongoFactory() {
        return new SimpleMongoClientDatabaseFactory(connectionString());
    }

    private ConnectionString connectionString() {
        return new ConnectionString("mongodb://root:12345@localhost:27017/notification?authSource=admin");
    }

}
