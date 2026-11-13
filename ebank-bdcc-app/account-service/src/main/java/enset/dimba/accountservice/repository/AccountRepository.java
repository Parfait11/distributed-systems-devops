package enset.dimba.accountservice.repository;

import enset.dimba.accountservice.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

}
