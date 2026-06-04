package com.example.MedicalPlatform.controller;

import com.example.MedicalPlatform.dto.request.DoctorRegisterReqDTO;
import com.example.MedicalPlatform.dto.request.LoginRequestDTO;
import com.example.MedicalPlatform.dto.request.PatientRegisterReqDTO;
import com.example.MedicalPlatform.dto.response.AuthResponseDTO;
import com.example.MedicalPlatform.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/patient")
    public String registerPatient(@Valid @RequestBody PatientRegisterReqDTO request){
        return authService.registerPatient(request);
    }

    @PostMapping("/register/doctor")
    public String registerDoctor(@Valid @RequestBody DoctorRegisterReqDTO request){
        return authService.registerDoctor(request);
    }

    @PostMapping("login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO request){
        return authService.login(request);
    }
}
