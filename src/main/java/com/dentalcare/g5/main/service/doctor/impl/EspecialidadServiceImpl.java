package com.dentalcare.g5.main.service.doctor.impl;

import com.dentalcare.g5.main.mapper.doctor.EspecialidadMapper;
import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.entity.doctor.Especialidad;
import com.dentalcare.g5.main.model.payload.doctor.EspecialidadCreateRequest;
import com.dentalcare.g5.main.model.payload.doctor.EspecialidadFilterRequest;
import com.dentalcare.g5.main.repository.doctor.EspecialidadRepository;
import com.dentalcare.g5.main.service.doctor.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {
    @Autowired
    private EspecialidadRepository especialidadRepository;
    @Autowired
    private EspecialidadMapper especialidadMapper;

    @Override
    public EspecialidadDto addEspecialidad(EspecialidadCreateRequest especialidadDto) {
        Especialidad especialidad = new Especialidad();
        especialidad.setName(especialidadDto.getName());
        Especialidad savedEspecialidad = especialidadRepository.save(especialidad);
        return especialidadMapper.toDto(savedEspecialidad);
    }

    @Override
    public EspecialidadDto updateEspecialidad(EspecialidadCreateRequest especialidadDto, int id) {
        Especialidad especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        especialidad.setName(especialidadDto.getName());
        Especialidad updatedEspecialidad = especialidadRepository.save(especialidad);
        return especialidadMapper.toDto(updatedEspecialidad);
    }

    @Override
    public EspecialidadDto getEspecialidadById(int id) {
        Especialidad especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        return especialidadMapper.toDto(especialidad);
    }

    @Override
    public List<EspecialidadDto> getAllEspecialidades() {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        return especialidadMapper.toDto(especialidades);
    }

    @Override
    public List<EspecialidadDto> filterEspecialidades(EspecialidadFilterRequest payload) {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        List<Especialidad> filteredEspecialidades = especialidades.stream()
                .filter(especialidad -> {
                    // Filtro por ID
                    if (payload.getId() != null && !payload.getId().equals(especialidad.getId())) {
                        return false;
                    }
                    // Filtro por nombre
                    return payload.getName() == null || 
                           especialidad.getName().toLowerCase().contains(payload.getName().toLowerCase());
                })
                .toList();
        return especialidadMapper.toDto(especialidades);
    }

    @Override
    public void deleteEspecialidad(int id) {
        Especialidad especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        especialidadRepository.delete(especialidad);
    }
}
