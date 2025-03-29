package com.dentalcare.g5.main.service;

import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.payload.RequestPacienteDto;

import java.util.List;

public interface PacienteService {
    PacienteDto addPaciente(PacienteDto pacienteDto);
    PacienteDto updatePaciente(PacienteDto pacienteDto);
    PacienteDto getPacienteById(int id);
    List<PacienteDto> getAllPacientes();
    List<PacienteDto> filterPaciente(RequestPacienteDto payload);
    void deletePaciente(int id);
}
