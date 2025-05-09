package com.dentalcare.g5.main.service.doctor.impl;

import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.payload.doctor.EspecialidadFilterRequest;
import com.dentalcare.g5.main.service.doctor.EspecialidadService;

import java.util.List;

public class EscpecialidadServiceImpl implements EspecialidadService {
    @Override
    public EspecialidadDto addEspecialidad(EspecialidadDto especialidadDto) {
        return null;
    }

    @Override
    public EspecialidadDto updateEspecialidad(EspecialidadDto especialidadDto) {
        return null;
    }

    @Override
    public EspecialidadDto getEspecialidadById(int id) {
        return null;
    }

    @Override
    public List<EspecialidadDto> getAllEspecialidades() {
        return List.of();
    }

    @Override
    public List<EspecialidadDto> filterEspecialidades(EspecialidadFilterRequest payload) {
        return List.of();
    }

    @Override
    public void deleteEspecialidad(int id) {

    }
}
