package com.project.home24.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.home24.dto.account.CreateAccountRequest;
import com.project.home24.entity.Account;
import com.project.home24.repository.AccountRepository;
import com.project.home24.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    private final static String EMAIL = "test@test.com";
    private final static String PASSWORD = "password";
    private final static String TOKEN = "token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void createSuccessful() throws Exception {

        when(accountService.handleTokenCreation(any())).thenReturn(getTestAccount());

        mockMvc.perform(MockMvcRequestBuilders.post("/token")
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(getTestAccountRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("token").isNotEmpty());

    }

    @Test
    public void getAccountSuccessful() throws Exception {
        when(accountRepository.getAccountByToken(anyString())).thenReturn(getTestAccount());

        mockMvc.perform(MockMvcRequestBuilders.get("/account")
                .header("X-Auth-Token", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").isNotEmpty())
                .andExpect(jsonPath("token").isNotEmpty());

        verify(accountRepository, times(1)).getAccountByToken(anyString());
    }

    private String toJson(CreateAccountRequest createAccountRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(createAccountRequest);
    }

    private Account getTestAccount() {
        Account account = new Account();
        account.setPassword(PASSWORD);
        account.setEmail(EMAIL);
        account.setToken(TOKEN);
        return account;
    }

    private CreateAccountRequest getTestAccountRequest() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setEmail(EMAIL);
        createAccountRequest.setPassword(PASSWORD);
        return createAccountRequest;
    }
}
