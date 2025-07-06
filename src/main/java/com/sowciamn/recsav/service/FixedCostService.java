package com.sowciamn.recsav.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sowciamn.recsav.exception.ResourceNotFoundException;
import com.sowciamn.recsav.model.entity.FixedCost;
import com.sowciamn.recsav.model.request.FixedCostRequest;
import com.sowciamn.recsav.repository.FixedCostMapper;

@Service
public class FixedCostService {

    private final FixedCostMapper fixedCostMapper;

    @Autowired
    public FixedCostService(FixedCostMapper fixedCostMapper) {
        this.fixedCostMapper = fixedCostMapper;
    }

    public List<FixedCost> findAllFixedCosts() {
        return fixedCostMapper.findAll();
    }

    @Transactional
    public FixedCost createFixedCost(FixedCostRequest request) {
        FixedCost fixedCost = new FixedCost();
        BeanUtils.copyProperties(request, fixedCost);
        fixedCostMapper.insert(fixedCost);
        return fixedCost;
    }

    @Transactional
    public FixedCost updateFixedCost(Long fixedCostSeq, FixedCostRequest request) {
        FixedCost fixedCost = fixedCostMapper.findById(fixedCostSeq)
                .orElseThrow(() -> new ResourceNotFoundException("FixedCost not found with id: " + fixedCostSeq));

        BeanUtils.copyProperties(request, fixedCost);
        fixedCost.setFixedCostSeq(fixedCostSeq);
        fixedCostMapper.update(fixedCost);
        return fixedCost;
    }

    @Transactional
    public void deleteFixedCost(Long fixedCostSeq) {
        fixedCostMapper.findById(fixedCostSeq)
                .orElseThrow(() -> new ResourceNotFoundException("FixedCost not found with id: " + fixedCostSeq));
        fixedCostMapper.deleteById(fixedCostSeq);
    }
}
