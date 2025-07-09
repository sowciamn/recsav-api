package com.sowciamn.recsav.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sowciamn.recsav.exception.ResourceNotFoundException;
import com.sowciamn.recsav.model.entity.HouseholdAccountBook;
import com.sowciamn.recsav.model.request.HouseholdAccountBookRequest;
import com.sowciamn.recsav.repository.HouseholdAccountBookMapper;

@Service
public class HouseholdAccountBookService {

    private final HouseholdAccountBookMapper householdAccountBookMapper;

    @Autowired
    public HouseholdAccountBookService(HouseholdAccountBookMapper householdAccountBookMapper) {
        this.householdAccountBookMapper = householdAccountBookMapper;
    }

    public List<HouseholdAccountBook> findAllHouseholdAccountBooks(String yearMonth) {
        return householdAccountBookMapper.findAll(yearMonth);
    }

    @Transactional
    public HouseholdAccountBook createHouseholdAccountBook(HouseholdAccountBookRequest request) {
        HouseholdAccountBook householdAccountBook = new HouseholdAccountBook();
        BeanUtils.copyProperties(request, householdAccountBook);
        householdAccountBookMapper.insert(householdAccountBook);
        return householdAccountBook;
    }

    @Transactional
    public HouseholdAccountBook updateHouseholdAccountBook(Long habSeq, HouseholdAccountBookRequest request) {
        HouseholdAccountBook householdAccountBook = householdAccountBookMapper.findById(habSeq)
                .orElseThrow(() -> new ResourceNotFoundException("HouseholdAccountBook not found with id: " + habSeq));

        BeanUtils.copyProperties(request, householdAccountBook);
        householdAccountBook.setHabSeq(habSeq);
        householdAccountBookMapper.update(householdAccountBook);
        return householdAccountBook;
    }

    @Transactional
    public void deleteHouseholdAccountBook(Long habSeq) {
        householdAccountBookMapper.findById(habSeq)
                .orElseThrow(() -> new ResourceNotFoundException("HouseholdAccountBook not found with id: " + habSeq));
        householdAccountBookMapper.deleteById(habSeq);
    }
}
