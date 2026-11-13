package enset.dimba.accountservice.repository;

import enset.dimba.accountservice.domain.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<AccountTransaction, Long> {
  List<AccountTransaction> findByAccountId(String accountId);
}
