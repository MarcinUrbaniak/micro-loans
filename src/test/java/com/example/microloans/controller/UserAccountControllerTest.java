package com.example.microloans.controller;

import com.example.microloans.api.response.CreateUserAccountResponse;
import com.example.microloans.model.UserAccount;
import com.example.microloans.repository.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void ifCreateAccountRequestShouldReturnHttpCode200AndCreateAccount() throws Exception{
        MvcResult mvcResult = mockMvc
                .perform(
                        post("/user-account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\": \"michal@o2.pl\",\n" +
                                "  \"firstName\": \"Michal\",\n" +
                                "  \"lastName\": \"Wolski\"\n" +
                                "}")
                )
                .andExpect(status().is(200))
                .andExpect(content().string(Matchers.containsString("Poprawnie utworzono konto uzytkownika.")))
                .andExpect(content().string(Matchers.containsString("userId")))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserAccountResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CreateUserAccountResponse.class
        );
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(response.getUserId());
        Assert.assertTrue(optionalUserAccount.isPresent());
    }

}