package com.atm.wallet.dto;

import com.atm.wallet.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "\\d{10}", message = "Account number must be 10 digits")
    private String accountNumber;
    @NotBlank(message = "Account name is required")
    private String accountName;
    @NotNull(message = "Account type is required")
    private AccountType accountType;
    @NotNull(message = "Balance is required")
    private BigDecimal balance;
}
