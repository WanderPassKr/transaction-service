package com.wanderpass.transaction_service.domain.transaction_record.application;

import com.wanderpass.transaction_service.domain.transaction_record.domain.entity.TransactionRecord;
import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.card.CardClient;
import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.card.dto.response.CardResponse;
import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.exchange.ExchangeRateClient;
import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.exchange.dto.request.ConvertRequest;
import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.exchange.dto.response.ConvertResponse;
import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.persistence.TransactionsRecordRepository;
import com.wanderpass.transaction_service.domain.transaction_record.presentation.dto.request.SpendRequest;
import com.wanderpass.transaction_service.domain.transaction_record.presentation.dto.response.SpendResponse;
import com.wanderpass.transaction_service.global.uuid.UuidGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionRecordService {

    private final UuidGenerator uuidGenerator;
    private final ExchangeRateClient exchangeRateClient;
    private final CardClient cardClient;
    private final TransactionsRecordRepository transactionsRecordRepository;

    @Transactional
    public SpendResponse spend(SpendRequest request) {

        // 1. 카드의 원 기준 통화 조회
        // 카드 개설 당시 통화를 직접 확인하여 카드 개설 통화를 기준으로 잔금에서 전환이 가능한지 확인해야한다.
        CardResponse cardInfoResponse = cardClient.getCardInfo(request.getCardId());

        // 2. 환율 환산 요청
        ConvertRequest convertRequest = ConvertRequest.builder()
                .requestId(uuidGenerator.generate())
                .fromCurrency(cardInfoResponse.getCurrency())
                .toCurrency(request.getOriginalCurrency())
                .amount(request.getOriginalAmount())
                .build();

        ConvertResponse convertResponse = exchangeRateClient.convert(convertRequest);
        BigDecimal convertedAmount = convertResponse.getConvertedAmount();

        // 3. hold 요청
        cardClient.holdPayment(request.getCardId(), convertedAmount);

        // 4. 거래 기록 저장
        TransactionRecord record = transactionsRecordRepository.save(
                TransactionRecord.createSpend(
                        request.getCardId(),
                        convertedAmount,
                        request.getOriginalCurrency(),
                        request.getOriginalAmount(),
                        request.getDescription()
                )
        );

        // 5. 응답 생성
        return SpendResponse.builder()
                .transactionId(record.getId())
                .cardId(request.getCardId())
                .originalCurrency(request.getOriginalCurrency())
                .originalAmount(request.getOriginalAmount())
                .convertedAmount(convertedAmount)
                .convertedCurrency(cardInfoResponse.getCurrency())
                .status(record.getStatus())
                .description(record.getDescription())
                .createdAt(record.getCreatedAt())
                .build();
    }
}
