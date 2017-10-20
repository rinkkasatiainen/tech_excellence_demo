package com.example.fi.rinkkasatiainen.application.config;

import com.example.fi.rinkkasatiainen.eventstore.JpaEventStore;
import com.example.fi.rinkkasatiainen.eventstore.PersistentEventStore;
import com.example.fi.rinkkasatiainen.model.EventStore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
//        basePackages = {"com.example.fi.rinkkasatiainen.", "com.yourkindofgames.infra.eventStore"},
//        entityManagerFactoryRef = "entityManagerFactory",
//        transactionManagerRef = "transactionManager"
)
@EnableTransactionManagement
public class EventStoreConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new EventStoreObjectMapper().getObjectMapper();
    }


    @Bean
    @Profile("dev")
    public EventStore getEventStore(ObjectMapper objectMapper, JpaEventStore jpaEventStore){
        return new PersistentEventStore(objectMapper, jpaEventStore);
    }


}
