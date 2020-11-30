package org.dukdns.kuma04.springrestapi.config;

import org.dukdns.kuma04.springrestapi.accounts.Account;
import org.dukdns.kuma04.springrestapi.accounts.AccountRole;
import org.dukdns.kuma04.springrestapi.accounts.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 기본적으로 구동될때 데이터를 만들어줌
     * @return
     */
    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account kuma = Account.builder()
                        .email("kimkuma04@email.com")
                        .password("kuma")
                        .roles(Set.of(AccountRole.ADMIN,AccountRole.USER))
                        .build();

                accountService.saveAccount(kuma);
            }
        };
    }
}
