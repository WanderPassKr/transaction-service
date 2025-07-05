package com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.exchange.dto.request;

import com.wanderpass.transaction_service.domain.transaction_record.domain.type.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertRequest {
    private String requestId;
    private Currency fromCurrency;
    private Currency toCurrency;
    private BigDecimal amount;
}
