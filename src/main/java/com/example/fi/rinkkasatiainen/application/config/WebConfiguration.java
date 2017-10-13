package com.example.fi.rinkkasatiainen.application.config;

import com.example.fi.rinkkasatiainen.model.EventStore;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.web.session.RegisterParticipantCommandHandler;
import com.example.fi.rinkkasatiainen.web.session.commands.AddSessionCommandHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;


@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(WebConfiguration.class);

    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(true);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    @Profile("dev")
    public CorsFilter corsFilter() {

        log.info("Initializing CORS filter...");

        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.setMaxAge(1800L);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/v1/**", config);
        source.registerCorsConfiguration("/v2/api-docs/**", config);

        return new CorsFilter(source);
    }


    private Supplier<UUID> uuidSupplier(){
        // TODO AkS: The supplier could be one using DB unique constraint.
        return () -> UUID.randomUUID();
    }


    @Bean
    public EventStore getEventStore(){
        return (uuid) -> new ArrayList<>();
    }

    @Bean
    public Schedule schedule(EventStore eventStore) {
        return new Schedule(uuidSupplier(), eventStore);
    }
    @Bean
    public AddSessionCommandHandler addSessionCommandHandler(Schedule schedule) {
        return new AddSessionCommandHandler( schedule );
    }
    @Bean
    public RegisterParticipantCommandHandler registerParticipantCommandHandler() {
        return new RegisterParticipantCommandHandler();
    }
}