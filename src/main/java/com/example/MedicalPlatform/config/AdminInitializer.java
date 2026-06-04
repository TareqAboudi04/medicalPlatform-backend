package com.example.MedicalPlatform.config;


import com.example.MedicalPlatform.entity.User;
import com.example.MedicalPlatform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(String... args){
        String adminEmail = "admin@medicalplatform.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin12345"));
            admin.setRole("ADMIN");
            admin.setStatus("APPROVED");
            userRepository.save(admin);
            System.out.println("Admin user created successfully.");
        }
        else {
            System.out.println("Admin user already exists.");
        }
    }
}
