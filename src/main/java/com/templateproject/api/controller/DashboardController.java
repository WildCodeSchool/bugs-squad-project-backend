package com.templateproject.api.controller;

import com.templateproject.api.entity.Dashboard;
import com.templateproject.api.repository.DashboardRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardRepository dashboardRepository;

    public DashboardController(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @GetMapping("")
    public @ResponseBody List<Dashboard> getDashboard() {
        return dashboardRepository.findAll();
    }

    @PostMapping("")
    public @ResponseBody Dashboard createDashboard(@RequestBody Dashboard dashboard) {
        return dashboardRepository.save(dashboard);
    }
}
