package org.dukdns.kuma04.springrestapi.config;

import org.dukdns.kuma04.springrestapi.accounts.Account;
import org.dukdns.kuma04.springrestapi.accounts.AccountRole;
import org.dukdns.kuma04.springrestapi.accounts.AccountService;
import org.dukdns.kuma04.springrestapi.common.AppProperties;
import org.dukdns.kuma04.springrestapi.common.BaseControllerTest;
import org.dukdns.kuma04.springrestapi.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AppProperties appProperties;

    @Test
    @TestDescription("인증 토큰을 발급받는 테스트")
    public void getAuthToken() throws Exception {
        //Given
//
//        Account kuma = Account.builder()
//                .email(appProperties.getUserUsername())
//                .password(appProperties.getUserPassword())
//                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
//                .build();
//        this.accountService.saveAccount(kuma);

        this.mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                    .param("username",appProperties.getUserUsername())
                    .param("password",appProperties.getUserPassword())
                    .param("grant_type","password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }
}