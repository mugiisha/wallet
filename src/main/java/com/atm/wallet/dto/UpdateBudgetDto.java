package com.atm.wallet.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class UpdateBudgetDto {
    private BigDecimal amount;
    private BigDecimal usedAmount;
    private int exceedBudgetAttempts;
}
