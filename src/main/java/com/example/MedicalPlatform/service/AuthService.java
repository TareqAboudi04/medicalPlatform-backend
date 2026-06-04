package com.example.MedicalPlatform.service;

import com.example.MedicalPlatform.dto.request.DoctorRegisterReqDTO;
import com.example.MedicalPlatform.dto.request.LoginRequestDTO;
import com.example.MedicalPlatform.dto.request.PatientRegisterReqDTO;
import com.example.MedicalPlatform.dto.response.AuthResponseDTO;
import com.example.MedicalPlatform.entity.Doctor;
import com.example.MedicalPlatform.entity.Patient;
import com.example.MedicalPlatform.entity.User;
import com.example.MedicalPlatform.repository.DoctorRepository;
import com.example.MedicalPlatform.repository.PatientRepository;
import com.example.MedicalPlatform.repository.UserRepository;
import com.example.MedicalPlatform.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public String registerPatient(PatientRegisterReqDTO request){
        if (userRepository.existsByEmail(request.getEmail())){
            return "Email exists!";
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("PATIENT");
        user.setStatus("APPROVED");
        User savedUser = userRepository.save(user);

        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setPhone(request.getPhone());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setUser(savedUser);
        patientRepository.save(patient);
        return "Welcome " + patient.getFirstName();

    }

    @Transactional
    public String registerDoctor(DoctorRegisterReqDTO request){
        if (userRepository.existsByEmail(request.getEmail())){
            return "Email exists!";
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("DOCTOR");
        user.setStatus("PENDING");
        User savedUser = userRepository.save(user);


        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setPhone(request.getPhone());
        doctor.setLocation(request.getLocation());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setBio(request.getBio());
        doctor.setUser(savedUser);
        doctorRepository.save(doctor);
        return "Welcome " + doctor.getFirstName();
    }

    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong Password!");
        }

        if (!user.getStatus().equalsIgnoreCase("APPROVED")) {
            throw new RuntimeException("Account is not approved yet!");
        }

        String token = jwtService.generateToken(user);

        Long patientId = null;
        Long doctorId = null;

        if (user.getRole().equalsIgnoreCase("PATIENT")) {
            patientId = patientRepository.findByUser(user)
                    .map(patient -> patient.getPatientId())
                    .orElse(null);
        }

        if (user.getRole().equalsIgnoreCase("DOCTOR")) {
            doctorId = doctorRepository.findByUser(user)
                    .map(doctor -> doctor.getDoctorId())
                    .orElse(null);
        }

        return new AuthResponseDTO(
                token,
                user.getUser_Id(),
                patientId,
                doctorId,
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );
    }

    }

