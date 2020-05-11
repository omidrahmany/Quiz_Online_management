package io.spring.quiz_online.controller;


import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.excetion_handling.InvalidAccountException;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.model.OutputMsg;
import io.spring.quiz_online.model.RegisteredUserInfo;
import io.spring.quiz_online.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/sign-up")
public class SignUpController {

    private AccountService accountService;

    @Autowired
    public SignUpController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Transactional
    public OutputMsg signUp(@RequestBody RegisteredUserInfo registeredUserInfo) throws InvalidAccountException {

        Optional<Account> account = accountService.findAccountByEmail(registeredUserInfo.getEmail());
        if (account.isPresent()) return new OutputMsg(" .قبلا ثبت نام کرده است " +registeredUserInfo.getEmail() + " حساب با ایمیل ",false);

         account = accountService.findByUsername(registeredUserInfo.getUsername());
        if (account.isPresent()) return new OutputMsg(" .قبلا ثبت نام کرده است " +registeredUserInfo.getUsername() + " حساب با نام کاربری ",false);
        /*return account.map(value -> new OutputMsg("Non Unique"))
                .orElse(new OutputMsg("OK"));*/

        accountService.register(registeredUserInfo);
        return new OutputMsg(" .با موفقیت انجام شد " + registeredUserInfo.getUsername() + " ثبت نام اولیه با نام کاربری ",true);
    }






}
