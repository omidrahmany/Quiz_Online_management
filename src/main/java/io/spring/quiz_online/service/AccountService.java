package io.spring.quiz_online.service;

import io.spring.quiz_online.excetion_handling.InvalidAccountException;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.model.RegisteredUserInfo;
import io.spring.quiz_online.model.RoleEnum;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    void register(RegisteredUserInfo registeredUserInfo) throws InvalidAccountException;
    Optional<Account> findByUsername(String username);
    List<Account> findAccountsByRoleType(RoleEnum roleType);
    void deleteAccountsByRole(RoleEnum roleType);
    Optional<Account> findAccountByEmail(String email);
}
