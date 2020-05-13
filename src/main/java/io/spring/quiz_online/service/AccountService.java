package io.spring.quiz_online.service;

import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.dto.SignInAccountDto;
import io.spring.quiz_online.excetion_handling.InvalidAccountException;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.model.RegisteredUserInfo;
import io.spring.quiz_online.model.RoleEnum;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    void register(RegisteredUserInfo registeredUserInfo) ;
    Optional<Account> findByUsername(String username);
    List<Account> findAccountsByRoleType(RoleEnum roleType);
    void deleteAccountsByRole(RoleEnum roleType);
    Optional<Account> findAccountByEmail(String email);
   AccountDto findAccountByUsernameAndPassword(String username, String password);

}
