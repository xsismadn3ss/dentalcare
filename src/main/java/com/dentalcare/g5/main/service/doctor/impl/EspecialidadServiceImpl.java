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
    @Autowired
    private EspecialidadRepository especialidadRepository;
    
    @Autowired
    private EspecialidadRepository especialidadRepository;
    
    @Autowired
    private EspecialidadMapper especialidadMapper;
    
    /**
     * Validates the uniqueness of the especialidad name
     * @param currentId The current especialidad ID (null for new especialidades)
     * @param name The especialidad name to validate
     */
    private void validateUniqueName(Integer currentId, String name) {
        if (name == null) {
            return;
        }
        
        List<Especialidad> existingEspecialidades = especialidadRepository.findAll();
        
        Optional<Especialidad> duplicateEspecialidad = existingEspecialidades.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name) 
                    && (currentId == null || !currentId.equals(e.getId())))
                .findFirst();
                
        if (duplicateEspecialidad.isPresent()) {
            throw new RuntimeException("Especialidad with name '" + name + "' already exists");
        }
    }
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
    public EspecialidadDto updateEspecialidad(EspecialidadDto especialidadDto) {
        // Find the especialidad by ID
        Especialidad existingEspecialidad = especialidadRepository.findById(especialidadDto.getId())
                .orElseThrow(() -> new RuntimeException("Especialidad not found with ID: " + especialidadDto.getId()));
        
        // Check if name already exists for another especialidad
        validateUniqueName(especialidadDto.getId(), especialidadDto.getName());
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
    public EspecialidadDto addEspecialidad(EspecialidadDto especialidadDto) {
        // Check if name already exists
        validateUniqueName(null, especialidadDto.getName());
        }
    }
}

