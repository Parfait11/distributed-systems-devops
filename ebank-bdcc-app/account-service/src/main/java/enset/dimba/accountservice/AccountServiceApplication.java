
package enset.dimba.accountservice;

import enset.dimba.accountservice.domain.AccountType;
import enset.dimba.accountservice.service.EbankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(EbankService ebankService) {
        return args -> {
            ebankService.savedNewAccount(
                    65000,
                    AccountType.CURRENT_ACCOUNT,
                    "MAD",
                    Long.valueOf(1)
            );
            ebankService.savedNewAccount(
                    6000,
                    AccountType.CURRENT_ACCOUNT,
                    "MAD",
                    Long.valueOf(2)
            );
            ebankService.savedNewAccount(
                    98800,
                    AccountType.CURRENT_ACCOUNT,
                    "MAD",
                    Long.valueOf(3)
            );


            ebankService.getAllAccounts().forEach(acc ->{
                for(int i = 0; i < 5; i++){
                    ebankService.credit(acc.getId(), 500+Math.random()*230000);
                    ebankService.debit(acc.getId(), 500+Math.random()*10000);
                }
            });
        };
    }
}
