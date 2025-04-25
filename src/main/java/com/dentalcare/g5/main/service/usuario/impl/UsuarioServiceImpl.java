package com.dentalcare.g5.main.service.usuario.impl;

import com.dentalcare.g5.main.mapper.usuario.UsuarioMapper;
import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.entity.usuario.Rol;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioFilterRequest;
import com.dentalcare.g5.main.repository.usuario.RolRepository;
import com.dentalcare.g5.main.repository.usuario.UsuarioRepository;
import com.dentalcare.g5.main.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the UsuarioService interface
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                             RolRepository rolRepository,
                             UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Creates a new usuario
     * @param payload The usuario creation request
     * @return The created usuario DTO
     */
    @Override
    @Transactional
    public UsuarioDto addUsuario(UsuarioCreateRequest payload) {
        // Check for unique constraints: username, email, telefono
        validateUniqueConstraints(null, payload.getUsername(), payload.getEmail(), payload.getTelefono());
        
        try {
            // Create new usuario entity
            Usuario usuario = Usuario.builder()
                    .nombre(payload.getNombre())
                    .apellido(payload.getApellido())
                    .email(payload.getEmail())
                    .telefono(payload.getTelefono())
                    // Assuming these fields exist in the entity based on UsuarioCreateRequest
                    .username(payload.getUsername())
                    .password(payload.getPassword()) // In production, this would be encrypted
                    .build();
            
            // By default, assign a basic role if available
            Optional<Rol> defaultRol = rolRepository.findByNombre("USER");
            defaultRol.ifPresent(usuario::setRol);
            
            // Save the entity and return mapped DTO
            Usuario savedUsuario = usuarioRepository.save(usuario);
            return usuarioMapper.toDto(savedUsuario);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not create usuario due to a constraint violation. Check uniqueness of email, username and telefono.", e);
        }
    }

    /**
     * Updates an existing usuario
     * @param usuarioDto The usuario DTO with updated data
     * @return The updated usuario DTO
     */
    @Override
    @Transactional
    public UsuarioDto updateUsuario(UsuarioDto usuarioDto) {
        // Find the usuario by ID
        Usuario existingUsuario = usuarioRepository.findById(usuarioDto.getId())
                .orElseThrow(() -> new RuntimeException("Usuario not found with ID: " + usuarioDto.getId()));
        
        // Check unique constraints but exclude current usuario
        // Assuming these fields exist in the entity
        validateUniqueConstraints(
            usuarioDto.getId(), 
            usuarioDto.getUsername(), 
            usuarioDto.getEmail(), 
            usuarioDto.getTelefono()
        );
        
        try {
            // Update fields
            existingUsuario.setNombre(usuarioDto.getNombre());
            existingUsuario.setApellido(usuarioDto.getApellido());
            existingUsuario.setEmail(usuarioDto.getEmail());
            existingUsuario.setTelefono(usuarioDto.getTelefono());
            // Assuming these fields exist in the entity
            existingUsuario.setUsername(usuarioDto.getUsername());
            
            // Only update password if provided
            if (usuarioDto.getPassword() != null && !usuarioDto.getPassword().isEmpty()) {
                existingUsuario.setPassword(usuarioDto.getPassword()); // In production, this would be encrypted
            }
            
            // Update role if provided
            if (usuarioDto.getRol() != null && usuarioDto.getRol().getId() != null) {
                Rol rol = rolRepository.findById(usuarioDto.getRol().getId())
                        .orElseThrow(() -> new RuntimeException("Rol not found with ID: " + usuarioDto.getRol().getId()));
                existingUsuario.setRol(rol);
            }
            
            // Save updated entity and return mapped DTO
            Usuario updatedUsuario = usuarioRepository.save(existingUsuario);
            return usuarioMapper.toDto(updatedUsuario);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not update usuario due to a constraint violation. Check uniqueness of email, username and telefono.", e);
        }
    }

    /**
     * Retrieves a usuario by ID
     * @param id The usuario ID
     * @return The usuario DTO
     */
    @Override
    public UsuarioDto getUsuarioById(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario not found with ID: " + id));
        return usuarioMapper.toDto(usuario);
    }

    /**
     * Retrieves all usuarios
     * @return List of all usuario DTOs
     */
    @Override
    public List<UsuarioDto> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters usuarios based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered usuario DTOs
     */
    @Override
    public List<UsuarioDto> filterUsuarios(UsuarioFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<Usuario> allUsuarios = usuarioRepository.findAll();
        
        List<Usuario> filteredUsuarios = allUsuarios.stream()
                .filter(usuario -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(usuario.getId())) {
                        return false;
                    }
                    
                    // Nombre filter
                    if (payload.getNombre() != null && (usuario.getNombre() == null || 
                            !usuario.getNombre().toLowerCase().contains(payload.getNombre().toLowerCase()))) {
                        return false;
                    }
                    
                    // Apellido filter
                    if (payload.getApellido() != null && (usuario.getApellido() == null || 
                            !usuario.getApellido().toLowerCase().contains(payload.getApellido().toLowerCase()))) {
                        return false;
                    }
                    
                    // Email filter
                    if (payload.getEmail() != null && (usuario.getEmail() == null || 
                            !usuario.getEmail().toLowerCase().contains(payload.getEmail().toLowerCase()))) {
                        return false;
                    }
                    
                    // Telefono filter
                    if (payload.getTelefono() != null && (usuario.getTelefono() == null || 
                            !usuario.getTelefono().toLowerCase().contains(payload.getTelefono().toLowerCase()))) {
                        return false;
                    }
                    
                    // Rol ID filter
                    if (payload.getRolId() != null && (usuario.getRol() == null || 
                            !payload.getRolId().equals(usuario.getRol().getId()))) {
                        return false;
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
        
        return filteredUsuarios.stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a usuario by ID
     * @param id The usuario ID to delete
     */
    @Override
    @Transactional
    public void deleteUsuario(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario not found with ID: " + id));
        
        // Check if this user has associated entities before deletion
        if (usuario.getDoctor() != null || usuario.getPaciente() != null) {
            throw new RuntimeException("Cannot delete usuario because it is associated with a doctor or paciente record");
        }
        
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete usuario due to existing relationships", e);
        }
    }
    
    /**
     * Validates unique constraints for usuario fields
     */
    private void validateUniqueConstraints(Integer currentId, String username, String email, String telefono) {
        List<Usuario> existingUsuarios = usuarioRepository.findAll();
        
        // Check email uniqueness
        if (email != null) {
            Optional<Usuario> emailExists = existingUsuarios.stream()
                    .filter(u -> email.equalsIgnoreCase(u.getEmail()) && 
                            (currentId == null || !currentId.equals(u.getId())))
                    .findFirst();
            
            if (emailExists.isPresent()) {
                throw new RuntimeException("Email '" + email + "' is already in use");
            }
        }
        
        // Check telefono uniqueness
        if (telefono != null) {
            Optional<Usuario> telefonoExists = existingUsuarios.stream()
                    .filter(u -> telefono.equals(u.getTelefono()) && 
                            (currentId == null || !currentId.equals(u.getId())))
                    .findFirst();
            
            if (telefonoExists.isPresent()) {
                throw new RuntimeException("Telefono '" + telefono + "' is already in use");
            }
        }
        
        // Check username uniqueness
        if (username != null) {
            Optional<Usuario> usernameExists = existingUsuarios.stream()
                    .filter(u -> username.equalsIgnoreCase(u.getUsername()) && 
                            (currentId == null || !currentId.equals(u.getId())))
                    .findFirst();
            
            if (usernameExists.isPresent()) {
                throw new RuntimeException("Username '" + username + "' is already in use");
            }
        }
    }
}

