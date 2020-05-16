package io.spring.quiz_online.controller;

import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.model.ResultMsg;
import io.spring.quiz_online.repositories.AccountRepository;
import io.spring.quiz_online.service.AccountService;
import io.spring.quiz_online.service.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/manager")
public class MangerController {

    private AuthenticationFacade authenticationFacade;
    private AccountService accountService;

    public MangerController(AuthenticationFacade authenticationFacade, AccountService accountService) {
        this.authenticationFacade = authenticationFacade;
        this.accountService = accountService;

    }

    @GetMapping("/get-manager-info")
    public AccountDto getMangerInfo() {
        String username = authenticationFacade.getAuthentication().getName();
        return accountService.findByUsername(username);
    }


    @GetMapping("/non-active-students")
    public List<AccountDto> getStudentsInactivated() {
        return accountService.findAccountsNotEnabled();
    }

    @DeleteMapping("/delete-account/{accountId}")
    public void deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
    }

    @GetMapping("/get-account/{accountId}")
    public AccountDto getStudentById(@PathVariable Long accountId){
        return accountService.findById(accountId);
    }

    @PutMapping("/update-account")
    public ResultMsg updateAccount(@RequestBody AccountDto accountDto){
        accountService.updateAccount(accountDto);
        return new ResultMsg( " اعمال تغییرات با موفقیت انجام شد. " , true);
    }


}
