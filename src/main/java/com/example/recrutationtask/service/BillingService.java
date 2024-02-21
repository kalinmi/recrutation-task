package com.example.recrutationtask.service;

import com.example.recrutationtask.model.dto.BillDto;

import java.util.UUID;

public interface BillingService {

    BillDto calculateBillingForParent(UUID parentId);
}
