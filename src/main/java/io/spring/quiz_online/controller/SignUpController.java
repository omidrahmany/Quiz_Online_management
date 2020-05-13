package io.spring.quiz_online.controller;


import io.spring.quiz_online.excetion_handling.InvalidAccountException;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.model.ResultMsg;
import io.spring.quiz_online.model.RegisteredUserInfo;
import io.spring.quiz_online.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public ResultMsg signUp(@RequestBody RegisteredUserInfo registeredUserInfo)  {

        Optional<Account> account = accountService.findAccountByEmail(registeredUserInfo.getEmail());
        if (account.isPresent()) return new ResultMsg(" .قبلا ایجاد شده است " +registeredUserInfo.getEmail() + " حساب با ایمیل ",false);

         account = accountService.findByUsername(registeredUserInfo.getUsername());
        if (account.isPresent()) return new ResultMsg(" .قبلا ایجاد شده است " +registeredUserInfo.getUsername() + " حساب با نام کاربری ",false);

        accountService.register(registeredUserInfo);
        return new ResultMsg(" .با موفقیت انجام شد " + registeredUserInfo.getUsername() + " ثبت نام اولیه با نام کاربری ",true);
    }
}
