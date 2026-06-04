package com.example.MedicalPlatform.dto.response;

public class UserResponseDTO {

    private Long userId;
    private String email;
    private String role;
    private String status;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long userId, String email, String role, String status) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
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
