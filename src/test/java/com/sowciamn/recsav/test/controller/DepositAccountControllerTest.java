package com.sowciamn.recsav.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sowciamn.recsav.model.entity.DepositAccount;
import com.sowciamn.recsav.model.request.DepositAccountRequest;
import com.sowciamn.recsav.service.DepositAccountService;
import com.sowciamn.recsav.controller.DepositAccountController;

@WebMvcTest(DepositAccountController.class)
public class DepositAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DepositAccountService depositAccountService;

    private DepositAccount depositAccount1;
    private DepositAccount depositAccount2;
    private DepositAccountRequest depositAccountRequest;

    @BeforeEach
    void setUp() {
        depositAccount1 = new DepositAccount();
        depositAccount1.setDepositAccountCd("BK1");
        depositAccount1.setDepositAccountNm("銀行口座1");
        depositAccount1.setDepositUsage("生活費");
        depositAccount1.setInvestmentAccountFlg("0");

        depositAccount2 = new DepositAccount();
        depositAccount2.setDepositAccountCd("INV");
        depositAccount2.setDepositAccountNm("投資口座");
        depositAccount2.setDepositUsage("投資用");
        depositAccount2.setInvestmentAccountFlg("1");

        depositAccountRequest = new DepositAccountRequest();
        depositAccountRequest.setDepositAccountCd("BK2");
        depositAccountRequest.setDepositAccountNm("銀行口座2");
        depositAccountRequest.setDepositUsage("貯蓄用");
        depositAccountRequest.setInvestmentAccountFlg("0");
    }

    @Test
    void getAllDepositAccounts_shouldReturnListOfDepositAccounts() throws Exception {
        List<DepositAccount> allDepositAccounts = Arrays.asList(depositAccount1, depositAccount2);
        when(depositAccountService.findAllDepositAccounts()).thenReturn(allDepositAccounts);

        mockMvc.perform(get("/api/deposit-accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].depositAccountCd").value(depositAccount1.getDepositAccountCd()))
                .andExpect(jsonPath("$[0].depositAccountNm").value(depositAccount1.getDepositAccountNm()))
                .andExpect(jsonPath("$[1].depositAccountCd").value(depositAccount2.getDepositAccountCd()));
    }

    @Test
    void createDepositAccount_shouldReturnCreatedDepositAccount() throws Exception {
        DepositAccount createdDepositAccount = new DepositAccount();
        createdDepositAccount.setDepositAccountCd(depositAccountRequest.getDepositAccountCd());
        createdDepositAccount.setDepositAccountNm(depositAccountRequest.getDepositAccountNm());
        createdDepositAccount.setDepositUsage(depositAccountRequest.getDepositUsage());
        createdDepositAccount.setInvestmentAccountFlg(depositAccountRequest.getInvestmentAccountFlg());

        when(depositAccountService.createDepositAccount(any(DepositAccountRequest.class))).thenReturn(createdDepositAccount);

        mockMvc.perform(post("/api/deposit-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositAccountRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.depositAccountCd").value(createdDepositAccount.getDepositAccountCd()))
                .andExpect(jsonPath("$.depositAccountNm").value(createdDepositAccount.getDepositAccountNm()));
    }

    @Test
    void updateDepositAccount_shouldReturnUpdatedDepositAccount() throws Exception {
        String depositAccountCdToUpdate = "BK1";
        DepositAccount updatedDepositAccount = new DepositAccount();
        updatedDepositAccount.setDepositAccountCd(depositAccountCdToUpdate);
        updatedDepositAccount.setDepositAccountNm("更新銀行口座1");
        updatedDepositAccount.setDepositUsage("更新生活費");
        updatedDepositAccount.setInvestmentAccountFlg("0");

        DepositAccountRequest updateRequest = new DepositAccountRequest();
        updateRequest.setDepositAccountCd(depositAccountCdToUpdate);
        updateRequest.setDepositAccountNm("更新銀行口座1");
        updateRequest.setDepositUsage("更新生活費");
        updateRequest.setInvestmentAccountFlg("0");

        when(depositAccountService.updateDepositAccount(eq(depositAccountCdToUpdate), any(DepositAccountRequest.class))).thenReturn(updatedDepositAccount);

        mockMvc.perform(put("/api/deposit-accounts/{depositAccountCd}", depositAccountCdToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.depositAccountCd").value(updatedDepositAccount.getDepositAccountCd()))
                .andExpect(jsonPath("$.depositAccountNm").value(updatedDepositAccount.getDepositAccountNm()));
    }

    @Test
    void deleteDepositAccount_shouldReturnNoContent() throws Exception {
        String depositAccountCdToDelete = "BK1";
        doNothing().when(depositAccountService).deleteDepositAccount(depositAccountCdToDelete);

        mockMvc.perform(delete("/api/deposit-accounts/{depositAccountCd}", depositAccountCdToDelete))
                .andExpect(status().isNoContent());
    }
}
