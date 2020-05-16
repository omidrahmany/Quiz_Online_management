package io.spring.quiz_online.repositories;

import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long>  {

    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByEmail(String email);
    List<Account> findAccountsByRole(Role role);
    void deleteAccountsByRole(Role role);
    Optional<Account> findAccountByUsernameAndPassword(String username, String password);
    Optional<List<Account>> findAccountsByEnabledFalse();


}
