package com.atm.wallet.service;

import com.atm.wallet.dto.TransactionDto;
import com.atm.wallet.enums.TransactionType;
import com.atm.wallet.exception.BadRequestException;
import com.atm.wallet.model.Account;
import com.atm.wallet.model.Budget;
import com.atm.wallet.model.Transaction;
import com.atm.wallet.repository.BudgetRepository;
import com.atm.wallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final UserService userService;
    private final BudgetRepository budgetService;

    public Transaction recordTransaction(TransactionDto transactionDto, UUID userId) {
        Account account = validateAndGetAccount(transactionDto.getAccountId(), userId);
        Transaction transaction = createTransactionFromDto(transactionDto);

        return switch (transactionDto.getType()) {
            case INFLOW -> handleInflowTransaction(transaction, account);
            case OUTFLOW -> handleOutflowTransaction(transaction, account, userId);
            default -> throw new BadRequestException("Invalid transaction type");
        };
    }

    private Account validateAndGetAccount(UUID accountId, UUID userId) {
        Account account = accountService.getAccountById(accountId);
        if (!account.getUserId().equals(userId)) {
            throw new BadRequestException("User not allowed to record transaction on this account");
        }
        return account;
    }

    private Transaction createTransactionFromDto(TransactionDto dto) {
        return Transaction.builder()
                .accountId(dto.getAccountId())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .type(dto.getType())
                .build();
    }

    private Transaction handleInflowTransaction(Transaction transaction, Account account) {
        Transaction savedTransaction = transactionRepository.save(transaction);
        updateAccountBalance(account, transaction.getAmount(), true);
        return savedTransaction;
    }

    private Transaction handleOutflowTransaction(Transaction transaction, Account account, UUID userId) {
        validateSufficientFunds(account, transaction.getAmount());
        Budget budget = validateAndGetBudget(userId, transaction.getAmount());

        updateBudgetAndAccount(budget, account, transaction.getAmount());
        return transactionRepository.save(transaction);
    }

    private void validateSufficientFunds(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BadRequestException("Insufficient funds");
        }
    }

    private Budget validateAndGetBudget(UUID userId, BigDecimal transactionAmount) {
        Budget budget = budgetService.findById(userService.getUserById(userId).getBudget().getId()).orElseThrow(()->new BadRequestException("Budget not found"));
        if (budget == null) {
            throw new BadRequestException("User must create a budget to record outflow transactions");
        }

        validateBudgetLimit(budget, transactionAmount);
        return budget;
    }

    private void validateBudgetLimit(Budget budget, BigDecimal transactionAmount) {
        BigDecimal projectedUsedAmount = budget.getUsedAmount().add(transactionAmount);
        if (projectedUsedAmount.compareTo(budget.getAmount()) > 0 && budget.getExceedBudgetAttempts() < 1) {
            budget.setExceedBudgetAttempts(budget.getExceedBudgetAttempts() + 1);
            budgetService.save(budget);
            throw new BadRequestException("Transaction will exceed budget. Please update budget or retry to confirm exceeding budget");
        }
    }

    private void updateBudgetAndAccount(Budget budget, Account account, BigDecimal amount) {
        budget.setUsedAmount(budget.getUsedAmount().add(amount));
        budgetService.save(budget);

        updateAccountBalance(account, amount, false);
    }

    private void updateAccountBalance(Account account, BigDecimal amount, boolean isInflow) {
        account.setBalance(isInflow ?
                account.getBalance().add(amount) :
                account.getBalance().subtract(amount));
        accountService.saveAccount(account);
    }
}