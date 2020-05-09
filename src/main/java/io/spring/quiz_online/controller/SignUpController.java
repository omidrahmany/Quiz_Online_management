package io.spring.quiz_online.controller;


import io.spring.quiz_online.excetion_handling.InvalidAccountException;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.model.OutputMsg;
import io.spring.quiz_online.model.RegisteredUserInfo;
import io.spring.quiz_online.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sign-up")
public class SignUpController {

    private AccountService accountService;

    @Autowired
    public SignUpController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public OutputMsg signUp(@RequestBody RegisteredUserInfo registeredUserInfo) throws InvalidAccountException {
        System.out.println(registeredUserInfo.getRole());
        System.out.println(registeredUserInfo.isEnable());
        accountService.register(registeredUserInfo);
        return new OutputMsg(" .با موفقیت انجام شد " + registeredUserInfo.getUsername() + " ثبت نام اولیه با نام کاربری ");
    }

    @PostMapping("/username-validation")
    public Account getAccountForUsernameValidation(@RequestBody RegisteredUserInfo registeredUserInfo){
//        return accountService.findByUsername(registeredUserInfo.getUsername()).orElse(null);
        Optional<Account> account = accountService.findByUsername(registeredUserInfo.getUsername());
        if (account.isPresent()) return account.get() ;
        else throw new UsernameNotFoundException("user name not found");
    }
    @PostMapping("/email-validation")
    public Account getAccountForEmailValidation(@RequestBody RegisteredUserInfo registeredUserInfo){
//      return   accountService.findAccountByEmail(registeredUserInfo.getEmail()).orElse(null);
        Optional<Account> account = accountService.findAccountByEmail(registeredUserInfo.getEmail());
        if (account.isPresent()) return account.get() ;
        else throw new UsernameNotFoundException("username not found");
    }


}
