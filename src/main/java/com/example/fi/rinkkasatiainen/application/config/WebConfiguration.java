package com.example.fi.rinkkasatiainen.application.config;

import com.example.fi.rinkkasatiainen.model.*;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.function.Supplier;


@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(WebConfiguration.class);



    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(true);
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


    private Supplier<SessionUUID> uuidSupplier(){
        // TODO AkS: The supplier could be one using DB unique constraint.
        return SessionUUID::generate;
    }

    @Bean
    public Audience audience(EventStore eventStore) {
        return new Audience(eventStore);
    }

    @Bean
    public Schedule schedule(EventStore eventStore) {
        return new Schedule(uuidSupplier(), eventStore);
    }

    @Bean
    public AddSessionCommandHandler addSessionCommandHandler(Schedule schedule, EventPublisher eventPublisher) {
        return new AddSessionCommandHandler( schedule, eventPublisher);
    }

    @Bean
    public RegisterParticipantCommandHandler registerParticipantCommandHandler(Schedule schedule, Audience audience, EventPublisher eventPublisher) {
        return new RegisterParticipantCommandHandler(schedule, audience, eventPublisher);
    }

    @Bean
    public RateSessionCommandHandler rateSessionCommandHandler(Schedule schedule, EventPublisher eventPublisher) {
        return new RateSessionCommandHandler(schedule, eventPublisher);
    }

    @Bean
    public EventPublisher getEventPublisher(EventStore eventStore) {
        return new EventPublisher(eventStore);
    }

}