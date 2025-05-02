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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the EspecialidadService interface
 */
@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;
    
    @Autowired
    private EspecialidadMapper especialidadMapper;

    @Override
    @Transactional
    public EspecialidadDto addEspecialidad(EspecialidadDto especialidadDto) {
        // Validar que el nombre no sea nulo
        if (especialidadDto.getName() == null || especialidadDto.getName().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la especialidad es requerido");
        }

        try {
            Especialidad especialidad = especialidadMapper.toEntity(especialidadDto);
            Especialidad savedEspecialidad = especialidadRepository.save(especialidad);
            return especialidadMapper.toDto(savedEspecialidad);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se pudo crear la especialidad debido a una violación de restricción", e);
        }
    }

    @Override
    @Transactional
    public EspecialidadDto updateEspecialidad(EspecialidadDto especialidadDto) {
        // Validar que el ID y el nombre no sean nulos
        if (especialidadDto.getId() == null) {
            throw new RuntimeException("El ID de la especialidad es requerido para la actualización");
        }
        if (especialidadDto.getName() == null || especialidadDto.getName().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la especialidad es requerido");
        }

        try {
            Especialidad existingEspecialidad = especialidadRepository.findById(especialidadDto.getId())
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + especialidadDto.getId()));
            
            existingEspecialidad.setName(especialidadDto.getName());
            
            Especialidad updatedEspecialidad = especialidadRepository.save(existingEspecialidad);
            return especialidadMapper.toDto(updatedEspecialidad);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se pudo actualizar la especialidad debido a una violación de restricción", e);
        }
    }

    @Override
    public EspecialidadDto getEspecialidadById(int id) {
        Especialidad especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad not found with ID: " + id));
        return especialidadMapper.toDto(especialidad);
    }

    @Override
    public List<EspecialidadDto> getAllEspecialidades() {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        return especialidades.stream()
                .map(especialidadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EspecialidadDto> filterEspecialidades(EspecialidadFilterRequest payload) {
        List<Especialidad> allEspecialidades = especialidadRepository.findAll();
        
        List<Especialidad> filteredEspecialidades = allEspecialidades.stream()
                .filter(especialidad -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(especialidad.getId())) {
                        return false;
                    }
                    
                    // Name filter
                    return payload.getName() == null ||
                            (especialidad.getName() != null &&
                                    especialidad.getName().toLowerCase().contains(payload.getName().toLowerCase()));
                })
                .toList();
        
        return filteredEspecialidades.stream()
                .map(especialidadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteEspecialidad(int id) {
        Especialidad especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
                
        // Verificar si hay doctores asociados
        if (especialidad.getDoctores() != null && !especialidad.getDoctores().isEmpty()) {
            throw new RuntimeException("No se puede eliminar la especialidad porque tiene doctores asociados");
        }

        try {
            especialidadRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar la especialidad debido a relaciones existentes", e);
        }
    }
}