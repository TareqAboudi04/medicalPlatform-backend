package com.example.MedicalPlatform.controller;

import com.example.MedicalPlatform.dto.response.DashboardResponseDTO;
import com.example.MedicalPlatform.dto.response.UserResponseDTO;
import com.example.MedicalPlatform.entity.User;
import com.example.MedicalPlatform.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/doctors/pending")
    public List<UserResponseDTO> getPendingDoctors(){
        return adminService.getPendingDoctors();
    }
    @PutMapping("/doctors/approve/{userId}")
    public String approveDoctor(@PathVariable Long userId){
        return adminService.approveDoctor(userId);
    }
    @PutMapping("/doctors/reject/{userId}")
    public String rejectDoctor(@PathVariable Long userId){
        return adminService.rejectDoctor(userId);
    }
    @PutMapping("/doctors/block/{userId}")
    public String blockUser(@PathVariable Long userId){
        return adminService.blockUser(userId);
    }
    @GetMapping("/dashboard")
    public DashboardResponseDTO getDashboardCounts(){
        return adminService.getDashboardCounts();
    }
}
