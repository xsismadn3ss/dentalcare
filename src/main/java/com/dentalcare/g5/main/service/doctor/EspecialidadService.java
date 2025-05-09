package com.dentalcare.g5.main.service.doctor;

import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.payload.doctor.EspecialidadCreateRequest;
import com.dentalcare.g5.main.model.payload.doctor.EspecialidadFilterRequest;

import java.util.List;

/**
 * Service interface for managing especialidades (specialties)
 */
public interface EspecialidadService {
    EspecialidadDto addEspecialidad(EspecialidadCreateRequest especialidadDto);
    EspecialidadDto updateEspecialidad(EspecialidadCreateRequest especialidadDto, int id);
    EspecialidadDto getEspecialidadById(int id);
    List<EspecialidadDto> getAllEspecialidades();
    List<EspecialidadDto> filterEspecialidades(EspecialidadFilterRequest payload);
    void deleteEspecialidad(int id);
}

