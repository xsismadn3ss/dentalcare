package com.dentalcare.g5.main.service.cita.impl;

import com.dentalcare.g5.main.mapper.cita.CitaMapper;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.entity.Paciente;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import com.dentalcare.g5.main.model.payload.cita.CitaCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaUpdateRequest;
import com.dentalcare.g5.main.repository.PacienteRepository;
import com.dentalcare.g5.main.repository.cita.CitaRepository;
import com.dentalcare.g5.main.repository.doctor.DoctorRepository;
import com.dentalcare.g5.main.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the CitaService interface
 */
@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private CitaMapper citaMapper;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * Creates a new cita
     * @param payload The cita creation request
     * @return The created cita DTO
     */
    @Override
    public CitaDto addCita(CitaCreateRequest payload) {
        // Find doctor and paciente by ID
        Doctor doctor = doctorRepository.findById(payload.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + payload.getDoctorId()));
        
        Paciente paciente = pacienteRepository.findById(payload.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente not found with ID: " + payload.getPacienteId()));
        
        // Create new cita entity
        Cita cita = Cita.builder()
                .fecha(payload.getFecha())
                .hora(payload.getHora())
                .motivo(payload.getMotivo())
                .doctor(doctor)
                .paciente(paciente)
                .notas(new ArrayList<>())
                .tratamientos(new ArrayList<>())
                .build();
        
        // Save the entity and return mapped DTO
        Cita savedCita = citaRepository.save(cita);
        return citaMapper.toDto(savedCita);
    }

    /**
     * Updates an existing cita
     * @param payload The cita update request
     * @return The updated cita DTO
     */
    @Override
    public CitaDto updateCita(CitaUpdateRequest payload) {
        // Find the cita by ID
        Cita existingCita = citaRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Cita not found with ID: " + payload.getId()));
        
        // Update fields
        if (payload.getFecha() != null) {
            existingCita.setFecha(payload.getFecha());
        }
        
        if (payload.getHora() != null) {
            existingCita.setHora(payload.getHora());
        }
        
        if (payload.getMotivo() != null) {
            existingCita.setMotivo(payload.getMotivo());
        }
        
        // Save updated entity and return mapped DTO
        Cita updatedCita = citaRepository.save(existingCita);
        return citaMapper.toDto(updatedCita);
    }



    /**
     * Retrieves a cita by ID
     * @param id The cita ID
     * @return The cita DTO
     */
    @Override
    public Optional<CitaDto> getCitaById(int id) {
        Optional<Cita> cita = citaRepository.findById(id);
        return cita.map(citaMapper::toDto);
    }

    /**
     * Retrieves all citas
     * @return List of all cita DTOs
     */
    @Override
    public List<CitaDto> getAllCitas() {
        List<Cita> citas = citaRepository.findAll();
        return citas.stream()
                .map(citaMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters citas based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered cita DTOs
     */
    @Override
    public List<CitaDto> filterCitas(CitaFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<Cita> allCitas = citaRepository.findAll();
        
        List<Cita> filteredCitas = allCitas.stream()
                .filter(cita -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(cita.getId())) {
                        return false;
                    }
                    
                    // Date range filter
                    if (payload.getFechaDesde() != null && cita.getFecha().isBefore(payload.getFechaDesde())) {
                        return false;
                    }
                    
                    if (payload.getFechaHasta() != null && cita.getFecha().isAfter(payload.getFechaHasta())) {
                        return false;
                    }
                    
                    // Time range filter (simple string comparison for demo)
                    if (payload.getHoraDesde() != null && cita.getHora().before(payload.getHoraDesde())) {
                        return false;
                    }
                    
                    if (payload.getHoraHasta() != null && cita.getHora().after(payload.getHoraHasta())) {
                        return false;
                    }
                    
                    // Motivo filter
                    if (payload.getMotivo() != null && !cita.getMotivo().toLowerCase()
                            .contains(payload.getMotivo().toLowerCase())) {
                        return false;
                    }
                    
                    // Doctor ID filter
                    if (payload.getDoctorId() != null && !payload.getDoctorId().equals(cita.getDoctor().getId())) {
                        return false;
                    }
                    
                    // Paciente ID filter
                    return payload.getPacienteId() == null || payload.getPacienteId().equals(cita.getPaciente().getId());
                })
                .toList();
        
        return filteredCitas.stream()
                .map(citaMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a cita by ID
     * @param id The cita ID to delete
     */
    @Override
    public void deleteCita(int id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita not found with ID: " + id);
        }
        citaRepository.deleteById(id);
    }
}

