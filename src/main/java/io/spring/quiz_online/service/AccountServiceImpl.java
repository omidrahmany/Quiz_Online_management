package io.spring.quiz_online.service;

import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.dto.SignInAccountDto;
import io.spring.quiz_online.model.*;
import io.spring.quiz_online.repositories.AccountRepository;
import io.spring.quiz_online.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private RoleRepository roleRepository;
    private AccountRepository accountRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountServiceImpl(RoleRepository roleRepository, AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void register(RegisteredUserInfo registeredUserInfo) {
        RoleEnum roleType = getEnumRoleType(registeredUserInfo.getRoleType());
        Role role = roleRepository.findByRoleType(roleType);
        Person person = null;
        assert roleType != null;
        if (roleType.equals(RoleEnum.ROLE_STUDENT))
            person = new Student(registeredUserInfo.getFirstName(), registeredUserInfo.getLastName(), null);
        else if (roleType.equals(RoleEnum.ROLE_TEACHER))
            person = new Teacher(registeredUserInfo.getFirstName(), registeredUserInfo.getLastName(), null);
        else if (roleType.equals(RoleEnum.ROLE_MANAGER))
            person = new Manager(registeredUserInfo.getFirstName(), registeredUserInfo.getLastName(), null);

        Account account = Account.getAccountBuilder()
                .setUsername(registeredUserInfo.getUsername())
                .setPassword(bCryptPasswordEncoder.encode(registeredUserInfo.getPassword()))
                .setEmail(registeredUserInfo.getEmail())
                .setEnabled(registeredUserInfo.isEnable())
                .setRole(role)
                .setPerson(person)
                .createAccount();
        accountRepository.save(account);
    }

    private RoleEnum getEnumRoleType(String roleType) {
        switch (roleType) {
            case "student":
                return RoleEnum.ROLE_STUDENT;
            case "teacher":
                return RoleEnum.ROLE_TEACHER;
            case "manager":
                return RoleEnum.ROLE_MANAGER;
            case "super_admin":
                return RoleEnum.ROLE_SUPER_ADMIN;
            default:
                return null;
        }
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    @Override
    public List<Account> findAccountsByRoleType(RoleEnum roleType) {
        Role role = roleRepository.findByRoleType(roleType);
        return accountRepository.findAccountsByRole(role);
    }

    @Override
    public void deleteAccountsByRole(RoleEnum roleType) {
        Role role = roleRepository.findByRoleType(roleType);
        accountRepository.deleteAccountsByRole(role);
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    @Override
    public AccountDto findAccountByUsernameAndPassword(String username, String password) {
        Optional<Account> account = accountRepository.findAccountByUsernameAndPassword(username, password);
        return account.map(value -> AccountDto.getInstance()
                .setUsername(value.getUsername())
                .setRoleType(String.valueOf(value.getRole().getRoleType()))
                .setIsEnable(value.isEnabled())
                .setFirstName(value.getPerson().getFirstName())
                .setLastName(value.getPerson().getLastName())
                .createAccountDto()).orElse(null);
    }

    @Override
    public List<AccountDto> findAccountsNotEnabled() {
        Optional<List<Account>> accountsNotEnabled = accountRepository.findAccountsByEnabledFalse();
        Function<Account, AccountDto> dtoFunction =
                account -> {
                    return AccountDto.getInstance()
                            .setAccountId(account.getAccountId())
                            .setRoleType(stringRole(account.getRole().getRoleType()))
                            .setIsEnable(account.isEnabled())
                            .setUsername(account.getUsername())
                            .setLastName(account.getPerson().getLastName())
                            .setFirstName(account.getPerson().getFirstName())
                            .setEmail(account.getEmail())
                            .createAccountDto();
                };
        return accountsNotEnabled
                .map(
                        accounts -> accounts
                                .stream()
                                .map(dtoFunction)
                                .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    private String stringRole(RoleEnum roleType) {
        return roleType == RoleEnum.ROLE_STUDENT ? "دانشجو" : "استاد";
    }
}
