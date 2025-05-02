package com.dentalcare.g5.main.service.cita;


import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.payload.cita.CitaCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaUpdateRequest;

import java.util.List;



public interface CitaService {
    CitaDto addCita(CitaCreateRequest payload);
    CitaDto updateCita(CitaUpdateRequest payload);
    CitaDto getCitaById(int id);
    List<CitaDto> getAllCitas();
    List<CitaDto> filterCitas(CitaFilterRequest payload);
    void deleteCita(int id);
    List<TratamientoDto> joinTratamientos(int id);
    List<NotaDto> joinNotas(int id);
}