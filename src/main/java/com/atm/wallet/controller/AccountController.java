package com.atm.wallet.controller;

import com.atm.wallet.dto.AccountDto;
import com.atm.wallet.model.Account;
import com.atm.wallet.service.AccountService;
import com.atm.wallet.util.Response;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public Response<Account> createAccount(HttpServletRequest request,@RequestBody @Valid AccountDto accountDto){
        UUID userId =(UUID) request.getAttribute("userId");
        Account account = accountService.createAccount(accountDto, userId);

        return new Response<>(true, "Account created successfully", account);
    }

    @GetMapping
    public Response<List<Account>> getAccounts(HttpServletRequest request){
        UUID userId =(UUID) request.getAttribute("userId");
        List<Account> account = accountService.getAccountsByUserId(userId);

        return new Response<>(true, "Accounts retrieved successfully", account);
    }

    @DeleteMapping("/{accountId}")
    public Response<String> deleteAccount(@PathVariable UUID accountId){
        accountService.deleteAccount(accountId);
        return new Response<>(true, "Account deleted successfully", null);
    }

    @GetMapping("/{accountId}")
    public Response<Account> getAccount(@PathVariable UUID accountId){
        Account account = accountService.getAccountById(accountId);
        return new Response<>(true, "Account retrieved successfully", account);
    }

}
