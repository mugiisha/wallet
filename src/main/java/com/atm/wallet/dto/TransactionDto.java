package com.atm.wallet.dto;

import com.atm.wallet.enums.Categories;
import com.atm.wallet.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionDto {
    @NotNull(message = "Account id is required")
    private UUID accountId;
    @NotNull(message = "Transaction type is required")
    private TransactionType type;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    @NotNull(message = "Category is required")
    private Categories category;
}
