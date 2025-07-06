package com.sowciamn.recsav.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sowciamn.recsav.model.request.DashboardRequest;
import com.sowciamn.recsav.model.response.DashboardResponse;
import com.sowciamn.recsav.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse getDashboardData(@Valid DashboardRequest request) {
        return dashboardService.getDashboardData(request.getYearMonth());
    }
}
