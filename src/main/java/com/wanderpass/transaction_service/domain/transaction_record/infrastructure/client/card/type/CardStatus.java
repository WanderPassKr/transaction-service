package com.wanderpass.transaction_service.domain.transaction_record.infrastructure.client.card.type;

/**
 * 카드 상태를 나타내는 열거형입니다.
 * 카드의 사용 가능 여부 및 분실/비활성 상태를 관리합니다.
 */
public enum CardStatus {
    ACTIVE,     // 사용 가능
    INACTIVE,   // 비활성 (예: 유저 요청으로 잠금)
    LOST        // 분실 처리됨 (결제 불가)
}