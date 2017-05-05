package net.uranus.astra.auth.model.repository;

import net.uranus.astra.auth.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Account,Long> {
    Account findByUsername(String username);

}
