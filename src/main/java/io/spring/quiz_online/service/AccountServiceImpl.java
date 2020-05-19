package io.spring.quiz_online.service;

import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.model.*;
import io.spring.quiz_online.repositories.AccountRepository;
import io.spring.quiz_online.repositories.PersonRepository;
import io.spring.quiz_online.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.InvalidRoleInfoException;
import javax.management.relation.InvalidRoleValueException;
import java.util.ArrayList;
import java.util.Collections;
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
        Person person;
        assert roleType != null;
        person = createNewPerson(roleType, registeredUserInfo.getFirstName(), registeredUserInfo.getLastName());

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

    private Person createNewPerson(RoleEnum roleType, String firstName, String lastName) {
        if (roleType.equals(RoleEnum.ROLE_STUDENT))
            return new Student(firstName, lastName, null);
        else if (roleType.equals(RoleEnum.ROLE_TEACHER))
            return new Teacher(firstName, lastName, null);
        else if (roleType.equals(RoleEnum.ROLE_MANAGER))
            return new Manager(firstName, lastName, null);
        return null;
    }

    @Override
    public void updateAccount(AccountDto accountDto) {

        Optional<Account> accountOptional = accountRepository.findById(accountDto.getAccountId());
        Role role = roleRepository.findByRoleType(getEnumRoleType(accountDto.getRoleType()));

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setUsername(accountDto.getUsername());
            account.setEmail(accountDto.getEmail());
            account.setEnabled(accountDto.isEnable());


            // compare saved person's role with new one
            // to update tables of Person's Children. (Teacher, Student or Manager Tables)
            // if equals ---> update first name and last name
            // otherwise ---> create new person and assign to account
            if (account.getRole().getRoleType().equals(role.getRoleType())) {
                account.getPerson().setLastName(accountDto.getLastName());
                account.getPerson().setFirstName(accountDto.getFirstName());

            } else {
                Person newPerson = createNewPerson(role.getRoleType(), accountDto.getFirstName(), accountDto.getLastName());
                account.setPerson(newPerson);
            }
            account.setRole(role);
            accountRepository.save(account);
        } else
            throw new UsernameNotFoundException(String.format("account by username %s not found.", accountDto.getUsername()));
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
    public AccountDto findByUsername(String username) {
        return accountRepository
                .findAccountByUsername(username)
                .map(mapAccountToAccountDtoFunction())
                .orElse(null);
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
    public AccountDto findAccountByEmail(String email) {
        return accountRepository
                .findAccountByEmail(email)
                .map(mapAccountToAccountDtoFunction())
                .orElse(null);
    }

    @Override
    public AccountDto findAccountByUsernameAndPassword(String username, String password) {
        Optional<Account> account = accountRepository.findAccountByUsernameAndPassword(username, password);
        return account.map(mapAccountToAccountDtoFunction()).orElse(null);
    }

    @Override
    public List<AccountDto> findAccountsNotEnabled() {
        Optional<List<Account>> accountsNotEnabled = accountRepository.findAccountsByEnabledFalse();
        return accountsNotEnabled
                .map(
                        accounts -> accounts
                                .stream()
                                .map(mapAccountToAccountDtoFunction())
                                .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDto> findAllAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(mapAccountToAccountDtoFunction())
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(Long accountId) {
        return accountRepository.findById(accountId).map(mapAccountToAccountDtoFunction()).orElse(null);

    }

    @Override
    public List<AccountDto> findAllTeachersAndStudentsRole() {
        Optional<List<Account>> students = accountRepository.findAllByRole_RoleType(RoleEnum.ROLE_STUDENT);
        Optional<List<Account>> teachers = accountRepository.findAllByRole_RoleType(RoleEnum.ROLE_TEACHER);
        List<AccountDto> result = new ArrayList<>();
        if(students.isPresent() && teachers.isPresent()) {
            List<AccountDto> studentsAccountDto = students.map(accounts -> accounts.stream()
                    .map(mapAccountToAccountDtoFunction()).collect(Collectors.toList())).get();
            List<AccountDto> teachersAccountDto = teachers.map(accounts -> accounts.stream()
                    .map(mapAccountToAccountDtoFunction()).collect(Collectors.toList())).get();
            result.addAll(studentsAccountDto);
            result.addAll(teachersAccountDto);
        }
        else if(students.isPresent()){
            result = students.map(accounts -> accounts.stream()
                    .map(mapAccountToAccountDtoFunction()).collect(Collectors.toList())).get();
        }
        else if(teachers.isPresent()){
            result = teachers.map(accounts -> accounts.stream()
                    .map(mapAccountToAccountDtoFunction()).collect(Collectors.toList())).get();
        }
        Collections.sort(result);
        return result;
    }


    private String getStringRole(RoleEnum roleType) throws InvalidRoleValueException {
        if (roleType.equals(RoleEnum.ROLE_STUDENT)) return "student";
        else if (roleType.equals(RoleEnum.ROLE_TEACHER)) return "teacher";
        else if (roleType.equals(RoleEnum.ROLE_MANAGER)) return "manager";
        else if (roleType.equals(RoleEnum.ROLE_SUPER_ADMIN)) return "super-admin";
        throw new InvalidRoleValueException("Role Type is invalid one.");
    }

    private Function<Account, AccountDto> mapAccountToAccountDtoFunction() {
        return account -> {
            try {
                return AccountDto.getInstance()
                        .setAccountId(account.getAccountId())
                        .setRoleType(getStringRole(account.getRole().getRoleType()))
                        .setIsEnable(account.isEnabled())
                        .setUsername(account.getUsername())
                        .setLastName(account.getPerson().getLastName())
                        .setFirstName(account.getPerson().getFirstName())
                        .setEmail(account.getEmail())
                        .createAccountDto();
            } catch (InvalidRoleValueException e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
