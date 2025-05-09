package com.dentalcare.g5.main.service.cita.impl;

import com.dentalcare.g5.main.mapper.cita.TratamientoMapper;
import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.entity.cita.Tratamiento;
import com.dentalcare.g5.main.model.payload.cita.TratamientoCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.TratamientoFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.TratamientoUpdateRequest;
import com.dentalcare.g5.main.repository.cita.TratamientoRepository;
import com.dentalcare.g5.main.service.cita.TratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TratamientoServiceImpl implements TratamientoService {
    @Autowired
    private TratamientoRepository tratamientoRepository;
    @Autowired
    private TratamientoMapper tratamientoMapper;

    @Override
    public TratamientoDto addTratamiento(TratamientoCreateRequest payload) {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setNombre(payload.getNombre());
        tratamiento.setPendiente(payload.getPendiente());
        Tratamiento savedTratamiento = tratamientoRepository.save(tratamiento);
        return tratamientoMapper.toDto(savedTratamiento);
    }

    @Override
    public TratamientoDto updateTratamiento(TratamientoUpdateRequest payload, int id) {
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
        tratamiento.setNombre(payload.getNombre());
        Tratamiento updatedTratamiento = tratamientoRepository.save(tratamiento);
        return tratamientoMapper.toDto(updatedTratamiento);
    }

    @Override
    public TratamientoDto getTratamientoById(int id) {
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
        return tratamientoMapper.toDto(tratamiento);
    }

    @Override
    public List<TratamientoDto> getAllTratamientos() {
        List<Tratamiento> tratamientos = tratamientoRepository.findAll();
        return tratamientoMapper.toDtoList(tratamientos);
    }

    @Override
    public List<TratamientoDto> filterTratamientos(TratamientoFilterRequest payload) {
        List<Tratamiento> tratamientos = tratamientoRepository.findAll();
        List<Tratamiento> filteredTratamientos = tratamientos.stream()
                .filter(tratamiento -> {
                    // Filtro por ID
                    if (payload.getId() != null && !payload.getId().equals(tratamiento.getId())) {
                        return false;
                    }
                    // Filtro por nombre
                    return payload.getNombre() == null ||
                           tratamiento.getNombre().toLowerCase().contains(payload.getNombre().toLowerCase());
                })
                .toList();
        return tratamientoMapper.toDtoList(filteredTratamientos);
    }

    @Override
    public void deleteTratamiento(int id) {
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
        tratamientoRepository.delete(tratamiento);
    }
}
