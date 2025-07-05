package com.wanderpass.transaction_service.domain.transaction_record.domain.entity;

import com.wanderpass.transaction_service.domain.transaction_record.domain.type.Currency;
import com.wanderpass.transaction_service.domain.transaction_record.domain.type.TransactionStatus;
import com.wanderpass.transaction_service.domain.transaction_record.domain.type.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_id", nullable = false)
    private Long cardId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "original_currency", nullable = false)
    private Currency originalCurrency;

    @Column(name = "original_amount", nullable = false)
    private BigDecimal originalAmount;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // 충전 행위를 담은 레코드를 생성하기 위한 정적 메서드
    public static TransactionRecord createCharge(Long cardId, BigDecimal amount, String description) {
        return TransactionRecord.builder()
                .cardId(cardId)
                .amount(amount)
                .type(TransactionType.CHARGE)
                .status(TransactionStatus.COMPLETE)
                .originalCurrency(Currency.KRW)
                .originalAmount(amount)
                .description(description)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // 결제 행위를 담은 레코드를 생성하기 위한 정적 메서드
    public static TransactionRecord createSpend(Long cardId, BigDecimal convertedAmount, Currency originalCurrency, BigDecimal originalAmount, String description) {
        return TransactionRecord.builder()
                .cardId(cardId)
                .amount(convertedAmount)
                .type(TransactionType.SPEND)
                .status(TransactionStatus.PENDING)
                .originalCurrency(originalCurrency)
                .originalAmount(originalAmount)
                .description(description)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private void markComplete() {
        this.status = TransactionStatus.COMPLETE;
        this.updatedAt = LocalDateTime.now();
    }

    private void markFailed() {
        this.status = TransactionStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }
}
