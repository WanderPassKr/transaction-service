package com.wanderpass.transaction_service.global.uuid;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultUuidGenerator implements UuidGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
