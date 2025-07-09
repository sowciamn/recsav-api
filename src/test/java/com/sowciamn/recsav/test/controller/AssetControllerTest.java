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
import com.sowciamn.recsav.model.entity.Asset;
import com.sowciamn.recsav.model.request.AssetRequest;
import com.sowciamn.recsav.service.AssetService;
import com.sowciamn.recsav.controller.AssetController;

@WebMvcTest(AssetController.class)
public class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssetService assetService;

    private Asset asset1;
    private Asset asset2;
    private AssetRequest assetRequest;

    @BeforeEach
    void setUp() {
        asset1 = new Asset();
        asset1.setAssetYearMonth(LocalDate.of(2024, 6, 1));
        asset1.setDepositAccountCd("BK1");
        asset1.setAssetAmount(new BigDecimal("1000000"));
        asset1.setAssetRemarks("6月資産");

        asset2 = new Asset();
        asset2.setAssetYearMonth(LocalDate.of(2024, 6, 1));
        asset2.setDepositAccountCd("INV");
        asset2.setAssetAmount(new BigDecimal("2000000"));
        asset2.setAssetRemarks("6月投資資産");

        assetRequest = new AssetRequest();
        assetRequest.setAssetYearMonth(LocalDate.of(2024, 7, 1));
        assetRequest.setDepositAccountCd("BK1");
        assetRequest.setAssetAmount(new BigDecimal("1100000"));
        assetRequest.setAssetRemarks("7月資産");
    }

    @Test
    void getAllAssets_shouldReturnListOfAssets() throws Exception {
        List<Asset> allAssets = Arrays.asList(asset1, asset2);
        when(assetService.findAllAssets(null)).thenReturn(allAssets);

        mockMvc.perform(get("/api/assets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].depositAccountCd").value(asset1.getDepositAccountCd()))
                .andExpect(jsonPath("$[0].assetAmount").value(asset1.getAssetAmount()))
                .andExpect(jsonPath("$[1].depositAccountCd").value(asset2.getDepositAccountCd()));
    }

    @Test
    void getAllAssets_shouldReturnFilteredListByYearMonth() throws Exception {
        String yearMonth = "2024-06";
        List<Asset> filteredAssets = Arrays.asList(asset1, asset2); // Both assets are in 2024-06
        when(assetService.findAllAssets(yearMonth)).thenReturn(filteredAssets);

        mockMvc.perform(get("/api/assets")
                .param("yearMonth", yearMonth))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].depositAccountCd").value(asset1.getDepositAccountCd()));
    }

    @Test
    void createAsset_shouldReturnCreatedAsset() throws Exception {
        Asset createdAsset = new Asset();
        createdAsset.setAssetYearMonth(assetRequest.getAssetYearMonth());
        createdAsset.setDepositAccountCd(assetRequest.getDepositAccountCd());
        createdAsset.setAssetAmount(assetRequest.getAssetAmount());
        createdAsset.setAssetRemarks(assetRequest.getAssetRemarks());

        when(assetService.createAsset(any(AssetRequest.class))).thenReturn(createdAsset);

        mockMvc.perform(post("/api/assets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assetRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.depositAccountCd").value(createdAsset.getDepositAccountCd()))
                .andExpect(jsonPath("$.assetAmount").value(createdAsset.getAssetAmount()));
    }

    @Test
    void updateAsset_shouldReturnUpdatedAsset() throws Exception {
        LocalDate assetYearMonthToUpdate = LocalDate.of(2024, 6, 1);
        String depositAccountCdToUpdate = "BK1";
        Asset updatedAsset = new Asset();
        updatedAsset.setAssetYearMonth(assetYearMonthToUpdate);
        updatedAsset.setDepositAccountCd(depositAccountCdToUpdate);
        updatedAsset.setAssetAmount(new BigDecimal("1050000"));
        updatedAsset.setAssetRemarks("更新6月資産");

        AssetRequest updateRequest = new AssetRequest();
        updateRequest.setAssetYearMonth(assetYearMonthToUpdate);
        updateRequest.setDepositAccountCd(depositAccountCdToUpdate);
        updateRequest.setAssetAmount(new BigDecimal("1050000"));
        updateRequest.setAssetRemarks("更新6月資産");

        when(assetService.updateAsset(eq(assetYearMonthToUpdate), eq(depositAccountCdToUpdate), any(AssetRequest.class))).thenReturn(updatedAsset);

        mockMvc.perform(put("/api/assets/{assetYearMonth}/{depositAccountCd}", assetYearMonthToUpdate, depositAccountCdToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.depositAccountCd").value(updatedAsset.getDepositAccountCd()))
                .andExpect(jsonPath("$.assetAmount").value(updatedAsset.getAssetAmount()));
    }

    @Test
    void deleteAsset_shouldReturnNoContent() throws Exception {
        LocalDate assetYearMonthToDelete = LocalDate.of(2024, 6, 1);
        String depositAccountCdToDelete = "BK1";
        doNothing().when(assetService).deleteAsset(assetYearMonthToDelete, depositAccountCdToDelete);

        mockMvc.perform(delete("/api/assets/{assetYearMonth}/{depositAccountCd}", assetYearMonthToDelete, depositAccountCdToDelete))
                .andExpect(status().isNoContent());
    }
}
