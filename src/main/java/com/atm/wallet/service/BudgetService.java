package com.atm.wallet.service;

import com.atm.wallet.dto.UpdateBudgetDto;
import com.atm.wallet.exception.NotFoundException;
import com.atm.wallet.model.Budget;
import com.atm.wallet.model.User;
import com.atm.wallet.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserService userService;

    @Transactional
    public Budget createBudget(UUID userId, BigDecimal amount){

        User user = userService.getUserById(userId);

        Budget budget = Budget.builder()
                .amount(amount)
                .usedAmount(BigDecimal.ZERO)
                .exceedBudgetAttempts(0)
                .build();
        Budget savedBudget = budgetRepository.save(budget);
        user.setBudget(savedBudget);
        userService.saveUser(user);

        return savedBudget;
    }

    public Budget updateBudget(UUID budgetId, UpdateBudgetDto updateBudgetDto){
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new NotFoundException("Budget not found"));


        budget.setAmount(updateBudgetDto.getAmount() != null ? updateBudgetDto.getAmount() : budget.getAmount());
        budget.setUsedAmount(updateBudgetDto.getUsedAmount() != null ? updateBudgetDto.getUsedAmount() : budget.getUsedAmount());
        budget.setExceedBudgetAttempts(budget.getExceedBudgetAttempts() == 0 ? updateBudgetDto.getExceedBudgetAttempts() : budget.getExceedBudgetAttempts());
        return budgetRepository.save(budget);
    }

    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget getBudgetById(UUID budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new NotFoundException("Budget not found"));
    }
}
