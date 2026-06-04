package com.example.MedicalPlatform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PrescriptionRequestDTO {

    @NotNull(message = "Appointment ID is required")
    private Long appointmentId;
    @NotNull(message = "Medicine name is required")
    private String medicineName;
    @NotBlank(message = "Instructions are required")
    private String instructions;

    public PrescriptionRequestDTO() {
    }

    public PrescriptionRequestDTO(Long appointmentId, String medicineName, String instructions) {
        this.appointmentId = appointmentId;
        this.medicineName = medicineName;
        this.instructions = instructions;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
