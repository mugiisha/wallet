package com.atm.wallet.service;

import com.atm.wallet.dto.AccountDto;
import com.atm.wallet.exception.NotFoundException;
import com.atm.wallet.model.Account;
import com.atm.wallet.model.User;
import com.atm.wallet.repository.AccountRepository;
import com.atm.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userService;

    @Transactional
    public Account createAccount(AccountDto accountDto, UUID userId){
        log.info("creating account: {}", accountDto);

        User user = userService.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        Account account = Account.builder()
                .accountName(accountDto.getAccountName())
                .accountNumber(accountDto.getAccountNumber())
                .accountType(accountDto.getAccountType())
                .balance(accountDto.getBalance())
                .userId(userId)
                .build();

        Account savedAccount =  accountRepository.save(account);
        log.info("savedAccount: {}", savedAccount);
        List<Account> accounts = user.getAccounts();
        accounts.add(account);
        user.setAccounts(accounts);
        userService.save(user);

        return savedAccount;
    }


    public List<Account> getAccountsByUserId(UUID userId){
        log.info("fetching accounts for user: {}", userId);
        return accountRepository.findAllByUserId(userId);
    }

    public void deleteAccount(UUID accountId){
        log.info("deleting account with id: {}", accountId);
         accountRepository.deleteById(accountId);
    }

    public Account getAccountById(UUID accountId){
        log.info("fetching account with id: {}", accountId);
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    public void saveAccount(Account account){
        log.info("saving account with id: {}", account.getId());
        accountRepository.save(account);
    }
}
