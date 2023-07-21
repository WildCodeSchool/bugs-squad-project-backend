package com.templateproject.api.controller;

import com.templateproject.api.entity.Dashboard;
import com.templateproject.api.repository.DashboardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @PutMapping("/{id}")
    public @ResponseBody Dashboard updateDashboard(@PathVariable(value = "id") Long id, @RequestBody Dashboard dashboard) {
        Optional<Dashboard> optionalDashboard = dashboardRepository.findById(id);
        if (optionalDashboard.isPresent()) {
            Dashboard updatedDashboard = optionalDashboard.get();
            updatedDashboard.setxPos(dashboard.getxPos());
            updatedDashboard.setyPos(dashboard.getyPos());
            updatedDashboard.setHeight(dashboard.getHeight());
            updatedDashboard.setWidth(dashboard.getWidth());
            return dashboardRepository.save(updatedDashboard);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dashboard non trouvée");
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void deleteDashboard(@PathVariable(value = "id") Long id) {
        dashboardRepository.deleteById(id);
    }
}
