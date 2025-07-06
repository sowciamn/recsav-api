package com.sowciamn.recsav.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.sowciamn.recsav.model.entity.DepositAccount;
import com.sowciamn.recsav.model.request.DepositAccountRequest;
import com.sowciamn.recsav.service.DepositAccountService;

@RestController
@RequestMapping("/api/deposit-accounts")
public class DepositAccountController {

    private final DepositAccountService depositAccountService;

    @Autowired
    public DepositAccountController(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    @GetMapping
    public List<DepositAccount> getAllDepositAccounts() {
        return depositAccountService.findAllDepositAccounts();
    }

    @PostMapping
    public ResponseEntity<DepositAccount> createDepositAccount(@Valid @RequestBody DepositAccountRequest request) {
        DepositAccount createdDepositAccount = depositAccountService.createDepositAccount(request);
        return new ResponseEntity<>(createdDepositAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{depositAccountCd}")
    public ResponseEntity<DepositAccount> updateDepositAccount(@PathVariable String depositAccountCd, @Valid @RequestBody DepositAccountRequest request) {
        DepositAccount updatedDepositAccount = depositAccountService.updateDepositAccount(depositAccountCd, request);
        return ResponseEntity.ok(updatedDepositAccount);
    }

    @DeleteMapping("/{depositAccountCd}")
    public ResponseEntity<Void> deleteDepositAccount(@PathVariable String depositAccountCd) {
        depositAccountService.deleteDepositAccount(depositAccountCd);
        return ResponseEntity.noContent().build();
    }
}
