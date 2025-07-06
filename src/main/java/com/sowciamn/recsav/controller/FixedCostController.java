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

import com.sowciamn.recsav.model.entity.FixedCost;
import com.sowciamn.recsav.model.request.FixedCostRequest;
import com.sowciamn.recsav.service.FixedCostService;

@RestController
@RequestMapping("/api/fixed-costs")
public class FixedCostController {

    private final FixedCostService fixedCostService;

    @Autowired
    public FixedCostController(FixedCostService fixedCostService) {
        this.fixedCostService = fixedCostService;
    }

    @GetMapping
    public List<FixedCost> getAllFixedCosts() {
        return fixedCostService.findAllFixedCosts();
    }

    @PostMapping
    public ResponseEntity<FixedCost> createFixedCost(@Valid @RequestBody FixedCostRequest request) {
        FixedCost createdFixedCost = fixedCostService.createFixedCost(request);
        return new ResponseEntity<>(createdFixedCost, HttpStatus.CREATED);
    }

    @PutMapping("/{fixedCostSeq}")
    public ResponseEntity<FixedCost> updateFixedCost(@PathVariable Long fixedCostSeq, @Valid @RequestBody FixedCostRequest request) {
        FixedCost updatedFixedCost = fixedCostService.updateFixedCost(fixedCostSeq, request);
        return ResponseEntity.ok(updatedFixedCost);
    }

    @DeleteMapping("/{fixedCostSeq}")
    public ResponseEntity<Void> deleteFixedCost(@PathVariable Long fixedCostSeq) {
        fixedCostService.deleteFixedCost(fixedCostSeq);
        return ResponseEntity.noContent().build();
    }
}
