package io.spring.quiz_online.controller;

import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.repositories.AccountRepository;
import io.spring.quiz_online.service.AccountService;
import io.spring.quiz_online.service.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/manager")
public class MangerPanelController {

    private AuthenticationFacade authenticationFacade;
    private AccountService accountService;

    public MangerPanelController(AuthenticationFacade authenticationFacade, AccountService accountService) {
        this.authenticationFacade = authenticationFacade;
        this.accountService = accountService;

    }

    @GetMapping("/get-manager-info")
    public AccountDto getMangerInfo() {
        String username = authenticationFacade.getAuthentication().getName();
        Optional<Account> account = accountService.findByUsername(username);
        return account
                .map(value -> AccountDto.getInstance()
                        .setFirstName(value.getPerson().getFirstName())
                        .setLastName(value.getPerson().getLastName())
                        .setUsername(value.getUsername())
//                    .setIsEnable(account.get().isEnabled())
//                    .setRoleType(String.valueOf(account.get().getRole().getRoleType()))
                        .createAccountDto())
                .orElse(null);
    }

    @GetMapping("/non-active-students")
    public List<AccountDto> getStudentsInactivated() {
        return accountService.findAccountsNotEnabled();
    }

    @DeleteMapping("/delete-account/{accountId}")
    public void deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
    }
}
