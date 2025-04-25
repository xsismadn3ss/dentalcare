package com.dentalcare.g5.main.service;

import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.payload.paciente.PacienteFilterRequest;
import com.dentalcare.g5.main.model.payload.paciente.PacienteUpdateRequest;

import java.util.List;

public interface PacienteService {
    PacienteDto addPaciente(PacienteDto pacienteDto);
    PacienteDto updatePaciente(PacienteUpdateRequest payload);
    PacienteDto getPacienteById(int id);
    List<PacienteDto> getAllPacientes();
    List<PacienteDto> filterPaciente(PacienteFilterRequest payload);
    void deletePaciente(int id);
}
