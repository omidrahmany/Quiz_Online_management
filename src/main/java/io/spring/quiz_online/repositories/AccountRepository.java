package io.spring.quiz_online.repositories;

import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.model.Role;
import io.spring.quiz_online.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findAccountByUsername(String username);
    List<Account> findAccountsByRole(Role role);
    void deleteAccountsByRole(Role role);

}
