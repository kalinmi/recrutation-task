package com.example.recrutationtask.controller;

import com.example.recrutationtask.model.dto.BillDto;
import com.example.recrutationtask.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/billing")
@RequiredArgsConstructor
@Slf4j
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/{uuid}")
    public ResponseEntity<BillDto> getBillForParent(@PathVariable("uuid") UUID uuid) {
        log.info("getBillForParent({})", uuid);
        BillDto billDto = billingService.calculateBillingForParent(uuid);
        return ResponseEntity.ok(billDto);
    }
}
