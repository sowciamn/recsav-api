package com.sowciamn.recsav.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sowciamn.recsav.exception.ResourceNotFoundException;
import com.sowciamn.recsav.model.entity.DepositAccount;
import com.sowciamn.recsav.model.request.DepositAccountRequest;
import com.sowciamn.recsav.repository.DepositAccountMapper;

@Service
public class DepositAccountService {

    private final DepositAccountMapper depositAccountMapper;

    @Autowired
    public DepositAccountService(DepositAccountMapper depositAccountMapper) {
        this.depositAccountMapper = depositAccountMapper;
    }

    public List<DepositAccount> findAllDepositAccounts() {
        return depositAccountMapper.findAll();
    }

    @Transactional
    public DepositAccount createDepositAccount(DepositAccountRequest request) {
        DepositAccount depositAccount = new DepositAccount();
        BeanUtils.copyProperties(request, depositAccount);
        depositAccountMapper.insert(depositAccount);
        return depositAccount;
    }

    @Transactional
    public DepositAccount updateDepositAccount(String depositAccountCd, DepositAccountRequest request) {
        DepositAccount depositAccount = depositAccountMapper.findById(depositAccountCd)
                .orElseThrow(() -> new ResourceNotFoundException("DepositAccount not found with id: " + depositAccountCd));

        BeanUtils.copyProperties(request, depositAccount);
        // PKは更新しないのでリクエストからCDを設定し直す
        depositAccount.setDepositAccountCd(depositAccountCd);
        depositAccountMapper.update(depositAccount);
        return depositAccount;
    }

    @Transactional
    public void deleteDepositAccount(String depositAccountCd) {
        depositAccountMapper.findById(depositAccountCd)
                .orElseThrow(() -> new ResourceNotFoundException("DepositAccount not found with id: " + depositAccountCd));
        depositAccountMapper.deleteById(depositAccountCd);
    }
}
