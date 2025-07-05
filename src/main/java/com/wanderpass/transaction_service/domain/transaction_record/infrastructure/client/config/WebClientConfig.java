package com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean(name = "cardWebClient")
    public WebClient cardServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082") // card-service 포트
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "exchangeWebClient")
    public WebClient exchangeRateServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083") // exchange-rate-service 포트
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
