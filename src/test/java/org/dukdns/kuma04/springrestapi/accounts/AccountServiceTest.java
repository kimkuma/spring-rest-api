package org.dukdns.kuma04.springrestapi.accounts;

import org.dukdns.kuma04.springrestapi.common.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountServiceTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void findByUsername() {
        //Given
        String password = "kuma";
        String username = "kimkuma04@email.com";

        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.USER))
                .build();

        //this.accountService.saveAccount(account);

        // When
        UserDetailsService userDetailsService =  accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertThat(this.passwordEncoder.matches(password,userDetails.getPassword())).isTrue();
    }

    @Test
    public void findByUsernameFail() {
        assertThrows(UsernameNotFoundException.class, () -> {
            accountService.loadUserByUsername("random@email.com");
        });

    }
}