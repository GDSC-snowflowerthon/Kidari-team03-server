package com.committers.snowflowerthon.committersserver.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HealthCheck {
    @GetMapping("/health")
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
