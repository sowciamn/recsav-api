package com.sowciamn.recsav.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sowciamn.recsav.model.entity.DepositAccount;

@Mapper
public interface DepositAccountMapper {
    List<DepositAccount> findAll();
    Optional<DepositAccount> findById(@Param("depositAccountCd") String depositAccountCd);
    int insert(DepositAccount depositAccount);
    int update(DepositAccount depositAccount);
    int deleteById(@Param("depositAccountCd") String depositAccountCd);
}
