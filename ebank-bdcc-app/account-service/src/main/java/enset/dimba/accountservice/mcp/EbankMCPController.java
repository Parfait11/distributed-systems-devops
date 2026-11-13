package enset.dimba.accountservice.mcp;

import enset.dimba.accountservice.domain.Account;
import enset.dimba.accountservice.domain.AccountTransaction;
import enset.dimba.accountservice.domain.AccountType;
import enset.dimba.accountservice.service.EbankService;
import org.springframework.ai.mcp.annotation.McpArg;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class EbankMCPController {


    private EbankService ebankService;

    public EbankMCPController(EbankService ebankService) {
        this.ebankService = ebankService;
    }


    @McpTool(description = "Get Account By Id")
    public Account getAccountById(@McpArg(description = "The account Id") String accountId) {
        return ebankService.getAccountById(accountId);
    }

    @McpTool(description = "Get All account")
    public List<Account> getAllAccounts() {
        return ebankService.getAllAccounts();
    }

    @McpTool(description = "Save a new account")
    public Account savedNewAccount(@McpArg(description = "The initial balance") double initialBalance,
                                   @McpArg(description = "The Account Type") AccountType type,
                                   @McpArg(description = "The Account Currency")String currency,
                                   @McpArg(description = "The customer Id")Long customerId) {
        return ebankService.savedNewAccount(initialBalance, type, currency, customerId);
    }

    @McpTool(description = "Credit account")
    public AccountTransaction credit(
            @McpArg(description = "The account Id") String accountId,
            @McpArg(description = "The credited amount") double amount) {
        return ebankService.credit(accountId, amount);
    }

    @McpTool(description = "Debit account")
    public AccountTransaction debit(
            @McpArg(description = "The account Id") String accountId,
            @McpArg(description = "The debited amount")double amount) {
        return ebankService.debit(accountId, amount);
    }

}
