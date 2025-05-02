package com.dentalcare.g5.main.service.cita;

import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.payload.cita.TratamientoCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.TratamientoFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.TratamientoUpdateRequest;

import java.util.List;

/**
 * Service interface for managing tratamientos (treatments)
 */
public interface TratamientoService {
    TratamientoDto addTratamiento(TratamientoCreateRequest payload);
    TratamientoDto updateTratamiento(TratamientoUpdateRequest payload);
    TratamientoDto getTratamientoById(int id);
    List<TratamientoDto> getAllTratamientos();
    List<TratamientoDto> filterTratamientos(TratamientoFilterRequest payload);
    void deleteTratamiento(int id);
    List<TratamientoDto> getTratamientosByCitaId(int citaId);
}






