package com.sowciamn.recsav.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sowciamn.recsav.model.entity.Asset;

@Mapper
public interface AssetMapper {
    List<Asset> findAll(@Param("yearMonth") String yearMonth);
    Optional<Asset> findById(@Param("assetYearMonth") LocalDate assetYearMonth, @Param("depositAccountCd") String depositAccountCd);
    int insert(Asset asset);
    int update(Asset asset);
    int deleteById(@Param("assetYearMonth") LocalDate assetYearMonth, @Param("depositAccountCd") String depositAccountCd);
}
