package com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.card;

import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.card.dto.request.HoldPaymentRequest;
import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.card.dto.response.CardResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CardClient {

    @Qualifier("cardWebClient")
    private final WebClient webClient;

    public CardResponse getCardInfo(Long cardId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/cards/{cardId}")
                        .build(cardId)
                )
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
    }

    public void holdPayment(Long cardId, BigDecimal amount) {
        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/cards/{cardId}/hold")
                        .build(cardId)
                )
                .bodyValue(new HoldPaymentRequest(amount))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, client -> {
                    return client.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new IllegalArgumentException("잘못된 요청: " + error)));
                })
                .onStatus(HttpStatusCode::is5xxServerError, client -> {
                    return client.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new IllegalStateException("카드 서버 오류: " + error)));
                })
                .toBodilessEntity()
                .block(); // 동기 처리
    }
}

