package enset.dimba.accountservice.web;

import enset.dimba.accountservice.domain.Account;
import enset.dimba.accountservice.domain.AccountTransaction;
import enset.dimba.accountservice.domain.AccountType;
import enset.dimba.accountservice.service.EbankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EbankRestController {

    private EbankService ebankService;

    public EbankRestController(EbankService ebankService) {
        this.ebankService = ebankService;
    }

    @GetMapping("/accounts/{accountId}")
    public Account getAccountById(@PathVariable String accountId) {
        return ebankService.getAccountById(accountId);
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return ebankService.getAllAccounts();
    }

    @PostMapping("/accounts/save")
    public Account savedNewAccount(double initialBalance, AccountType type, String currency, Long customerId) {
        return ebankService.savedNewAccount(initialBalance, type, currency, customerId);
    }

    @PostMapping("/accounts/credit")
    public AccountTransaction credit(String accountId, double amount) {
        return ebankService.credit(accountId, amount);
    }

    @PostMapping("/accounts/debit")
    public AccountTransaction debit(String accountId, double amount) {
        return ebankService.debit(accountId, amount);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e){
        return ResponseEntity.internalServerError().body(Map.of("errorMessage", e.getMessage()));
    }
}
