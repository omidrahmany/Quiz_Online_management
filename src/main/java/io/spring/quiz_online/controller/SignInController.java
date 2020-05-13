package io.spring.quiz_online.controller;

import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.dto.SignInAccountDto;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/sign-in")
public class SignInController {

    private AccountService accountService;

    @Autowired
    public SignInController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Transactional
    public AccountDto signIn(@RequestBody SignInAccountDto command) {
        System.out.println("server side ...");
       return accountService.findAccountByUsernameAndPassword(command.getUsername(), command.getPassword());
    }
}
