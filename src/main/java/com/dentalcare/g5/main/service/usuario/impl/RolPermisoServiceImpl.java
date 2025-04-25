package com.dentalcare.g5.main.service.usuario.impl;

import com.dentalcare.g5.main.mapper.usuario.RolPermisoMapper;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.Permiso;
import com.dentalcare.g5.main.model.entity.usuario.Rol;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import com.dentalcare.g5.main.model.payload.usuario.RolPermisoFilterRequest;
import com.dentalcare.g5.main.repository.usuario.PermisoRepository;
import com.dentalcare.g5.main.repository.usuario.RolPerRepository;
import com.dentalcare.g5.main.repository.usuario.RolPermisoRepository;
import com.dentalcare.g5.main.repository.usuario.RolRepository;
import com.dentalcare.g5.main.service.usuario.RolPermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the RolPermisoService interface
 */
@Service
public class RolPermisoServiceImpl implements RolPermisoService {

    @Autowired
    private RolPerRepository rolPermisoRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PermisoRepository permisoRepository;
    @Autowired
    private RolPermisoMapper rolPermisoMapper;

    /**
     * Creates a new rol-permiso relationship
     * @param rolPermisoDto The rol-permiso DTO to create
     * @return The created rol-permiso DTO
     */
    @Override
    @Transactional
    public RolPermisoDto addRolPermiso(RolPermisoDto rolPermisoDto) {
        // Validate that both rol and permiso exist
        Rol rol = validateRol(rolPermisoDto.getRol().getId());
        Permiso permiso = validatePermiso(rolPermisoDto.getPermiso().getId());
        
        // Check for duplicate rol-permiso relationship
        validateUniquePair(null, rol.getId(), permiso.getId());
        
        try {
            // Create new rolPermiso entity
            RolPermiso rolPermiso = RolPermiso.builder()
                    .rol(rol)
                    .permiso(permiso)
                    .build();
            
            // Save the entity and return mapped DTO
            RolPermiso savedRolPermiso = rolPermisoRepository.save(rolPermiso);
            return rolPermisoMapper.toDto(savedRolPermiso);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not create rol-permiso relationship due to a constraint violation.", e);
        }
    }

    /**
     * Updates an existing rol-permiso relationship
     * @param rolPermisoDto The rol-permiso DTO with updated data
     * @return The updated rol-permiso DTO
     */
    @Override
    @Transactional
    public RolPermisoDto updateRolPermiso(RolPermisoDto rolPermisoDto) {
        // Find the rolPermiso by ID
        RolPermiso existingRolPermiso = rolPermisoRepository.findById(rolPermisoDto.getId())
                .orElseThrow(() -> new RuntimeException("RolPermiso not found with ID: " + rolPermisoDto.getId()));
        
        // Validate that both rol and permiso exist
        Rol rol = validateRol(rolPermisoDto.getRol().getId());
        Permiso permiso = validatePermiso(rolPermisoDto.getPermiso().getId());
        
        // Check for duplicate rol-permiso relationship but exclude current one
        validateUniquePair(rolPermisoDto.getId(), rol.getId(), permiso.getId());
        
        try {
            // Update fields
            existingRolPermiso.setRol(rol);
            existingRolPermiso.setPermiso(permiso);
            
            // Save updated entity and return mapped DTO
            RolPermiso updatedRolPermiso = rolPermisoRepository.save(existingRolPermiso);
            return rolPermisoMapper.toDto(updatedRolPermiso);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not update rol-permiso relationship due to a constraint violation.", e);
        }
    }

    /**
     * Retrieves a rol-permiso relationship by ID
     * @param id The rol-permiso ID
     * @return The rol-permiso DTO
     */
    @Override
    public RolPermisoDto getRolPermisoById(int id) {
        RolPermiso rolPermiso = rolPermisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RolPermiso not found with ID: " + id));
        return rolPermisoMapper.toDto(rolPermiso);
    }

    /**
     * Retrieves all rol-permiso relationships
     * @return List of all rol-permiso DTOs
     */
    @Override
    public List<RolPermisoDto> getAllRolPermisos() {
        List<RolPermiso> rolPermisos = rolPermisoRepository.findAll();
        return rolPermisos.stream()
                .map(rolPermisoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters rol-permiso relationships based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered rol-permiso DTOs
     */
    @Override
    public List<RolPermisoDto> filterRolPermisos(RolPermisoFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<RolPermiso> allRolPermisos = rolPermisoRepository.findAll();
        
        List<RolPermiso> filteredRolPermisos = allRolPermisos.stream()
                .filter(rolPermiso -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(rolPermiso.getId())) {
                        return false;
                    }
                    
                    // Rol ID filter
                    if (payload.getRolId() != null && 
                            (rolPermiso.getRol() == null || !payload.getRolId().equals(rolPermiso.getRol().getId()))) {
                        return false;
                    }
                    
                    // Permiso ID filter
                    if (payload.getPermisoId() != null && 
                            (rolPermiso.getPermiso() == null || !payload.getPermisoId().equals(rolPermiso.getPermiso().getId()))) {
                        return false;
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
        
        return filteredRolPermisos.stream()
                .map(rolPermisoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a rol-permiso relationship by ID
     * @param id The rol-permiso ID to delete
     */
    @Override
    @Transactional
    public void deleteRolPermiso(int id) {
        if (!rolPermisoRepository.existsById(id)) {
            throw new RuntimeException("RolPermiso not found with ID: " + id);
        }
        
        try {
            rolPermisoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete rol-permiso relationship due to existing dependencies", e);
        }
    }
    
    /**
     * Validates that a rol exists
     * @param rolId The rol ID to validate
     * @return The validated Rol entity
     */
    private Rol validateRol(Integer rolId) {
        if (rolId == null) {
            throw new RuntimeException("Rol ID cannot be null");
        }
        
        return rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol not found with ID: " + rolId));
    }
    
    /**
     * Validates that a permiso exists
     * @param permisoId The permiso ID to validate
     * @return The validated Permiso entity
     */
    private Permiso validatePermiso(Integer permisoId) {
        if (permisoId == null) {
            throw new RuntimeException("Permiso ID cannot be null");
        }
        
        return permisoRepository.findById(permisoId)
                .orElseThrow(() -> new RuntimeException("Permiso not found with ID: " + permisoId));
    }
    
    /**
     * Validates the uniqueness of the rol-permiso pair
     * @param currentId The current rolPermiso ID (null for new relationships)
     * @param rolId The rol ID to validate
     * @param permisoId The permiso ID to validate
     */
}

