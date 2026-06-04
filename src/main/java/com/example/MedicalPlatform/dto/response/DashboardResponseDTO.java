package com.example.MedicalPlatform.dto.response;

public class DashboardResponseDTO {

    private long approvedDoctors;
    private long approvedPatients;
    private long appointments;

    public DashboardResponseDTO() {
    }

    public DashboardResponseDTO(long approvedDoctors, long approvedPatients, long appointments) {
        this.approvedDoctors = approvedDoctors;
        this.approvedPatients = approvedPatients;
        this.appointments = appointments;
    }

    public long getApprovedDoctors() {
        return approvedDoctors;
    }

    public void setApprovedDoctors(long approvedDoctors) {
        this.approvedDoctors = approvedDoctors;
    }

    public long getApprovedPatients() {
        return approvedPatients;
    }

    public void setApprovedPatients(long approvedPatients) {
        this.approvedPatients = approvedPatients;
    }

    public long getAppointments() {
        return appointments;
    }

    public void setAppointments(long appointments) {
        this.appointments = appointments;
    }

}
