package com.notification.config.mongo;

import com.mongodb.ConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

//@Profile("local") // 기본이 local
//@Configuration
public class DockerComposeMongoConfig {
    private static final String MONGO_HOST = "localhost";
    private static final int MONGO_PORT = 27017;
    private static final String DATABASE_NAME = "notification";

    @Bean(name = "notificationMongoFactory")
    public MongoDatabaseFactory notificationMongoFactory() {
        return new SimpleMongoClientDatabaseFactory(connectionString());
    }

    /**
     * mongodb://root:12345@localhost:27017/notification?authSource=admin
     */
    private ConnectionString connectionString() {
        return new ConnectionString("mongodb://root:12345@" + MONGO_HOST + ":" + MONGO_PORT + "/" + DATABASE_NAME + "?authSource=admin");
    }
}
