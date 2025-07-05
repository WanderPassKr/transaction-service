package com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.exchange;

import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.exchange.dto.request.ConvertRequest;
import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.exchange.dto.response.ConvertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ExchangeRateClient {

    @Qualifier("exchangeWebClient")
    private final WebClient webClient;

    public ConvertResponse convert(ConvertRequest request) {
        return webClient.post()
                .uri("/api/exchange/convert")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ConvertResponse.class)
                .block();
    }
}
