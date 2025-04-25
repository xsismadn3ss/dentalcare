package com.dentalcare.g5.main.service.cita.impl;

import com.dentalcare.g5.main.mapper.cita.NotaMapper;
import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import com.dentalcare.g5.main.model.entity.cita.Nota;
import com.dentalcare.g5.main.model.payload.cita.NotaCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.NotaFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.NotaUpdateRequest;
import com.dentalcare.g5.main.repository.cita.CitaRepository;
import com.dentalcare.g5.main.repository.cita.NotaRepository;
import com.dentalcare.g5.main.service.cita.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the NotaService interface
 */
@Service
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final CitaRepository citaRepository;
    private final NotaMapper notaMapper;

    @Autowired
    public NotaServiceImpl(NotaRepository notaRepository, 
                          CitaRepository citaRepository, 
                          NotaMapper notaMapper) {
        this.notaRepository = notaRepository;
        this.citaRepository = citaRepository;
        this.notaMapper = notaMapper;
    }

    /**
     * Creates a new note
     * @param payload The note creation request
     * @return The created note DTO
     */
    @Override
    public NotaDto addNota(NotaCreateRequest payload) {
        // Find the cita by ID
        Cita cita = citaRepository.findById(payload.getCitaId())
                .orElseThrow(() -> new RuntimeException("Cita not found with ID: " + payload.getCitaId()));
        
        // Create new note entity
        Nota nota = Nota.builder()
                .titulo(payload.getTitulo())
                .descripcion(payload.getDescripcion())
                .cita(cita)
                .build();
        
        // Save the entity and return mapped DTO
        Nota savedNota = notaRepository.save(nota);
        return notaMapper.toDto(savedNota);
    }

    /**
     * Updates an existing note
     * @param payload The note update request
     * @return The updated note DTO
     */
    @Override
    public NotaDto updateNota(NotaUpdateRequest payload) {
        // Find the note by ID
        Nota existingNota = notaRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Nota not found with ID: " + payload.getId()));
        
        // Update fields
        if (payload.getTitulo() != null) {
            existingNota.setTitulo(payload.getTitulo());
        }
        
        if (payload.getDescripcion() != null) {
            existingNota.setDescripcion(payload.getDescripcion());
        }
        
        // Save updated entity and return mapped DTO
        Nota updatedNota = notaRepository.save(existingNota);
        return notaMapper.toDto(updatedNota);
    }

    /**
     * Retrieves a note by ID
     * @param id The note ID
     * @return The note DTO
     */
    @Override
    public NotaDto getNotaById(int id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota not found with ID: " + id));
        return notaMapper.toDto(nota);
    }

    /**
     * Retrieves all notes
     * @return List of all note DTOs
     */
    @Override
    public List<NotaDto> getAllNotas() {
        List<Nota> notas = notaRepository.findAll();
        return notas.stream()
                .map(notaMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all notes associated with a specific cita
     * @param citaId The cita ID to filter by
     * @return List of note DTOs associated with the specified cita
     */
    @Override
    public List<NotaDto> getNotasByCitaId(int citaId) {
        // Check if cita exists
        if (!citaRepository.existsById(citaId)) {
            throw new RuntimeException("Cita not found with ID: " + citaId);
        }
        
        // In a real implementation, you would add a query method to NotaRepository
        // For simplicity, we'll filter in memory
        List<Nota> allNotas = notaRepository.findAll();
        
        List<Nota> citaNotas = allNotas.stream()
                .filter(nota -> nota.getCita() != null && nota.getCita().getId() == citaId)
                .collect(Collectors.toList());
                
        return citaNotas.stream()
                .map(notaMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters notes based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered note DTOs
     */
    @Override
    public List<NotaDto> filterNotas(NotaFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<Nota> allNotas = notaRepository.findAll();
        
        List<Nota> filteredNotas = allNotas.stream()
                .filter(nota -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(nota.getId())) {
                        return false;
                    }
                    
                    // Titulo filter
                    if (payload.getTitulo() != null && !nota.getTitulo().toLowerCase()
                            .contains(payload.getTitulo().toLowerCase())) {
                        return false;
                    }
                    
                    // Descripcion filter
                    if (payload.getDescripcion() != null && !nota.getDescripcion().toLowerCase()
                            .contains(payload.getDescripcion().toLowerCase())) {
                        return false;
                    }
                    
                    // Cita ID filter
                    if (payload.getCitaId() != null && (nota.getCita() == null || 
                            !payload.getCitaId().equals(nota.getCita().getId()))) {
                        return false;
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
        
        return filteredNotas.stream()
                .map(notaMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a note by ID
     * @param id The note ID to delete
     */
    @Override
    public void deleteNota(int id) {
        if (!notaRepository.existsById(id)) {
            throw new RuntimeException("Nota not found with ID: " + id);
        }
        notaRepository.deleteById(id);
    }
}

