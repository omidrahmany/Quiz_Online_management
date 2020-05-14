package io.spring.quiz_online.controller;

import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.service.AccountService;
import io.spring.quiz_online.service.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentProfileController {
    private AuthenticationFacade authenticationFacade;
    private AccountService accountService;

    @Autowired
    public StudentProfileController(AuthenticationFacade authenticationFacade, AccountService accountService) {
        this.authenticationFacade = authenticationFacade;
        this.accountService = accountService;
    }

    @GetMapping("/get-student-info")
    public AccountDto getStudentAccountAuthenticated() {
        String username = authenticationFacade.getAuthentication().getName();
        Optional<Account> account = accountService.findByUsername(username);
        return account.map(value -> AccountDto.getInstance()
                .setFirstName(value.getPerson().getFirstName())
                .setLastName(value.getPerson().getLastName())
                .setUsername(value.getUsername())
                .setIsEnable(account.get().isEnabled())
                .setRoleType(String.valueOf(account.get().getRole().getRoleType()))
                .createAccountDto()).orElse(null);
    }
}
