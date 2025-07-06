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

import com.sowciamn.recsav.model.entity.HouseholdAccountBook;
import com.sowciamn.recsav.model.request.HouseholdAccountBookRequest;
import com.sowciamn.recsav.service.HouseholdAccountBookService;

@RestController
@RequestMapping("/api/household-account-books")
public class HouseholdAccountBookController {

    private final HouseholdAccountBookService householdAccountBookService;

    @Autowired
    public HouseholdAccountBookController(HouseholdAccountBookService householdAccountBookService) {
        this.householdAccountBookService = householdAccountBookService;
    }

    @GetMapping
    public List<HouseholdAccountBook> getAllHouseholdAccountBooks() {
        return householdAccountBookService.findAllHouseholdAccountBooks();
    }

    @PostMapping
    public ResponseEntity<HouseholdAccountBook> createHouseholdAccountBook(@Valid @RequestBody HouseholdAccountBookRequest request) {
        HouseholdAccountBook createdHouseholdAccountBook = householdAccountBookService.createHouseholdAccountBook(request);
        return new ResponseEntity<>(createdHouseholdAccountBook, HttpStatus.CREATED);
    }

    @PutMapping("/{habSeq}")
    public ResponseEntity<HouseholdAccountBook> updateHouseholdAccountBook(@PathVariable Long habSeq, @Valid @RequestBody HouseholdAccountBookRequest request) {
        HouseholdAccountBook updatedHouseholdAccountBook = householdAccountBookService.updateHouseholdAccountBook(habSeq, request);
        return ResponseEntity.ok(updatedHouseholdAccountBook);
    }

    @DeleteMapping("/{habSeq}")
    public ResponseEntity<Void> deleteHouseholdAccountBook(@PathVariable Long habSeq) {
        householdAccountBookService.deleteHouseholdAccountBook(habSeq);
        return ResponseEntity.noContent().build();
    }
}
