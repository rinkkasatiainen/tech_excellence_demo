package com.example.fi.rinkkasatiainen.application.config;

import com.example.fi.rinkkasatiainen.eventstore.JpaEventStore;
import com.example.fi.rinkkasatiainen.eventstore.PersistentEventStore;
import com.example.fi.rinkkasatiainen.model.events.EventStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.example.fi.rinkkasatiainen.eventstore"}
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
//    @Profile("dev")
    public EventStore getEventStore(ObjectMapper objectMapper, @Autowired JpaEventStore jpaEventStore){
        return new PersistentEventStore(objectMapper, jpaEventStore);
    }


}
