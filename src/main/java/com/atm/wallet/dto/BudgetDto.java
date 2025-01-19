package com.atm.wallet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDto {
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
}
