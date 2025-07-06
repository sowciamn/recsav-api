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

import com.sowciamn.recsav.model.entity.Store;
import com.sowciamn.recsav.model.request.StoreRequest;
import com.sowciamn.recsav.service.StoreService;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<Store> getAllStores() {
        return storeService.findAllStores();
    }

    @PostMapping
    public ResponseEntity<Store> createStore(@Valid @RequestBody StoreRequest request) {
        Store createdStore = storeService.createStore(request);
        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }

    @PutMapping("/{storeCd}")
    public ResponseEntity<Store> updateStore(@PathVariable Integer storeCd, @Valid @RequestBody StoreRequest request) {
        Store updatedStore = storeService.updateStore(storeCd, request);
        return ResponseEntity.ok(updatedStore);
    }

    @DeleteMapping("/{storeCd}")
    public ResponseEntity<Void> deleteStore(@PathVariable Integer storeCd) {
        storeService.deleteStore(storeCd);
        return ResponseEntity.noContent().build();
    }
}
