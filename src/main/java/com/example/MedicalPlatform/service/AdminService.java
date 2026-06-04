package com.example.MedicalPlatform.service;

import com.example.MedicalPlatform.dto.response.DashboardResponseDTO;
import com.example.MedicalPlatform.dto.response.UserResponseDTO;
import com.example.MedicalPlatform.entity.User;
import com.example.MedicalPlatform.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;


    public AdminService(UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<UserResponseDTO> getPendingDoctors() {
        return userRepository.findAllByRoleAndStatus("DOCTOR", "PENDING").stream()
                .map(user -> new UserResponseDTO(
                        user.getUser_Id(),
                        user.getEmail(),
                        user.getRole(),
                        user.getStatus()
                )).toList();
    }

    @Transactional
    public String approveDoctor(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found!"));

        if (!user.getRole().equalsIgnoreCase("DOCTOR")){
            return "This user is not a doctor";
        }

        if (!user.getStatus().equalsIgnoreCase("PENDING")){
            return "Doctor is not pending approval";
        }

        user.setStatus("APPROVED");
        userRepository.save(user);
        return "Doctor approved successfully";

    }

    @Transactional
    public String rejectDoctor(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found!"));

        if (!user.getRole().equalsIgnoreCase("DOCTOR")){
            return "This user is not a doctor";
        }

        if (!user.getStatus().equalsIgnoreCase("PENDING")){
            return "Doctor is not pending approval";
        }

        user.setStatus("REJECTED");
        userRepository.save(user);
        return "Doctor rejected successfully";

    }
    public String blockUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found!"));

        user.setStatus("BLOCKED");
        userRepository.save(user);
        return "User blocked successfully";
    }

    public DashboardResponseDTO getDashboardCounts(){
        long doctorsCount = userRepository.countByRoleAndStatus("DOCTOR", "APPROVED");
        long patientsCount = userRepository.countByRoleAndStatus("PATIENT", "APPROVED");
        long appointmentsCount = appointmentRepository.count();
        return new DashboardResponseDTO(doctorsCount, patientsCount, appointmentsCount);
    }


    public String countDoctors(){
        Long doctorsCount = (Long)userRepository.countByRoleAndStatus("DOCTOR","APPROVED");
        return "Number of approved doctors: " + doctorsCount;
    }
    public String countPatients(){
        Long patientsCount = (Long)userRepository.countByRoleAndStatus("PATIENT","APPROVED");
        return "Number of patients: " + patientsCount;
    }
    public String countAppointments(){
        Long appointmentsCount = (Long) appointmentRepository.count();
        return "Number of appointments: " + appointmentsCount;
    }


}
