package com.wanderpass.transaction_service.domain.transaction_record.infrastructure.persistence;

import com.wanderpass.transaction_service.domain.transaction_record.domain.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRecordRepository extends JpaRepository<TransactionRecord, Long> {

}
