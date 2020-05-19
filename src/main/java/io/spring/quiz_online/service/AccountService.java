package io.spring.quiz_online.service;

import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.model.RegisteredUserInfo;
import io.spring.quiz_online.model.RoleEnum;

import java.util.List;

public interface AccountService {

    void register(RegisteredUserInfo registeredUserInfo) ;
    void updateAccount(AccountDto accountDto);
    AccountDto findByUsername(String username);
    List<Account> findAccountsByRoleType(RoleEnum roleType);
    void deleteAccountsByRole(RoleEnum roleType);
    AccountDto findAccountByEmail(String email);
   AccountDto findAccountByUsernameAndPassword(String username, String password);
   List<AccountDto> findAccountsNotEnabled();
   void deleteAccount(Long id);
   List<AccountDto> findAllAccounts();

   AccountDto findById(Long accountId);
   List<AccountDto> findAllTeachersAndStudentsRole();

}
