package com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.card.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HoldPaymentRequest {
    private BigDecimal amount;
}