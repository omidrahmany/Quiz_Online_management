package io.spring.quiz_online.controller;


import io.spring.quiz_online.model.RegisteredUserInfo;
import io.spring.quiz_online.service.AccountService;
import io.spring.quiz_online.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
public class SignUpController {

    private AccountService accountService;

    @Autowired
    public SignUpController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public void signUp(@RequestBody RegisteredUserInfo registeredUserInfo){
        System.out.println(registeredUserInfo.getRole());
        System.out.println(registeredUserInfo.isEnable());
        accountService.register(registeredUserInfo);
    }


}
