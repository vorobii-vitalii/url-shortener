package com.url.shortener.config;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@PropertySource("classpath:application.properties")
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = "com.url.shortener")
public class SpringConfig {

    @Value("${mongo.host}")
    private String mongoHostName;

    @Value("${mongo.port}")
    private Integer mongoPortNumber;

    @Value("${mongo.database}")
    private String mongoDatabase;

    @Bean
    public MongoClientFactoryBean mongoClientFactoryBean() {
        final MongoClientFactoryBean mongoClientFactoryBean = new MongoClientFactoryBean();

        mongoClientFactoryBean.setHost(mongoHostName);
        mongoClientFactoryBean.setPort(mongoPortNumber);

        return mongoClientFactoryBean;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, mongoDatabase);
    }

}
