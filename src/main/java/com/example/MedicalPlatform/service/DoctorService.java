package com.example.MedicalPlatform.service;

import com.example.MedicalPlatform.dto.response.DashboardResponseDTO;
import com.example.MedicalPlatform.dto.response.DoctorResponseDTO;
import com.example.MedicalPlatform.entity.Doctor;
import com.example.MedicalPlatform.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorResponseDTO> getAllDoctors(){
        return doctorRepository.findAll().stream().map(this::mapToDoctorResponseDTO).toList();
    }

    public DoctorResponseDTO getDoctorById(Long doctorId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));

        return mapToDoctorResponseDTO(doctor);

    }

    public List<DoctorResponseDTO> searchByName(String name){
        return doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name)
                .stream().map(this::mapToDoctorResponseDTO).toList();
    }

    public List<DoctorResponseDTO> searchByLocation(String location){
        return doctorRepository.findByLocation(location).stream().map(this::mapToDoctorResponseDTO).toList();
    }

    public List<DoctorResponseDTO> searchBySpecialty(String specialty){

        return doctorRepository.findBySpecialization(specialty).stream().map(this::mapToDoctorResponseDTO).toList();
    }
    private DoctorResponseDTO mapToDoctorResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getDoctorId(),
                doctor.getUser().getUser_Id(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getUser().getEmail(),
                doctor.getSpecialization(),
                doctor.getLocation(),
                doctor.getBio(),
                doctor.getPhone(),
                doctor.getUser().getStatus()
        );
    }

}
