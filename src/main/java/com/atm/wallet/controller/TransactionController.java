package com.atm.wallet.controller;

import com.atm.wallet.dto.TransactionDto;
import com.atm.wallet.model.Transaction;
import com.atm.wallet.service.TransactionService;
import com.atm.wallet.util.Response;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public Response<Transaction> recordTransaction(HttpServletRequest request, @RequestBody @Valid TransactionDto transactionDto) {
        UUID userId = (UUID) request.getAttribute("userId");
        Transaction transaction = transactionService.recordTransaction(transactionDto, userId);
        return new Response<>(true, "Transaction recorded successfully", transaction);
    }
}
