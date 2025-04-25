package com.dentalcare.g5.main.service.cita.impl;

import com.dentalcare.g5.main.mapper.cita.TratamientoMapper;
import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import com.dentalcare.g5.main.model.entity.cita.Tratamiento;
import com.dentalcare.g5.main.model.payload.cita.TratamientoCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.TratamientoFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.TratamientoUpdateRequest;
import com.dentalcare.g5.main.repository.cita.CitaRepository;
import com.dentalcare.g5.main.repository.cita.TratamientoRepository;
import com.dentalcare.g5.main.service.cita.TratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the TratamientoService interface
 */
@Service
public class TratamientoServiceImpl implements TratamientoService {

    @Autowired
    private TratamientoRepository tratamientoRepository;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private TratamientoMapper tratamientoMapper;

    /**
     * Creates a new tratamiento
     * @param payload The tratamiento creation request
     * @return The created tratamiento DTO
     */
    @Override
    public TratamientoDto addTratamiento(TratamientoCreateRequest payload) {
        // Find the cita by ID
        Cita cita = citaRepository.findById(payload.getCitaId())
                .orElseThrow(() -> new RuntimeException("Cita not found with ID: " + payload.getCitaId()));
        
        // Create new tratamiento entity
        Tratamiento tratamiento = Tratamiento.builder()
                .Nombre(payload.getNombre())
                .pendiente(payload.getPendiente() != null ? payload.getPendiente() : true)
                .cita(cita)
                .build();
        
        // Save the entity and return mapped DTO
        Tratamiento savedTratamiento = tratamientoRepository.save(tratamiento);
        return tratamientoMapper.toDto(savedTratamiento);
    }

    /**
     * Updates an existing tratamiento
     * @param payload The tratamiento update request
     * @return The updated tratamiento DTO
     */
    @Override
    public TratamientoDto updateTratamiento(TratamientoUpdateRequest payload) {
        // Find the tratamiento by ID
        Tratamiento existingTratamiento = tratamientoRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Tratamiento not found with ID: " + payload.getId()));
        
        // Update fields
        if (payload.getNombre() != null) {
            existingTratamiento.setNombre(payload.getNombre());
        }
        
        if (payload.getPendiente() != null) {
            existingTratamiento.setPendiente(payload.getPendiente());
        }
        
        // Save updated entity and return mapped DTO
        Tratamiento updatedTratamiento = tratamientoRepository.save(existingTratamiento);
        return tratamientoMapper.toDto(updatedTratamiento);
    }

    /**
     * Retrieves a tratamiento by ID
     * @param id The tratamiento ID
     * @return The tratamiento DTO
     */
    @Override
    public TratamientoDto getTratamientoById(int id) {
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento not found with ID: " + id));
        return tratamientoMapper.toDto(tratamiento);
    }

    /**
     * Retrieves all tratamientos
     * @return List of all tratamiento DTOs
     */
    @Override
    public List<TratamientoDto> getAllTratamientos() {
        List<Tratamiento> tratamientos = tratamientoRepository.findAll();
        return tratamientos.stream()
                .map(tratamientoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all tratamientos associated with a specific cita
     * @param citaId The cita ID to filter by
     * @return List of tratamiento DTOs associated with the specified cita
     */
    @Override
    public List<TratamientoDto> getTratamientosByCitaId(int citaId) {
        // Check if cita exists
        if (!citaRepository.existsById(citaId)) {
            throw new RuntimeException("Cita not found with ID: " + citaId);
        }
        
        // In a real implementation, you would add a query method to TratamientoRepository
        // For simplicity, we'll filter in memory
        List<Tratamiento> allTratamientos = tratamientoRepository.findAll();
        
        List<Tratamiento> citaTratamientos = allTratamientos.stream()
                .filter(tratamiento -> tratamiento.getCita() != null && tratamiento.getCita().getId() == citaId)
                .toList();
                
        return citaTratamientos.stream()
                .map(tratamientoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters tratamientos based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered tratamiento DTOs
     */
    @Override
    public List<TratamientoDto> filterTratamientos(TratamientoFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<Tratamiento> allTratamientos = tratamientoRepository.findAll();
        
        List<Tratamiento> filteredTratamientos = allTratamientos.stream()
                .filter(tratamiento -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(tratamiento.getId())) {
                        return false;
                    }
                    
                    // Nombre filter
                    if (payload.getNombre() != null && !tratamiento.getNombre().toLowerCase()
                            .contains(payload.getNombre().toLowerCase())) {
                        return false;
                    }
                    
                    // Pendiente filter
                    if (payload.getPendiente() != null && !payload.getPendiente().equals(tratamiento.getPendiente())) {
                        return false;
                    }
                    
                    // Cita ID filter
                    return payload.getCitaId() == null || (tratamiento.getCita() != null &&
                            payload.getCitaId().equals(tratamiento.getCita().getId()));
                })
                .toList();
        
        return filteredTratamientos.stream()
                .map(tratamientoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a tratamiento by ID
     * @param id The tratamiento ID to delete
     */
    @Override
    public void deleteTratamiento(int id) {
        if (!tratamientoRepository.existsById(id)) {
            throw new RuntimeException("Tratamiento not found with ID: " + id);
        }
        tratamientoRepository.deleteById(id);
    }
}

