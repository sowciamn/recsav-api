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
import com.sowciamn.recsav.model.entity.Store;
import com.sowciamn.recsav.model.request.StoreRequest;
import com.sowciamn.recsav.service.StoreService;
import com.sowciamn.recsav.controller.StoreController;

@WebMvcTest(StoreController.class)
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StoreService storeService;

    private Store store1;
    private Store store2;
    private StoreRequest storeRequest;

    @BeforeEach
    void setUp() {
        store1 = new Store();
        store1.setStoreCd(1);
        store1.setStoreNm("スーパーA");
        store1.setStoreAddress("東京都");

        store2 = new Store();
        store2.setStoreCd(2);
        store2.setStoreNm("コンビニB");
        store2.setStoreAddress("神奈川県");

        storeRequest = new StoreRequest();
        storeRequest.setStoreNm("ドラッグストアC");
        storeRequest.setStoreAddress("埼玉県");
    }

    @Test
    void getAllStores_shouldReturnListOfStores() throws Exception {
        List<Store> allStores = Arrays.asList(store1, store2);
        when(storeService.findAllStores()).thenReturn(allStores);

        mockMvc.perform(get("/api/stores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].storeCd").value(store1.getStoreCd()))
                .andExpect(jsonPath("$[0].storeNm").value(store1.getStoreNm()))
                .andExpect(jsonPath("$[1].storeCd").value(store2.getStoreCd()));
    }

    @Test
    void createStore_shouldReturnCreatedStore() throws Exception {
        Store createdStore = new Store();
        createdStore.setStoreCd(3);
        createdStore.setStoreNm(storeRequest.getStoreNm());
        createdStore.setStoreAddress(storeRequest.getStoreAddress());

        when(storeService.createStore(any(StoreRequest.class))).thenReturn(createdStore);

        mockMvc.perform(post("/api/stores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storeRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.storeCd").value(createdStore.getStoreCd()))
                .andExpect(jsonPath("$.storeNm").value(createdStore.getStoreNm()));
    }

    @Test
    void updateStore_shouldReturnUpdatedStore() throws Exception {
        Integer storeCdToUpdate = 1;
        Store updatedStore = new Store();
        updatedStore.setStoreCd(storeCdToUpdate);
        updatedStore.setStoreNm("更新スーパーA");
        updatedStore.setStoreAddress("東京都更新");

        StoreRequest updateRequest = new StoreRequest();
        updateRequest.setStoreNm("更新スーパーA");
        updateRequest.setStoreAddress("東京都更新");

        when(storeService.updateStore(eq(storeCdToUpdate), any(StoreRequest.class))).thenReturn(updatedStore);

        mockMvc.perform(put("/api/stores/{storeCd}", storeCdToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.storeCd").value(updatedStore.getStoreCd()))
                .andExpect(jsonPath("$.storeNm").value(updatedStore.getStoreNm()));
    }

    @Test
    void deleteStore_shouldReturnNoContent() throws Exception {
        Integer storeCdToDelete = 1;
        doNothing().when(storeService).deleteStore(storeCdToDelete);

        mockMvc.perform(delete("/api/stores/{storeCd}", storeCdToDelete))
                .andExpect(status().isNoContent());
    }
}
