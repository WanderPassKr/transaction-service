package com.wanderpass.transaction_service.domain.transaction_record.presentation.controller;

import com.wanderpass.transaction_service.global.dto.ApiResponse;
import com.wanderpass.transaction_service.domain.transaction_record.application.TransactionRecordService;
import com.wanderpass.transaction_service.domain.transaction_record.presentation.dto.request.SpendRequest;
import com.wanderpass.transaction_service.domain.transaction_record.presentation.dto.response.SpendResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction-records")
@RequiredArgsConstructor
public class TransactionRecordController {

    private final TransactionRecordService transactionRecordService;

    @PostMapping("/spend")
    public ResponseEntity<ApiResponse<SpendResponse>> spend(@RequestBody SpendRequest request) {
        SpendResponse response = transactionRecordService.spend(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
