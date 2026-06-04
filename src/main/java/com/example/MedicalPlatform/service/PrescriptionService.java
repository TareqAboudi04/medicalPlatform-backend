package com.example.MedicalPlatform.service;

import com.example.MedicalPlatform.dto.request.PrescriptionRequestDTO;
import com.example.MedicalPlatform.dto.response.PrescriptionResponseDTO;
import com.example.MedicalPlatform.entity.Appointment;
import com.example.MedicalPlatform.entity.Prescription;
import com.example.MedicalPlatform.repository.AppointmentRepository;
import com.example.MedicalPlatform.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, AppointmentRepository appointmentRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public String createPrescription(PrescriptionRequestDTO request){
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId()).orElseThrow(() -> new RuntimeException("There is no appointment with this ID!"));

        if (prescriptionRepository.findByAppointment_AppointmentId(request.getAppointmentId()).isPresent()){
            return "This appointment already has a prescription!";
        }

        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setMedicineName(request.getMedicineName());
        prescription.setInstructions(request.getInstructions());
        prescriptionRepository.save(prescription);

        return "Prescription is saved for this appointment ID: " + appointment.getAppointmentId();
    }

    public PrescriptionResponseDTO getPrescriptionByAppointment(Long appointmentId){
        Prescription prescription =  prescriptionRepository.findByAppointment_AppointmentId(appointmentId).orElseThrow(() -> new RuntimeException("No Appointment With This ID!"));

        return mapToPrescriptionResponseDTO(prescription);
    }

    private PrescriptionResponseDTO mapToPrescriptionResponseDTO(Prescription prescription) {

        Appointment appointment = prescription.getAppointment();

        String patientName = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();

        String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();

        return new PrescriptionResponseDTO(
                prescription.getPrescriptionId(),
                appointment.getAppointmentId(),
                appointment.getPatient().getPatientId(),
                patientName,
                appointment.getDoctor().getDoctorId(),
                doctorName,
                prescription.getMedicineName(),
                prescription.getInstructions(),
                prescription.getCreatedAt()
        );
    }

}
