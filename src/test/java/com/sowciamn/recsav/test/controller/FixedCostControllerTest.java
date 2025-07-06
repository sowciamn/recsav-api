package com.sowciamn.recsav.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
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
import com.sowciamn.recsav.model.entity.FixedCost;
import com.sowciamn.recsav.model.request.FixedCostRequest;
import com.sowciamn.recsav.service.FixedCostService;
import com.sowciamn.recsav.controller.FixedCostController;

@WebMvcTest(FixedCostController.class)
public class FixedCostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FixedCostService fixedCostService;

    private FixedCost fixedCost1;
    private FixedCost fixedCost2;
    private FixedCostRequest fixedCostRequest;

    @BeforeEach
    void setUp() {
        fixedCost1 = new FixedCost();
        fixedCost1.setFixedCostSeq(1L);
        fixedCost1.setCategoryCd(1);
        fixedCost1.setFixedCostDetails("家賃");
        fixedCost1.setDisplayOrder(10);
        fixedCost1.setJanuary(new BigDecimal("80000"));
        fixedCost1.setFebruary(new BigDecimal("80000"));

        fixedCost2 = new FixedCost();
        fixedCost2.setFixedCostSeq(2L);
        fixedCost2.setCategoryCd(2);
        fixedCost2.setFixedCostDetails("通信費");
        fixedCost2.setDisplayOrder(20);
        fixedCost2.setJanuary(new BigDecimal("5000"));
        fixedCost2.setFebruary(new BigDecimal("5000"));

        fixedCostRequest = new FixedCostRequest();
        fixedCostRequest.setCategoryCd(3);
        fixedCostRequest.setFixedCostDetails("サブスク");
        fixedCostRequest.setDisplayOrder(30);
        fixedCostRequest.setJanuary(new BigDecimal("1000"));
        fixedCostRequest.setFebruary(new BigDecimal("1000"));
    }

    @Test
    void getAllFixedCosts_shouldReturnListOfFixedCosts() throws Exception {
        List<FixedCost> allFixedCosts = Arrays.asList(fixedCost1, fixedCost2);
        when(fixedCostService.findAllFixedCosts()).thenReturn(allFixedCosts);

        mockMvc.perform(get("/api/fixed-costs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].fixedCostSeq").value(fixedCost1.getFixedCostSeq()))
                .andExpect(jsonPath("$[0].fixedCostDetails").value(fixedCost1.getFixedCostDetails()))
                .andExpect(jsonPath("$[1].fixedCostSeq").value(fixedCost2.getFixedCostSeq()));
    }

    @Test
    void createFixedCost_shouldReturnCreatedFixedCost() throws Exception {
        FixedCost createdFixedCost = new FixedCost();
        createdFixedCost.setFixedCostSeq(3L);
        createdFixedCost.setCategoryCd(fixedCostRequest.getCategoryCd());
        createdFixedCost.setFixedCostDetails(fixedCostRequest.getFixedCostDetails());
        createdFixedCost.setDisplayOrder(fixedCostRequest.getDisplayOrder());
        createdFixedCost.setJanuary(fixedCostRequest.getJanuary());
        createdFixedCost.setFebruary(fixedCostRequest.getFebruary());

        when(fixedCostService.createFixedCost(any(FixedCostRequest.class))).thenReturn(createdFixedCost);

        mockMvc.perform(post("/api/fixed-costs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fixedCostRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fixedCostSeq").value(createdFixedCost.getFixedCostSeq()))
                .andExpect(jsonPath("$.fixedCostDetails").value(createdFixedCost.getFixedCostDetails()));
    }

    @Test
    void updateFixedCost_shouldReturnUpdatedFixedCost() throws Exception {
        Long fixedCostSeqToUpdate = 1L;
        FixedCost updatedFixedCost = new FixedCost();
        updatedFixedCost.setFixedCostSeq(fixedCostSeqToUpdate);
        updatedFixedCost.setCategoryCd(1);
        updatedFixedCost.setFixedCostDetails("更新家賃");
        updatedFixedCost.setDisplayOrder(10);
        updatedFixedCost.setJanuary(new BigDecimal("85000"));
        updatedFixedCost.setFebruary(new BigDecimal("85000"));

        FixedCostRequest updateRequest = new FixedCostRequest();
        updateRequest.setCategoryCd(1);
        updateRequest.setFixedCostDetails("更新家賃");
        updateRequest.setDisplayOrder(10);
        updateRequest.setJanuary(new BigDecimal("85000"));
        updateRequest.setFebruary(new BigDecimal("85000"));

        when(fixedCostService.updateFixedCost(eq(fixedCostSeqToUpdate), any(FixedCostRequest.class))).thenReturn(updatedFixedCost);

        mockMvc.perform(put("/api/fixed-costs/{fixedCostSeq}", fixedCostSeqToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fixedCostSeq").value(updatedFixedCost.getFixedCostSeq()))
                .andExpect(jsonPath("$.fixedCostDetails").value(updatedFixedCost.getFixedCostDetails()));
    }

    @Test
    void deleteFixedCost_shouldReturnNoContent() throws Exception {
        Long fixedCostSeqToDelete = 1L;
        doNothing().when(fixedCostService).deleteFixedCost(fixedCostSeqToDelete);

        mockMvc.perform(delete("/api/fixed-costs/{fixedCostSeq}", fixedCostSeqToDelete))
                .andExpect(status().isNoContent());
    }
}
