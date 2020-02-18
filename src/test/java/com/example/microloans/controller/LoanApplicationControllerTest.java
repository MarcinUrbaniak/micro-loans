package com.example.microloans.controller;

import com.example.microloans.api.response.CreateUserAccountResponse;
import com.example.microloans.api.response.LoanApplicationResponse;
import com.example.microloans.model.LoanApplication;
import com.example.microloans.model.UserAccount;
import com.example.microloans.model.type.LoanValueParameter;
import com.example.microloans.repository.LoanApplicationRepository;
import com.example.microloans.repository.LoanRepository;
import com.example.microloans.repository.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.*;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableConfigurationProperties
public class LoanApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private Long userAccountId;

    @Test
    public void isCorrect_LoanApplication_ShouldReturnHttpCode200AndAddApplication() throws Exception {
        loanApplicationRepository.deleteAll();
        // create UserAccount
        MvcResult mvcResultUserAccount = mockMvc
                .perform(
                        post("/user-account/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"email\": \"michal@o2.pl\",\n" +
                                        "  \"firstName\": \" Andrzej \",\n" +
                                        "  \"lastName\": \"Wolski\"\n" +
                                        "}")
                )
                .andReturn();

        ObjectMapper objectMapperUserAccount = new ObjectMapper();
        CreateUserAccountResponse createUserAccountResponse = objectMapperUserAccount.readValue(
                mvcResultUserAccount.getResponse().getContentAsString(),
                CreateUserAccountResponse.class);

        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(createUserAccountResponse.getUserId());
        optionalUserAccount.ifPresent(userAccount -> userAccountId = userAccount.getId());

        MvcResult mvcResultLoanApplication = mockMvc
                .perform(
                        post("/loan-application/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"amount\": 2000,\n" +
                                        "  \"loanPeriod\": 14,\n" +
                                        "  \"userId\": " + userAccountId + "\n" +
                                        "}")
                )
                .andExpect(status().is(200))
                .andExpect(content().string(Matchers.containsString("Twoj wniosek o pozyczke zostal rozpatrzony pozytywnie. Pozyczka zostala uruchomiona.")))
                .andExpect(content().string(Matchers.containsString("loanApplicationId")))
                .andReturn();
        ObjectMapper objectMapperLoanApplication = new ObjectMapper();
        LoanApplicationResponse loanApplicationResponse = objectMapperLoanApplication.readValue(
                mvcResultLoanApplication.getResponse().getContentAsString(), LoanApplicationResponse.class);
        Optional<LoanApplication> optionalLoanApplication = loanApplicationRepository.findById(loanApplicationResponse.getLoanApplicationId());
        Assert.assertTrue(optionalLoanApplication.isPresent());
        String ip = optionalLoanApplication.get().getIpAddress();
        System.out.println("ip = " + ip);

    }

    @Test
    public void ifThirdLoanApplicationFromIp_ShouldRejectLoanApplication() throws Exception {
        loanApplicationRepository.deleteAll();

        // create UserAccount
        MvcResult mvcResultUserAccount = mockMvc
                .perform(
                        post("/user-account/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"email\": \"michal@o2.pl\",\n" +
                                        "  \"firstName\": \" Andrzej \",\n" +
                                        "  \"lastName\": \"Wolski\"\n" +
                                        "}")
                )
                .andReturn();

        ObjectMapper objectMapperUserAccount = new ObjectMapper();
        CreateUserAccountResponse createUserAccountResponse = objectMapperUserAccount.readValue(
                mvcResultUserAccount.getResponse().getContentAsString(),
                CreateUserAccountResponse.class);

        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(createUserAccountResponse.getUserId());
        optionalUserAccount.ifPresent(userAccount -> userAccountId = userAccount.getId());

        //Create first LoanApplication
        mockMvc
                .perform(
                        post("/loan-application/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"amount\": 2000,\n" +
                                        "  \"loanPeriod\": 14,\n" +
                                        "  \"userId\": " + userAccountId + "\n" +
                                        "}")
                );
        //Create second LoanApplication
        mockMvc
                .perform(
                        post("/loan-application/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"amount\": 2000,\n" +
                                        "  \"loanPeriod\": 14,\n" +
                                        "  \"userId\": " + userAccountId + "\n" +
                                        "}")
                );
        //Create third LoanApplication
        mockMvc
                .perform(
                        post("/loan-application/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"amount\": 2000,\n" +
                                        "  \"loanPeriod\": 14,\n" +
                                        "  \"userId\": " + userAccountId + "\n" +
                                        "}")
                )
                .andExpect(status().is(400))
                .andExpect(content().json("{\n" +
                        "  \"rejectCode\": \"REJ001\",\n" +
                        "  \"riskMsg\": \"Wniosek rozpatrzony negatywnie.\",\n" +
                        "  \"status\": \"REJECTED\"\n" +
                        "}"));

    }

    @Test
    public void ifAmountIsMaxAndLoanApplicationIsBetween0And6Hour_shouldRejectLoanApplication() throws Exception {
        loanApplicationRepository.deleteAll();

        // create UserAccount
        MvcResult mvcResultUserAccount = mockMvc
                .perform(
                        post("/user-account/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"email\": \"michal@o2.pl\",\n" +
                                        "  \"firstName\": \" Andrzej \",\n" +
                                        "  \"lastName\": \"Wolski\"\n" +
                                        "}")
                )
                .andReturn();

        ObjectMapper objectMapperUserAccount = new ObjectMapper();
        CreateUserAccountResponse createUserAccountResponse = objectMapperUserAccount.readValue(
                mvcResultUserAccount.getResponse().getContentAsString(),
                CreateUserAccountResponse.class);

        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(createUserAccountResponse.getUserId());
        optionalUserAccount.ifPresent(userAccount -> userAccountId = userAccount.getId());

        mockMvc
                .perform(
                        post("/loan-application/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"amount\": " + LoanValueParameter.MAX_VALUE.getValue() + ",\n" +
                                        "  \"loanPeriod\": 14,\n" +
                                        "  \"userId\": " + userAccountId + "\n" +
                                        "}"))
                .andExpect(status().is(400))
                .andExpect(content().json("{\n" +
                        "  \"rejectCode\": \"REJ001\",\n" +
                        "  \"riskMsg\": \"Wniosek rozpatrzony negatywnie.\",\n" +
                        "  \"status\": \"REJECTED\"\n" +
                        "}"));
    }


}