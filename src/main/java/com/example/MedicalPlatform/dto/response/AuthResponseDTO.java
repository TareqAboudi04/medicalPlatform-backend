package com.example.MedicalPlatform.dto.response;

public class AuthResponseDTO {
    private String token;
    private Long userId;
    private Long patientId;
    private Long doctorId;
    private String email;
    private String role;
    private String status;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token, Long userId, Long patientId, Long doctorId, String email, String role, String status) {
        this.token = token;
        this.userId = userId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}