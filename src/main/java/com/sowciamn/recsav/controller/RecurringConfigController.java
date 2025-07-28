package com.sowciamn.recsav.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sowciamn.recsav.model.entity.RecurringConfig;
import com.sowciamn.recsav.model.request.RecurringConfigRequest;
import com.sowciamn.recsav.service.RecurringConfigService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recurring-configs")
@RequiredArgsConstructor
public class RecurringConfigController {

    private final RecurringConfigService recurringConfigService;

    @GetMapping
    public List<RecurringConfig> getRecurringConfigs() {
        return recurringConfigService.findAll();
    }

    @GetMapping("/{recurringConfigSeq}")
    public RecurringConfig getRecurringConfig(@PathVariable Integer recurringConfigSeq) {
        return recurringConfigService.findById(recurringConfigSeq);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecurringConfig createRecurringConfig(@RequestBody RecurringConfigRequest request) {
        RecurringConfig recurringConfig = new RecurringConfig();
        recurringConfig.setRecurringNm(request.getRecurringNm());
        recurringConfig.setExecutionIntervalType(request.getExecutionIntervalType());
        recurringConfig.setCategoryCd(request.getCategoryCd());
        recurringConfig.setStoreCd(request.getStoreCd());
        recurringConfig.setAmount(request.getAmount());
        recurringConfig.setRemarks(request.getRemarks());
        recurringConfig.setLinkingDataType(request.getLinkingDataType());
        recurringConfig.setActiveFlg(request.getActiveFlg());
        return recurringConfigService.create(recurringConfig);
    }

    @PutMapping("/{recurringConfigSeq}")
    public RecurringConfig updateRecurringConfig(@PathVariable Integer recurringConfigSeq, @RequestBody RecurringConfigRequest request) {
        RecurringConfig recurringConfig = new RecurringConfig();
        recurringConfig.setRecurringNm(request.getRecurringNm());
        recurringConfig.setExecutionIntervalType(request.getExecutionIntervalType());
        recurringConfig.setCategoryCd(request.getCategoryCd());
        recurringConfig.setStoreCd(request.getStoreCd());
        recurringConfig.setAmount(request.getAmount());
        recurringConfig.setRemarks(request.getRemarks());
        recurringConfig.setLinkingDataType(request.getLinkingDataType());
        recurringConfig.setActiveFlg(request.getActiveFlg());
        return recurringConfigService.update(recurringConfigSeq, recurringConfig);
    }

    @DeleteMapping("/{recurringConfigSeq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecurringConfig(@PathVariable Integer recurringConfigSeq) {
        recurringConfigService.delete(recurringConfigSeq);
    }
}
