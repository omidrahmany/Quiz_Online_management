package io.spring.quiz_online.service;

import io.spring.quiz_online.model.*;
import io.spring.quiz_online.repositories.AccountRepository;
import io.spring.quiz_online.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IAccountService implements AccountService {

    private RoleRepository roleRepository;
    private AccountRepository accountRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public IAccountService(RoleRepository roleRepository, AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void register(RegisteredUserInfo registeredUserInfo) {
//        Optional<Account> account = accountRepository.findAccountByUsername(registeredUserInfo.getUsername());
        Role role = roleRepository.findByRole(RoleEnum.valueOf(registeredUserInfo.getRole()));
        Person person = null;
        if (role.getRole().equals(RoleEnum.STUDENT))
            person = new Student(registeredUserInfo.getFirstName(), registeredUserInfo.getLastName(), null);
        else if (role.getRole().equals(RoleEnum.TEACHER))
            person = new Teacher(registeredUserInfo.getFirstName(), registeredUserInfo.getLastName(), null);
        else if (role.getRole().equals(RoleEnum.MANAGER))
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

    @Override
    public Optional<Account> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public List<Account> findAccountsByRoleType(RoleEnum roleType) {
        Role role = roleRepository.findByRole(roleType);
        return accountRepository.findAccountsByRole(role);
    }

    @Override
    public void deleteAccountsByRole(RoleEnum roleType) {
        Role role = roleRepository.findByRole(roleType);
        accountRepository.deleteAccountsByRole(role);
    }


}
