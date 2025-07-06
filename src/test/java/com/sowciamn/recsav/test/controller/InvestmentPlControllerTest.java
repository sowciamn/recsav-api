package com.sowciamn.recsav.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.sowciamn.recsav.model.entity.InvestmentPl;
import com.sowciamn.recsav.model.request.InvestmentPlRequest;
import com.sowciamn.recsav.service.InvestmentPlService;
import com.sowciamn.recsav.controller.InvestmentPlController;

@WebMvcTest(InvestmentPlController.class)
public class InvestmentPlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvestmentPlService investmentPlService;

    private InvestmentPl investmentPl1;
    private InvestmentPl investmentPl2;
    private InvestmentPlRequest investmentPlRequest;

    @BeforeEach
    void setUp() {
        investmentPl1 = new InvestmentPl();
        investmentPl1.setInvestmentPlYearMonth(LocalDate.of(2024, 6, 1));
        investmentPl1.setDepositAccountCd("INV1");
        investmentPl1.setInvestmentPlAmount(new BigDecimal("50000"));
        investmentPl1.setInvestmentPlRemarks("6月投資損益1");

        investmentPl2 = new InvestmentPl();
        investmentPl2.setInvestmentPlYearMonth(LocalDate.of(2024, 6, 1));
        investmentPl2.setDepositAccountCd("INV2");
        investmentPl2.setInvestmentPlAmount(new BigDecimal("100000"));
        investmentPl2.setInvestmentPlRemarks("6月投資損益2");

        investmentPlRequest = new InvestmentPlRequest();
        investmentPlRequest.setInvestmentPlYearMonth(LocalDate.of(2024, 7, 1));
        investmentPlRequest.setDepositAccountCd("INV1");
        investmentPlRequest.setInvestmentPlAmount(new BigDecimal("60000"));
        investmentPlRequest.setInvestmentPlRemarks("7月投資損益");
    }

    @Test
    void getAllInvestmentPls_shouldReturnListOfInvestmentPls() throws Exception {
        List<InvestmentPl> allInvestmentPls = Arrays.asList(investmentPl1, investmentPl2);
        when(investmentPlService.findAllInvestmentPls()).thenReturn(allInvestmentPls);

        mockMvc.perform(get("/api/investment-pls"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].depositAccountCd").value(investmentPl1.getDepositAccountCd()))
                .andExpect(jsonPath("$[0].investmentPlAmount").value(investmentPl1.getInvestmentPlAmount()))
                .andExpect(jsonPath("$[1].depositAccountCd").value(investmentPl2.getDepositAccountCd()));
    }

    @Test
    void createInvestmentPl_shouldReturnCreatedInvestmentPl() throws Exception {
        InvestmentPl createdInvestmentPl = new InvestmentPl();
        createdInvestmentPl.setInvestmentPlYearMonth(investmentPlRequest.getInvestmentPlYearMonth());
        createdInvestmentPl.setDepositAccountCd(investmentPlRequest.getDepositAccountCd());
        createdInvestmentPl.setInvestmentPlAmount(investmentPlRequest.getInvestmentPlAmount());
        createdInvestmentPl.setInvestmentPlRemarks(investmentPlRequest.getInvestmentPlRemarks());

        when(investmentPlService.createInvestmentPl(any(InvestmentPlRequest.class))).thenReturn(createdInvestmentPl);

        mockMvc.perform(post("/api/investment-pls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(investmentPlRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.depositAccountCd").value(createdInvestmentPl.getDepositAccountCd()))
                .andExpect(jsonPath("$.investmentPlAmount").value(createdInvestmentPl.getInvestmentPlAmount()));
    }

    @Test
    void updateInvestmentPl_shouldReturnUpdatedInvestmentPl() throws Exception {
        LocalDate investmentPlYearMonthToUpdate = LocalDate.of(2024, 6, 1);
        String depositAccountCdToUpdate = "INV1";
        InvestmentPl updatedInvestmentPl = new InvestmentPl();
        updatedInvestmentPl.setInvestmentPlYearMonth(investmentPlYearMonthToUpdate);
        updatedInvestmentPl.setDepositAccountCd(depositAccountCdToUpdate);
        updatedInvestmentPl.setInvestmentPlAmount(new BigDecimal("55000"));
        updatedInvestmentPl.setInvestmentPlRemarks("更新6月投資損益");

        InvestmentPlRequest updateRequest = new InvestmentPlRequest();
        updateRequest.setInvestmentPlYearMonth(investmentPlYearMonthToUpdate);
        updateRequest.setDepositAccountCd(depositAccountCdToUpdate);
        updateRequest.setInvestmentPlAmount(new BigDecimal("55000"));
        updateRequest.setInvestmentPlRemarks("更新6月投資損益");

        when(investmentPlService.updateInvestmentPl(eq(investmentPlYearMonthToUpdate), eq(depositAccountCdToUpdate), any(InvestmentPlRequest.class))).thenReturn(updatedInvestmentPl);

        mockMvc.perform(put("/api/investment-pls/{investmentPlYearMonth}/{depositAccountCd}", investmentPlYearMonthToUpdate, depositAccountCdToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.depositAccountCd").value(updatedInvestmentPl.getDepositAccountCd()))
                .andExpect(jsonPath("$.investmentPlAmount").value(updatedInvestmentPl.getInvestmentPlAmount()));
    }

    @Test
    void deleteInvestmentPl_shouldReturnNoContent() throws Exception {
        LocalDate investmentPlYearMonthToDelete = LocalDate.of(2024, 6, 1);
        String depositAccountCdToDelete = "INV1";
        doNothing().when(investmentPlService).deleteInvestmentPl(investmentPlYearMonthToDelete, depositAccountCdToDelete);

        mockMvc.perform(delete("/api/investment-pls/{investmentPlYearMonth}/{depositAccountCd}", investmentPlYearMonthToDelete, depositAccountCdToDelete))
                .andExpect(status().isNoContent());
    }
}
