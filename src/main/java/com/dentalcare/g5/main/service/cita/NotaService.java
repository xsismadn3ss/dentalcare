package com.dentalcare.g5.main.service.cita;

import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.payload.cita.NotaCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.NotaFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.NotaUpdateRequest;

import java.util.List;

/**
 * Service interface for managing notas (notes)
 */
public interface NotaService {
    NotaDto addNota(NotaCreateRequest payload);
    NotaDto updateNota(NotaUpdateRequest payload, int id);
    NotaDto getNotaById(int id);
    List<NotaDto> getAllNotas();
    List<NotaDto> filterNotas(NotaFilterRequest payload);
    void deleteNota(int id);
}

