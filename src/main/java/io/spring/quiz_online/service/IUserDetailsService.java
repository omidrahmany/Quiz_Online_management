package io.spring.quiz_online.service;

import io.spring.quiz_online.config.IUserDetails;
import io.spring.quiz_online.model.Account;
import io.spring.quiz_online.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class IUserDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Autowired
    public IUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findAccountByUsername(s);
        optionalAccount.orElseThrow(() -> new UsernameNotFoundException(String.format("The account by username %s not found.", s)));
        return optionalAccount.map(IUserDetails::new).get();
    }
}
