package com.wanderpass.transaction_service.domain.transaction_record.presentation.dto.request;

import com.wanderpass.transaction_service.domain.transaction_record.domain.type.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class SpendRequest {
    private Long cardId;
    private Currency originalCurrency;
    private BigDecimal originalAmount;
    private BigDecimal amount;
    private String description;
}
