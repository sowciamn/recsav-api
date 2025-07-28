package com.sowciamn.recsav.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sowciamn.recsav.model.entity.RecurringConfig;
import com.sowciamn.recsav.repository.RecurringConfigMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecurringConfigService {

    private final RecurringConfigMapper recurringConfigMapper;

    public List<RecurringConfig> findAll() {
        return recurringConfigMapper.findAll();
    }

    public RecurringConfig findById(Integer recurringConfigSeq) {
        return recurringConfigMapper.findById(recurringConfigSeq);
    }

    @Transactional
    public RecurringConfig create(RecurringConfig recurringConfig) {
        recurringConfigMapper.insert(recurringConfig);
        return recurringConfig;
    }

    @Transactional
    public RecurringConfig update(Integer recurringConfigSeq, RecurringConfig recurringConfig) {
        recurringConfig.setRecurringConfigSeq(recurringConfigSeq);
        recurringConfigMapper.update(recurringConfig);
        return recurringConfig;
    }

    @Transactional
    public void delete(Integer recurringConfigSeq) {
        recurringConfigMapper.delete(recurringConfigSeq);
    }
}
