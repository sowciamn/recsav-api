package com.sowciamn.recsav.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sowciamn.recsav.model.entity.InvestmentPl;

@Mapper
public interface InvestmentPlMapper {
    List<InvestmentPl> findAll(@Param("yearMonth") String yearMonth);
    Optional<InvestmentPl> findById(@Param("investmentPlYearMonth") LocalDate investmentPlYearMonth, @Param("depositAccountCd") String depositAccountCd);
    int insert(InvestmentPl investmentPl);
    int update(InvestmentPl investmentPl);
    int deleteById(@Param("investmentPlYearMonth") LocalDate investmentPlYearMonth, @Param("depositAccountCd") String depositAccountCd);
}
