package enset.dimba.accountservice.service;

import enset.dimba.accountservice.domain.Account;
import enset.dimba.accountservice.domain.AccountTransaction;
import enset.dimba.accountservice.domain.AccountType;
import enset.dimba.accountservice.domain.TransactionType;
import enset.dimba.accountservice.exceptions.AccountNotFoundException;
import enset.dimba.accountservice.exceptions.BalanceNotSufficientException;
import enset.dimba.accountservice.exceptions.NegativeAmountException;
import enset.dimba.accountservice.repository.AccountRepository;
import enset.dimba.accountservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class EbankService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public EbankService(AccountRepository accountRepository, TransactionRepository transactionRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account getAccountById(String accountId) {
         Account account = accountRepository.findById(accountId)
                .orElseThrow(
                        () -> new AccountNotFoundException("Account not found")
                );
         return account;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }



    @Transactional
    public Account savedNewAccount(double initialBalance, AccountType type,
                                   String currency, Long customerId){
        if (initialBalance<0) throw new NegativeAmountException("The amount should positive");
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .currency(currency)
                .type(type)
                .createdAt(LocalDateTime.now())
                .customerId(customerId)
                .balance(initialBalance)
                .build();
        Account savedAccount = accountRepository.save(account);
        AccountTransaction transaction = AccountTransaction.builder()
                .amount(initialBalance)
                .type(TransactionType.CREDIT)
                .dateTime(LocalDateTime.now())
                .account(savedAccount)
                .build();
        transactionRepository.save(transaction);
        return savedAccount;
    }

    @Transactional
    public AccountTransaction credit(String accountId, double amount) {

        if(amount <=0) throw  new NegativeAmountException("Account should be positive");
        Account account = getAccountById(accountId);


        AccountTransaction accountTransaction =
                AccountTransaction.builder()
                        .amount(amount)
                        .dateTime(LocalDateTime.now())
                        .type(TransactionType.CREDIT)
                        .account(account)
                        .build();
        AccountTransaction transaction = transactionRepository.save(accountTransaction);
        account.setBalance(account.getBalance() + amount);
        Account savedAccount = accountRepository.save(account);

        return transaction;
    }

    @Transactional
    public AccountTransaction debit (String accountId, double amount) {

        if(amount <= 0) throw  new NegativeAmountException("Account should be positive");
        Account account = getAccountById(accountId);
        if(account.getBalance() < amount) throw new BalanceNotSufficientException("Balanvce Not Sufficient Exception");
        AccountTransaction accountTransaction =
                AccountTransaction.builder()
                        .amount(amount)
                        .dateTime(LocalDateTime.now())
                        .type(TransactionType.DEBIT)
                        .account(account)
                        .build();
        AccountTransaction transaction = transactionRepository.save(accountTransaction);
        account.setBalance(account.getBalance() - amount);
        Account savedAccount = accountRepository.save(account);

        return transaction;
    }
}
