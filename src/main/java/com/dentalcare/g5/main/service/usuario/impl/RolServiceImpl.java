package com.dentalcare.g5.main.service.usuario.impl;

import com.dentalcare.g5.main.mapper.usuario.RolMapper;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.entity.usuario.Rol;
import com.dentalcare.g5.main.model.payload.usuario.RolFilterRequest;
import com.dentalcare.g5.main.repository.usuario.RolRepository;
import com.dentalcare.g5.main.service.usuario.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the RolService interface
 */
@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    /**
     * Creates a new rol
     * @param rolDto The rol DTO to create
     * @return The created rol DTO
     */
    @Override
    @Transactional
    public RolDto addRol(RolDto rolDto) {
        // Check if name already exists
        validateUniqueName(null, rolDto.getNombre());
        
        try {
            // Create new rol entity
            Rol rol = Rol.builder()
                    .nombre(rolDto.getNombre())
                    .rolPermisos(new ArrayList<>())
                    .build();
            
            // Save the entity and return mapped DTO
            Rol savedRol = rolRepository.save(rol);
            return rolMapper.toDto(savedRol);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not create rol due to a constraint violation. Name must be unique.", e);
        }
    }

    /**
     * Updates an existing rol
     * @param rolDto The rol DTO with updated data
     * @return The updated rol DTO
     */
    @Override
    @Transactional
    public RolDto updateRol(RolDto rolDto) {
        // Find the rol by ID
        Rol existingRol = rolRepository.findById(rolDto.getId())
                .orElseThrow(() -> new RuntimeException("Rol not found with ID: " + rolDto.getId()));
        
        // Check if the new name is already in use by another rol
        validateUniqueName(rolDto.getId(), rolDto.getNombre());
        
        try {
            // Update fields
            existingRol.setNombre(rolDto.getNombre());
            
            // Save updated entity and return mapped DTO
            Rol updatedRol = rolRepository.save(existingRol);
            return rolMapper.toDto(updatedRol);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not update rol due to a constraint violation. Name must be unique.", e);
        }
    }

    /**
     * Retrieves a rol by ID
     * @param id The rol ID
     * @return The rol DTO
     */
    @Override
    public RolDto getRolById(int id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol not found with ID: " + id));
        return rolMapper.toDto(rol);
    }

    /**
     * Retrieves all roles
     * @return List of all rol DTOs
     */
    @Override
    public List<RolDto> getAllRoles() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream()
                .map(rolMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters roles based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered rol DTOs
     */
    @Override
    public List<RolDto> filterRoles(RolFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<Rol> allRoles = rolRepository.findAll();
        
        List<Rol> filteredRoles = allRoles.stream()
                .filter(rol -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(rol.getId())) {
                        return false;
                    }
                    
                    // Nombre filter
                    if (payload.getNombre() != null && (rol.getNombre() == null || 
                            !rol.getNombre().toLowerCase().contains(payload.getNombre().toLowerCase()))) {
                        return false;
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
        
        return filteredRoles.stream()
                .map(rolMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a rol by ID
     * @param id The rol ID to delete
     */
    @Override
    @Transactional
    public void deleteRol(int id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol not found with ID: " + id));
        
        // Check if this rol has associated users before deletion
        if (rol.getUsuario() != null) {
            throw new RuntimeException("Cannot delete rol because it is assigned to one or more users");
        }
        
        try {
            // Delete any associated RolPermiso records first
            if (rol.getRolPermisos() != null && !rol.getRolPermisos().isEmpty()) {
                rol.getRolPermisos().clear();
                rolRepository.save(rol);
            }
            
            // Then delete the rol
            rolRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete rol due to existing relationships", e);
        }
    }
    
    /**
     * Validates the uniqueness of the rol name
     * @param currentId The current rol ID (null for new roles)
     * @param nombre The rol name to validate
     */
    private void validateUniqueName(Integer currentId, String nombre) {
        if (nombre == null) {
            return;
        }
        
        Optional<Rol> existingRol = rolRepository.findByNombre(nombre);
        
        if (existingRol.isPresent() && (currentId == null || !currentId.equals(existingRol.get().getId()))) {
            throw new RuntimeException("Rol with name '" + nombre + "' already exists");
        }
    }
}

