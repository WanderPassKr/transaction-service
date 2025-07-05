package com.wanderpass.transaction_service.domain.transaction_record.presentation.dto.response;

import com.wanderpass.transaction_service.domain.transaction_record.domain.type.Currency;
import com.wanderpass.transaction_service.domain.transaction_record.domain.type.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SpendResponse {
    private Long transactionId;
    private Long cardId;
    private Currency originalCurrency;
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private Currency convertedCurrency;
    private TransactionStatus status;
    private String description;
    private LocalDateTime createdAt;
}
