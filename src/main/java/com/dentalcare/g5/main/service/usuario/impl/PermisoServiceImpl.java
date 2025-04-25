package com.dentalcare.g5.main.service.usuario.impl;

import com.dentalcare.g5.main.mapper.usuario.PermisoMapper;
import com.dentalcare.g5.main.model.dto.usuario.PermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.Permiso;
import com.dentalcare.g5.main.model.payload.usuario.PermisoFilterRequest;
import com.dentalcare.g5.main.repository.usuario.PermisoRepository;
import com.dentalcare.g5.main.service.usuario.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the PermisoService interface
 */
@Service
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;
    private final PermisoMapper permisoMapper;

    @Autowired
    public PermisoServiceImpl(PermisoRepository permisoRepository, PermisoMapper permisoMapper) {
        this.permisoRepository = permisoRepository;
        this.permisoMapper = permisoMapper;
    }

    /**
     * Creates a new permiso
     * @param permisoDto The permiso DTO to create
     * @return The created permiso DTO
     */
    @Override
    @Transactional
    public PermisoDto addPermiso(PermisoDto permisoDto) {
        // Check if name already exists
        validateUniqueName(null, permisoDto.getNombre());
        
        try {
            // Create new permiso entity
            Permiso permiso = Permiso.builder()
                    .nombre(permisoDto.getNombre())
                    .descripcion(permisoDto.getDescripcion())
                    .activo(permisoDto.getActivo() != null ? permisoDto.getActivo() : true)
                    .rolPermisos(new ArrayList<>())
                    .build();
            
            // Save the entity and return mapped DTO
            Permiso savedPermiso = permisoRepository.save(permiso);
            return permisoMapper.toDto(savedPermiso);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not create permiso due to a constraint violation. Name must be unique.", e);
        }
    }

    /**
     * Updates an existing permiso
     * @param permisoDto The permiso DTO with updated data
     * @return The updated permiso DTO
     */
    @Override
    @Transactional
    public PermisoDto updatePermiso(PermisoDto permisoDto) {
        // Find the permiso by ID
        Permiso existingPermiso = permisoRepository.findById(permisoDto.getId())
                .orElseThrow(() -> new RuntimeException("Permiso not found with ID: " + permisoDto.getId()));
        
        // Check if the new name is already in use by another permiso
        validateUniqueName(permisoDto.getId(), permisoDto.getNombre());
        
        try {
            // Update fields
            existingPermiso.setNombre(permisoDto.getNombre());
            existingPermiso.setDescripcion(permisoDto.getDescripcion());
            
            // Only update activo if provided
            if (permisoDto.getActivo() != null) {
                existingPermiso.setActivo(permisoDto.getActivo());
            }
            
            // Save updated entity and return mapped DTO
            Permiso updatedPermiso = permisoRepository.save(existingPermiso);
            return permisoMapper.toDto(updatedPermiso);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not update permiso due to a constraint violation. Name must be unique.", e);
        }
    }

    /**
     * Retrieves a permiso by ID
     * @param id The permiso ID
     * @return The permiso DTO
     */
    @Override
    public PermisoDto getPermisoById(int id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso not found with ID: " + id));
        return permisoMapper.toDto(permiso);
    }

    /**
     * Retrieves all permisos
     * @return List of all permiso DTOs
     */
    @Override
    public List<PermisoDto> getAllPermisos() {
        List<Permiso> permisos = permisoRepository.findAll();
        return permisos.stream()
                .map(permisoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters permisos based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered permiso DTOs
     */
    @Override
    public List<PermisoDto> filterPermisos(PermisoFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<Permiso> allPermisos = permisoRepository.findAll();
        
        List<Permiso> filteredPermisos = allPermisos.stream()
                .filter(permiso -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(permiso.getId())) {
                        return false;
                    }
                    
                    // Nombre filter
                    if (payload.getNombre() != null && (permiso.getNombre() == null || 
                            !permiso.getNombre().toLowerCase().contains(payload.getNombre().toLowerCase()))) {
                        return false;
                    }
                    
                    // Descripcion filter
                    if (payload.getDescripcion() != null && (permiso.getDescripcion() == null || 
                            !permiso.getDescripcion().toLowerCase().contains(payload.getDescripcion().toLowerCase()))) {
                        return false;
                    }
                    
                    // Activo filter
                    if (payload.getActivo() != null && 
                            (permiso.getActivo() == null || !payload.getActivo().equals(permiso.getActivo()))) {
                        return false;
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
        
        return filteredPermisos.stream()
                .map(permisoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a permiso by ID
     * @param id The permiso ID to delete
     */
    @Override
    @Transactional
    public void deletePermiso(int id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso not found with ID: " + id));
        
        try {
            // Delete any associated RolPermiso records first
            if (permiso.getRolPermisos() != null && !permiso.getRolPermisos().isEmpty()) {
                permiso.getRolPermisos().clear();
                permisoRepository.save(permiso);
            }
            
            // Then delete the permiso
            permisoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete permiso due to existing relationships", e);
        }
    }
    
    /**
     * Validates the uniqueness of the permiso name
     * @param currentId The current permiso ID (null for new permisos)
     * @param nombre The permiso name to validate
     */
    private void validateUniqueName(Integer currentId, String nombre) {
        if (nombre == null) {
            return;
        }
        
        Optional<Permiso> existingPermiso = permisoRepository.findByNombre(nombre);
        
        if (existingPermiso.isPresent() && (currentId == null || !currentId.equals(existingPermiso.get().getId()))) {
            throw new RuntimeException("Permiso with name '" + nombre + "' already exists");
        }
    }
}

