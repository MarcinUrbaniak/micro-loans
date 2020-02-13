package com.example.microloans.controller;

import com.example.microloans.api.response.CreateUserAccountResponse;
import com.example.microloans.api.response.LoanApplicationResponse;
import com.example.microloans.model.Loan;
import com.example.microloans.model.LoanApplication;
import com.example.microloans.model.UserAccount;
import com.example.microloans.repository.LoanApplicationRepository;
import com.example.microloans.repository.LoanRepository;
import com.example.microloans.repository.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    Long userAccountId;
    Long loanId;

    @Test
    public void ifCorrectRequestForDeferral_ShouldReturnHttpCode200AndChangeEndDate() throws Exception {
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

        //create LoanApplication
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
                .andReturn();
        ObjectMapper objectMapperLoanApplication = new ObjectMapper();
        LoanApplicationResponse loanApplicationResponse = objectMapperLoanApplication.readValue(
                mvcResultLoanApplication.getResponse().getContentAsString(), LoanApplicationResponse.class);
        Optional<LoanApplication> optionalLoanApplication = loanApplicationRepository.findById(loanApplicationResponse.getLoanApplicationId());
        loanId = optionalLoanApplication.get().getLoan().getId();
        LocalDate previousEndDate = loanRepository.findById(loanId).get().getEndDate();


        mockMvc
                .perform(
                        post("/loan/extension")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"extension\": 14,\n" +
                                        "  \"loanId\": " + loanId + "\n" +
                                        "}")
                )
                .andExpect(status().is(200))
                .andExpect(content().json("{\n" +
                        "  \"responseMsg\": \"Twoj wniosek o prolongate splaty zostal rozpatrzony pozytywnie.\",\n" +
                        "  \"status\": \"ACCEPTED\",\n" +
                        "  \"loanId\": " + loanId + "\n" +
                        "}"));
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        Assert.assertEquals(previousEndDate.plusDays(14), optionalLoan.get().getEndDate());
    }

    @Test
    public void ifIncorrectRequestForDeferral_ShouldReturnHttpCode400AndRejectDeferral() throws Exception {
        // create UserAccount
        loanApplicationRepository.deleteAll();
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
        userAccountId = optionalUserAccount.get().getId();

        //create LoanApplication
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
                .andReturn();
        ObjectMapper objectMapperLoanApplication2 = new ObjectMapper();
        LoanApplicationResponse loanApplicationResponse = objectMapperLoanApplication2.readValue(
                mvcResultLoanApplication.getResponse().getContentAsString(), LoanApplicationResponse.class);
        Optional<LoanApplication> optionalLoanApplication = loanApplicationRepository.findById(loanApplicationResponse.getLoanApplicationId());
        loanId = optionalLoanApplication.get().getId();


        //check deferral
        mockMvc
                .perform(
                        post("/loan/extension")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"extension\": 15,\n" +
                                        "  \"loanId\": " + loanId + "\n" +
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
    public void ifSecondDeferralApplication_shouldReturnHttpCode400AndRejectDeferral() throws Exception {
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
        userAccountId = optionalUserAccount.get().getId();

        //create LoanApplication
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
                .andReturn();
        ObjectMapper objectMapperLoanApplication = new ObjectMapper();
        LoanApplicationResponse loanApplicationResponse = objectMapperLoanApplication.readValue(
                mvcResultLoanApplication.getResponse().getContentAsString(), LoanApplicationResponse.class);
        Optional<LoanApplication> optionalLoanApplication = loanApplicationRepository.findById(loanApplicationResponse.getLoanApplicationId());
        loanId = optionalLoanApplication.get().getLoan().getId();
        optionalLoanApplication.get().setIpAddress("0.2");

        //first deferral
        mockMvc
                .perform(
                        post("/loan/extension")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"extension\": 14,\n" +
                                        "  \"loanId\": " + loanId + "\n" +
                                        "}")
                );
        //second deferral
        mockMvc
                .perform(
                        post("/loan/extension")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "  \"extension\": 14,\n" +
                                        "  \"loanId\": " + loanId + "\n" +
                                        "}")
                )
                .andExpect(status().is(400))
                .andExpect(content().json("{\n" +
                        "  \"rejectCode\": \"REJ001\",\n" +
                        "  \"riskMsg\": \"Wniosek rozpatrzony negatywnie.\",\n" +
                        "  \"status\": \"REJECTED\"\n" +
                        "}"));
        Assert.assertTrue(loanRepository.findById(loanId).get().isDeferral());

    }
}