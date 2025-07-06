package com.sowciamn.recsav.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sowciamn.recsav.exception.ResourceNotFoundException;
import com.sowciamn.recsav.model.entity.Store;
import com.sowciamn.recsav.model.request.StoreRequest;
import com.sowciamn.recsav.repository.StoreMapper;

@Service
public class StoreService {

    private final StoreMapper storeMapper;

    @Autowired
    public StoreService(StoreMapper storeMapper) {
        this.storeMapper = storeMapper;
    }

    public List<Store> findAllStores() {
        return storeMapper.findAll();
    }

    @Transactional
    public Store createStore(StoreRequest request) {
        Store store = new Store();
        BeanUtils.copyProperties(request, store);
        storeMapper.insert(store);
        return store;
    }

    @Transactional
    public Store updateStore(Integer storeCd, StoreRequest request) {
        Store store = storeMapper.findById(storeCd)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeCd));

        BeanUtils.copyProperties(request, store);
        storeMapper.update(store);
        return store;
    }

    @Transactional
    public void deleteStore(Integer storeCd) {
        storeMapper.findById(storeCd)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeCd));
        storeMapper.deleteById(storeCd);
    }
}
