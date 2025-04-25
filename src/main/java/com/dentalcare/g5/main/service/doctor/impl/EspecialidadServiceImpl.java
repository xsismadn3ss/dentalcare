package com.dentalcare.g5.main.service.doctor.impl;

import com.dentalcare.g5.main.mapper.doctor.EspecialidadMapper;
import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.entity.doctor.Especialidad;
import com.dentalcare.g5.main.model.payload.doctor.EspecialidadFilterRequest;
import com.dentalcare.g5.main.repository.doctor.EspecialidadRepository;
import com.dentalcare.g5.main.service.doctor.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the EspecialidadService interface
 */
@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;
    private final EspecialidadMapper especialidadMapper;

    @Autowired
    public EspecialidadServiceImpl(EspecialidadRepository especialidadRepository,
                                  EspecialidadMapper especialidadMapper) {
        this.especialidadRepository = especialidadRepository;
        this.especialidadMapper = especialidadMapper;
    }

    /**
     * Creates a new especialidad
     * @param especialidadDto The especialidad data
     * @return The created especialidad DTO
     */
    @Override
    public EspecialidadDto addEspecialidad(EspecialidadDto especialidadDto) {
        // Check if name already exists
        Optional<Especialidad> existingEspecialidad = especialidadRepository.findAll().stream()
                .filter(e -> e.getName().equalsIgnoreCase(especialidadDto.getName()))
                .findFirst();
                
        if (existingEspecialidad.isPresent()) {
            throw new RuntimeException("Especialidad with name '" + especialidadDto.getName() + "' already exists");
        }
        
        try {
            // Create new especialidad entity
            Especialidad especialidad = Especialidad.builder()
                    .name(especialidadDto.getName())
                    .build();
            
            // Save the entity and return mapped DTO
            Especialidad savedEspecialidad = especialidadRepository.save(especialidad);
            return especialidadMapper.toDto(savedEspecialidad);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not create especialidad. Possible duplicate name.", e);
        }
    }

    /**
     * Updates an existing especialidad
     * @param especialidadDto The updated especialidad data
     * @return The updated especialidad DTO
     */
    @Override
    public EspecialidadDto updateEspecialidad(EspecialidadDto especialidadDto) {
        // Find the especialidad by ID
        Especialidad existingEspecialidad = especialidadRepository.findById(especialidadDto.getId())
                .orElseThrow(() -> new RuntimeException("Especialidad not found with ID: " + especialidadDto.getId()));
        
        // Check if name already exists for another especialidad
        Optional<Especialidad> duplicateEspecialidad = especialidadRepository.findAll().stream()
                .filter(e -> e.getName().equalsIgnoreCase(especialidadDto.getName())
                        && !e.getId().equals(especialidadDto.getId()))
                .findFirst();
                
        if (duplicateEspecialidad.isPresent()) {
            throw new RuntimeException("Another especialidad with name '" + especialidadDto.getName() + "' already exists");
        }
        
        try {
            // Update fields
            existingEspecialidad.setName(especialidadDto.getName());
            
            // Save updated entity and return mapped DTO
            Especialidad updatedEspecialidad = especialidadRepository.save(existingEspecialidad);
            return especialidadMapper.toDto(updatedEspecialidad);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not update especialidad. Possible duplicate name.", e);
        }
    }

    /**
     * Retrieves an especialidad by ID
     * @param id The especialidad ID
     * @return The especialidad DTO
     */
    @Override
    public EspecialidadDto getEspecialidadById(int id) {
        Especialidad especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad not found with ID: " + id));
        return especialidadMapper.toDto(especialidad);
    }

    /**
     * Retrieves all especialidades
     * @return List of all especialidad DTOs
     */
    @Override
    public List<EspecialidadDto> getAllEspecialidades() {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        return especialidades.stream()
                .map(especialidadMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters especialidades based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered especialidad DTOs
     */
    @Override
    public List<EspecialidadDto> filterEspecialidades(EspecialidadFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<Especialidad> allEspecialidades = especialidadRepository.findAll();
        
        List<Especialidad> filteredEspecialidades = allEspecialidades.stream()
                .filter(especialidad -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(especialidad.getId())) {
                        return false;
                    }
                    
                    // Name filter
                    if (payload.getName() != null && (especialidad.getName() == null || 
                            !especialidad.getName().toLowerCase().contains(payload.getName().toLowerCase()))) {
                        return false;
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
        
        return filteredEspecialidades.stream()
                .map(especialidadMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes an especialidad by ID
     * @param id The especialidad ID to delete
     */
    @Override
    public void deleteEspecialidad(int id) {
        if (!especialidadRepository.existsById(id)) {
            throw new RuntimeException("Especialidad not found with ID: " + id);
        }
        
        try {
            especialidadRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete especialidad because it has associated doctors", e);
        }
    }
}

