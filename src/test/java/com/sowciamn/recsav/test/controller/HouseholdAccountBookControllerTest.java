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
import com.sowciamn.recsav.model.entity.HouseholdAccountBook;
import com.sowciamn.recsav.model.request.HouseholdAccountBookRequest;
import com.sowciamn.recsav.service.HouseholdAccountBookService;
import com.sowciamn.recsav.controller.HouseholdAccountBookController;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class HouseholdAccountBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HouseholdAccountBookService householdAccountBookService;

    private HouseholdAccountBook hab1;
    private HouseholdAccountBook hab2;
    private HouseholdAccountBookRequest habRequest;

    @BeforeEach
    void setUp() {
        hab1 = new HouseholdAccountBook();
        hab1.setHabSeq(1L);
        hab1.setActualDate(LocalDate.of(2024, 7, 1));
        hab1.setCategoryCd(1);
        hab1.setStoreCd(1);
        hab1.setAmount(new BigDecimal("1000"));
        hab1.setRemarks("食費");

        hab2 = new HouseholdAccountBook();
        hab2.setHabSeq(2L);
        hab2.setActualDate(LocalDate.of(2024, 7, 2));
        hab2.setCategoryCd(2);
        hab2.setStoreCd(2);
        hab2.setAmount(new BigDecimal("2000"));
        hab2.setRemarks("交通費");

        habRequest = new HouseholdAccountBookRequest();
        habRequest.setActualDate(LocalDate.of(2024, 7, 3));
        habRequest.setCategoryCd(3);
        habRequest.setStoreCd(3);
        habRequest.setAmount(new BigDecimal("3000"));
        habRequest.setRemarks("娯楽費");
    }

    @Test
    void getAllHouseholdAccountBooks_shouldReturnListOfHouseholdAccountBooks() throws Exception {
        List<HouseholdAccountBook> allHab = Arrays.asList(hab1, hab2);
        when(householdAccountBookService.findAllHouseholdAccountBooks()).thenReturn(allHab);

        mockMvc.perform(get("/api/household-account-books").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].habSeq").value(hab1.getHabSeq()))
                .andExpect(jsonPath("$[0].remarks").value(hab1.getRemarks()))
                .andExpect(jsonPath("$[1].habSeq").value(hab2.getHabSeq()));
    }

    @Test
    void createHouseholdAccountBook_shouldReturnCreatedHouseholdAccountBook() throws Exception {
        HouseholdAccountBook createdHab = new HouseholdAccountBook();
        createdHab.setHabSeq(3L);
        createdHab.setActualDate(habRequest.getActualDate());
        createdHab.setCategoryCd(habRequest.getCategoryCd());
        createdHab.setStoreCd(habRequest.getStoreCd());
        createdHab.setAmount(habRequest.getAmount());
        createdHab.setRemarks(habRequest.getRemarks());

        when(householdAccountBookService.createHouseholdAccountBook(any(HouseholdAccountBookRequest.class))).thenReturn(createdHab);

        mockMvc.perform(post("/api/household-account-books").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(habRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.habSeq").value(createdHab.getHabSeq()))
                .andExpect(jsonPath("$.remarks").value(createdHab.getRemarks()));
    }

    @Test
    void updateHouseholdAccountBook_shouldReturnUpdatedHouseholdAccountBook() throws Exception {
        Long habSeqToUpdate = 1L;
        HouseholdAccountBook updatedHab = new HouseholdAccountBook();
        updatedHab.setHabSeq(habSeqToUpdate);
        updatedHab.setActualDate(LocalDate.of(2024, 7, 1));
        updatedHab.setCategoryCd(1);
        updatedHab.setStoreCd(1);
        updatedHab.setAmount(new BigDecimal("1500"));
        updatedHab.setRemarks("更新食費");

        HouseholdAccountBookRequest updateRequest = new HouseholdAccountBookRequest();
        updateRequest.setActualDate(LocalDate.of(2024, 7, 1));
        updateRequest.setCategoryCd(1);
        updateRequest.setStoreCd(1);
        updateRequest.setAmount(new BigDecimal("1500"));
        updateRequest.setRemarks("更新食費");

        when(householdAccountBookService.updateHouseholdAccountBook(eq(habSeqToUpdate), any(HouseholdAccountBookRequest.class))).thenReturn(updatedHab);

        mockMvc.perform(put("/api/household-account-books/{habSeq}", habSeqToUpdate).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.habSeq").value(updatedHab.getHabSeq()))
                .andExpect(jsonPath("$.remarks").value(updatedHab.getRemarks()));
    }

    @Test
    void deleteHouseholdAccountBook_shouldReturnNoContent() throws Exception {
        Long habSeqToDelete = 1L;
        doNothing().when(householdAccountBookService).deleteHouseholdAccountBook(habSeqToDelete);

        mockMvc.perform(delete("/api/household-account-books/{habSeq}", habSeqToDelete).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
