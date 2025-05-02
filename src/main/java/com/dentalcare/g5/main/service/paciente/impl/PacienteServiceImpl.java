package com.dentalcare.g5.main.service.paciente.impl;

import com.dentalcare.g5.main.mapper.PacienteMapper;
import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.entity.Paciente;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import com.dentalcare.g5.main.model.payload.paciente.PacienteFilterRequest;
import com.dentalcare.g5.main.model.payload.paciente.PacienteUpdateRequest;
import com.dentalcare.g5.main.repository.PacienteRepository;
import com.dentalcare.g5.main.repository.usuario.UsuarioRepository;
import com.dentalcare.g5.main.service.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the PacienteService interface
 */
@Service
public class PacienteServiceImpl implements PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PacienteMapper pacienteMapper;
    /**
     * Creates a new paciente
     * @param pacienteDto The paciente DTO to create
     * @return The created paciente DTO
     * 
     * Note: Ideally this method should accept PacienteCreateRequest instead of PacienteDto
     */
    @Override
    @Transactional
    public PacienteDto addPaciente(PacienteDto pacienteDto) {
        try {
            // Get or create the usuario
            Usuario usuario;
            if (pacienteDto.getUsuario() != null && pacienteDto.getUsuario().getId() != null) {
                // Get existing usuario
                usuario = usuarioRepository.findById(pacienteDto.getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario not found with ID: " + pacienteDto.getUsuario().getId()));
            } else if (pacienteDto.getUsuario() != null) {
                // Create new usuario
                usuario = new Usuario();
                usuario.setNombre(pacienteDto.getUsuario().getNombre());
                usuario.setApellido(pacienteDto.getUsuario().getApellido());
                usuario.setEmail(pacienteDto.getUsuario().getEmail());
                usuario.setTelefono(pacienteDto.getUsuario().getTelefono());
                // Assuming these fields exist in the entity
                /*if (pacienteDto.getUsuario().getUsername() != null) {
                    usuario.setUsername(pacienteDto.getUsuario().getUsername());
                }
                if (pacienteDto.getUsuario().getPassword() != null) {
                    usuario.setPassword(pacienteDto.getUsuario().getPassword());
                }*/
                usuario = usuarioRepository.save(usuario);
            } else {
                throw new RuntimeException("Usuario information is required to create a paciente");
            }
            
            // Create new paciente entity
            Paciente paciente = Paciente.builder()
                    .fechaRegistro(LocalDate.now())
                    .usuario(usuario)
                    .citas(new ArrayList<>())
                    .build();
            
            // Save the entity and return mapped DTO
            Paciente savedPaciente = pacienteRepository.save(paciente);
            return pacienteMapper.toDto(savedPaciente);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not create paciente due to a constraint violation.", e);
        }
    }

    /**
     * Updates an existing paciente
     * @param payload The paciente update request
     * @return The updated paciente DTO
     */
    @Override
    @Transactional
    public PacienteDto updatePaciente(PacienteUpdateRequest payload) {
        // Find the paciente by ID
        Paciente existingPaciente = pacienteRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Paciente not found with ID: " + payload.getId()));
        
        try {
            // Update fields on the associated usuario
            if (existingPaciente.getUsuario() != null && 
                (payload.getNombre() != null || payload.getApellido() != null || payload.getTelefono() != null)) {
                
                Usuario usuario = existingPaciente.getUsuario();
                
                if (payload.getNombre() != null) {
                    usuario.setNombre(payload.getNombre());
                }
                
                if (payload.getApellido() != null) {
                    usuario.setApellido(payload.getApellido());
                }
                
                if (payload.getTelefono() != null) {
                    usuario.setTelefono(payload.getTelefono());
                }
                
                // Additional fields like direccion might be stored on Paciente entity in the future
                
                usuarioRepository.save(usuario);
            }
            
            // Save updated entity and return mapped DTO
            return pacienteMapper.toDto(existingPaciente);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not update paciente due to a constraint violation.", e);
        }
    }

    /**
     * Retrieves a paciente by ID
     * @param id The paciente ID
     * @return The paciente DTO
     */
    @Override
    public PacienteDto getPacienteById(int id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente not found with ID: " + id));
        return pacienteMapper.toDto(paciente);
    }

    /**
     * Retrieves all pacientes
     * @return List of all paciente DTOs
     */
    @Override
    public List<PacienteDto> getAllPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters pacientes based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered paciente DTOs
     */
    @Override
    public List<PacienteDto> filterPaciente(PacienteFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<Paciente> allPacientes = pacienteRepository.findAll();
        
        List<Paciente> filteredPacientes = allPacientes.stream()
                .filter(paciente -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(paciente.getId())) {
                        return false;
                    }
                    
                    // FechaRegistro range filter
                    if (payload.getFechaRegistroDesde() != null && 
                            (paciente.getFechaRegistro() == null || 
                             paciente.getFechaRegistro().isBefore(payload.getFechaRegistroDesde()))) {
                        return false;
                    }
                    
                    if (payload.getFechaRegistroHasta() != null && 
                            (paciente.getFechaRegistro() == null || 
                             paciente.getFechaRegistro().isAfter(payload.getFechaRegistroHasta()))) {
                        return false;
                    }
                    
                    // Usuario ID filter
                    if (payload.getUsuarioId() != null && 
                            (paciente.getUsuario() == null || 
                             !payload.getUsuarioId().equals(paciente.getUsuario().getId()))) {
                        return false;
                    }
                    
                    // Usuario attributes filters
                    if (paciente.getUsuario() != null) {
                        // Nombre filter
                        if (payload.getNombre() != null && 
                                (paciente.getUsuario().getNombre() == null || 
                                 !paciente.getUsuario().getNombre().toLowerCase().contains(payload.getNombre().toLowerCase()))) {
                            return false;
                        }
                        
                        // Apellido filter
                        if (payload.getApellido() != null && 
                                (paciente.getUsuario().getApellido() == null || 
                                 !paciente.getUsuario().getApellido().toLowerCase().contains(payload.getApellido().toLowerCase()))) {
                            return false;
                        }
                        
                        // Email filter
                        if (payload.getEmail() != null && 
                                (paciente.getUsuario().getEmail() == null || 
                                 !paciente.getUsuario().getEmail().toLowerCase().contains(payload.getEmail().toLowerCase()))) {
                            return false;
                        }
                        
                        // Teléfono filter
                        if (payload.getTelefono() != null && 
                                (paciente.getUsuario().getTelefono() == null || 
                                 !paciente.getUsuario().getTelefono().toLowerCase().contains(payload.getTelefono().toLowerCase()))) {
                            return false;
                        }
                    } else if (payload.getNombre() != null || payload.getApellido() != null || 
                               payload.getEmail() != null || payload.getTelefono() != null) {
                        return false;
                    }
                    
                    return true;
                })
                .toList();
        
        return filteredPacientes.stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a paciente by ID
     * @param id The paciente ID to delete
     */
    @Override
    @Transactional
    public void deletePaciente(int id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente not found with ID: " + id));
        
        // Check if this paciente has associated citas before deletion
        if (paciente.getCitas() != null && !paciente.getCitas().isEmpty()) {
            throw new RuntimeException("Cannot delete paciente because it has associated citas");
        }
        
        try {
            // Delete the paciente
            pacienteRepository.deleteById(id);
            
            // Optionally, delete the associated usuario if needed
            // usuarioRepository.deleteById(paciente.getUsuario().getId());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete paciente due to existing relationships", e);
        }
    }
}

