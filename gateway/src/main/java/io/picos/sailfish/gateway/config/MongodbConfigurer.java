package io.picos.sailfish.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "io.picos.sailfish.gateway.impl.mongodb")
public class MongodbConfigurer {

}
