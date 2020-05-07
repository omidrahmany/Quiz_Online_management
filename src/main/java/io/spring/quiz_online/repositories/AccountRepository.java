package io.spring.quiz_online.repositories;

import io.spring.quiz_online.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findAccountByUsername(String username);

}
