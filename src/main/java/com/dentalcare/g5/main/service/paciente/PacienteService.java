package com.dentalcare.g5.main.service.paciente;

import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.payload.paciente.PacienteCreateRequest;
import com.dentalcare.g5.main.model.payload.paciente.PacienteFilterRequest;
import com.dentalcare.g5.main.model.payload.paciente.PacienteUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PacienteService {
    PacienteDto addPaciente(PacienteCreateRequest payload);
    PacienteDto updatePaciente(PacienteUpdateRequest payload, int id);
    PacienteDto getPacienteById(int id);
    Page<PacienteDto> getAllPacientes(Pageable pageable);
    List<PacienteDto> filterPacientes(PacienteFilterRequest payload);
    void deletePaciente(int id);
    List<CitaDto> joinCitas(int id);
}
