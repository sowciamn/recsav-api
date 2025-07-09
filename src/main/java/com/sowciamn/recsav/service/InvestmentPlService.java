package com.sowciamn.recsav.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sowciamn.recsav.exception.ResourceNotFoundException;
import com.sowciamn.recsav.model.entity.InvestmentPl;
import com.sowciamn.recsav.model.request.InvestmentPlRequest;
import com.sowciamn.recsav.repository.InvestmentPlMapper;

@Service
public class InvestmentPlService {

    private final InvestmentPlMapper investmentPlMapper;

    @Autowired
    public InvestmentPlService(InvestmentPlMapper investmentPlMapper) {
        this.investmentPlMapper = investmentPlMapper;
    }

    public List<InvestmentPl> findAllInvestmentPls(String yearMonth) {
        return investmentPlMapper.findAll(yearMonth);
    }

    @Transactional
    public InvestmentPl createInvestmentPl(InvestmentPlRequest request) {
        InvestmentPl investmentPl = new InvestmentPl();
        BeanUtils.copyProperties(request, investmentPl);
        investmentPlMapper.insert(investmentPl);
        return investmentPl;
    }

    @Transactional
    public InvestmentPl updateInvestmentPl(LocalDate investmentPlYearMonth, String depositAccountCd, InvestmentPlRequest request) {
        InvestmentPl investmentPl = investmentPlMapper.findById(investmentPlYearMonth, depositAccountCd)
                .orElseThrow(() -> new ResourceNotFoundException("InvestmentPl not found with id: " + investmentPlYearMonth + "/" + depositAccountCd));

        BeanUtils.copyProperties(request, investmentPl);
        investmentPl.setInvestmentPlYearMonth(investmentPlYearMonth);
        investmentPl.setDepositAccountCd(depositAccountCd);
        investmentPlMapper.update(investmentPl);
        return investmentPl;
    }

    @Transactional
    public void deleteInvestmentPl(LocalDate investmentPlYearMonth, String depositAccountCd) {
        investmentPlMapper.findById(investmentPlYearMonth, depositAccountCd)
                .orElseThrow(() -> new ResourceNotFoundException("InvestmentPl not found with id: " + investmentPlYearMonth + "/" + depositAccountCd));
        investmentPlMapper.deleteById(investmentPlYearMonth, depositAccountCd);
    }
}
