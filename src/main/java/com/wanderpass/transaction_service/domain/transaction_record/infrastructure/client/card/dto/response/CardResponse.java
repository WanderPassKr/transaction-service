package com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.card.dto.response;

import com.wanderpass.transaction_service.domain.transaction_record.domain.type.Currency;
import com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.card.type.CardStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CardResponse {
    private Long cardId;
    private Currency currency;
    private CardStatus statua;
    private BigDecimal balance;
}
