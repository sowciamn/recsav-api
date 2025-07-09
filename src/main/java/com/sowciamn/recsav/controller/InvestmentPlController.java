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

import com.sowciamn.recsav.model.entity.InvestmentPl;
import com.sowciamn.recsav.model.request.InvestmentPlRequest;
import com.sowciamn.recsav.service.InvestmentPlService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/investment-pls")
public class InvestmentPlController {

    private final InvestmentPlService investmentPlService;

    @Autowired
    public InvestmentPlController(InvestmentPlService investmentPlService) {
        this.investmentPlService = investmentPlService;
    }

    @GetMapping
    public List<InvestmentPl> getAllInvestmentPls(@RequestParam(required = false) String yearMonth) {
        return investmentPlService.findAllInvestmentPls(yearMonth);
    }

    @PostMapping
    public ResponseEntity<InvestmentPl> createInvestmentPl(@Valid @RequestBody InvestmentPlRequest request) {
        InvestmentPl createdInvestmentPl = investmentPlService.createInvestmentPl(request);
        return new ResponseEntity<>(createdInvestmentPl, HttpStatus.CREATED);
    }

    @PutMapping("/{investmentPlYearMonth}/{depositAccountCd}")
    public ResponseEntity<InvestmentPl> updateInvestmentPl(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate investmentPlYearMonth, 
            @PathVariable String depositAccountCd, 
            @Valid @RequestBody InvestmentPlRequest request) {
        InvestmentPl updatedInvestmentPl = investmentPlService.updateInvestmentPl(investmentPlYearMonth, depositAccountCd, request);
        return ResponseEntity.ok(updatedInvestmentPl);
    }

    @DeleteMapping("/{investmentPlYearMonth}/{depositAccountCd}")
    public ResponseEntity<Void> deleteInvestmentPl(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate investmentPlYearMonth, 
            @PathVariable String depositAccountCd) {
        investmentPlService.deleteInvestmentPl(investmentPlYearMonth, depositAccountCd);
        return ResponseEntity.noContent().build();
    }
}
