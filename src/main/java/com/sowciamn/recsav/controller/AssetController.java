package com.sowciamn.recsav.controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sowciamn.recsav.model.entity.Asset;
import com.sowciamn.recsav.model.request.AssetRequest;
import com.sowciamn.recsav.service.AssetService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public List<Asset> getAllAssets(@RequestParam(required = false) String yearMonth) {
        return assetService.findAllAssets(yearMonth);
    }

    @PostMapping
    public ResponseEntity<Asset> createAsset(@Valid @RequestBody AssetRequest request) {
        Asset createdAsset = assetService.createAsset(request);
        return new ResponseEntity<>(createdAsset, HttpStatus.CREATED);
    }

    @PutMapping("/{assetYearMonth}/{depositAccountCd}")
    public ResponseEntity<Asset> updateAsset(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate assetYearMonth, 
            @PathVariable String depositAccountCd, 
            @Valid @RequestBody AssetRequest request) {
        Asset updatedAsset = assetService.updateAsset(assetYearMonth, depositAccountCd, request);
        return ResponseEntity.ok(updatedAsset);
    }

    @DeleteMapping("/{assetYearMonth}/{depositAccountCd}")
    public ResponseEntity<Void> deleteAsset(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate assetYearMonth, 
            @PathVariable String depositAccountCd) {
        assetService.deleteAsset(assetYearMonth, depositAccountCd);
        return ResponseEntity.noContent().build();
    }
}
