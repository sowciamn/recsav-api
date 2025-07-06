package com.sowciamn.recsav.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sowciamn.recsav.exception.ResourceNotFoundException;
import com.sowciamn.recsav.model.entity.Asset;
import com.sowciamn.recsav.model.request.AssetRequest;
import com.sowciamn.recsav.repository.AssetMapper;

@Service
public class AssetService {

    private final AssetMapper assetMapper;

    @Autowired
    public AssetService(AssetMapper assetMapper) {
        this.assetMapper = assetMapper;
    }

    public List<Asset> findAllAssets() {
        return assetMapper.findAll();
    }

    @Transactional
    public Asset createAsset(AssetRequest request) {
        Asset asset = new Asset();
        BeanUtils.copyProperties(request, asset);
        assetMapper.insert(asset);
        return asset;
    }

    @Transactional
    public Asset updateAsset(LocalDate assetYearMonth, String depositAccountCd, AssetRequest request) {
        Asset asset = assetMapper.findById(assetYearMonth, depositAccountCd)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + assetYearMonth + "/" + depositAccountCd));

        BeanUtils.copyProperties(request, asset);
        asset.setAssetYearMonth(assetYearMonth);
        asset.setDepositAccountCd(depositAccountCd);
        assetMapper.update(asset);
        return asset;
    }

    @Transactional
    public void deleteAsset(LocalDate assetYearMonth, String depositAccountCd) {
        assetMapper.findById(assetYearMonth, depositAccountCd)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + assetYearMonth + "/" + depositAccountCd));
        assetMapper.deleteById(assetYearMonth, depositAccountCd);
    }
}
