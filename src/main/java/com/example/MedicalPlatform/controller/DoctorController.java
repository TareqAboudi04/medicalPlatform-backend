package com.example.MedicalPlatform.controller;

import com.example.MedicalPlatform.dto.response.DoctorResponseDTO;
import com.example.MedicalPlatform.entity.Doctor;
import com.example.MedicalPlatform.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200") // Allow API requests from Angular frontend
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{doctorId}")
    public DoctorResponseDTO getDoctorById(@PathVariable Long doctorId){
        return doctorService.getDoctorById(doctorId);
    }

    @GetMapping("/search/name")
    public List<DoctorResponseDTO> searchByName(@RequestParam String name){
        return doctorService.searchByName(name);
    }

    @GetMapping("/search/location")
    public List<DoctorResponseDTO> searchByLocation(@RequestParam String location){
        return doctorService.searchByLocation(location);
    }

    @GetMapping("/search/specialty")
    public List<DoctorResponseDTO> searchBySpecialty(@RequestParam String specialty){
        return doctorService.searchBySpecialty(specialty);
    }

}
