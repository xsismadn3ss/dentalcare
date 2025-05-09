package com.dentalcare.g5.main.service.paciente.impl;

import com.dentalcare.g5.main.mapper.PacienteMapper;
import com.dentalcare.g5.main.mapper.cita.CitaMapper;
import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.entity.Paciente;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import com.dentalcare.g5.main.model.payload.paciente.PacienteCreateRequest;
import com.dentalcare.g5.main.model.payload.paciente.PacienteFilterRequest;
import com.dentalcare.g5.main.model.payload.paciente.PacienteUpdateRequest;
import com.dentalcare.g5.main.repository.PacienteRepository;
import com.dentalcare.g5.main.repository.cita.CitaRepository;
import com.dentalcare.g5.main.service.paciente.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
public class PacienteServiceImpl implements PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private CitaMapper citaMapper;

    @Override
    public PacienteDto addPaciente(PacienteCreateRequest payload) {
        Paciente paciente = new Paciente();
        paciente.setFechaRegistro(LocalDate.now());
        paciente.setUsuario_id(payload.getUsuario_id());
        Paciente savedPaciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(savedPaciente);
    }

    @Override
    public PacienteDto updatePaciente(PacienteUpdateRequest payload) {
        Paciente paciente = pacienteRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        paciente.setUsuario_id(payload.getUsuario_id());
        Paciente updatedPaciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(updatedPaciente);
    }

    @Override
    public PacienteDto getPacienteById(int id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        return pacienteMapper.toDto(paciente);
    }

    @Override
    public List<PacienteDto> getAllPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PacienteDto> filterPacientes(PacienteFilterRequest payload) {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<Paciente> pacientes_filtered = pacientes.stream()
                .filter(paciente -> {
                    if (payload.getId() != null && !payload.getId().equals(paciente.getId())) {
                        return false;
                    }
                    if (payload.getUsuario_id() != null && !payload.getUsuario_id().equals(paciente.getUsuario_id())) {
                        return false;
                    }
                    return true;
                }).toList();
        return pacienteMapper.toDto(pacientes_filtered);
    }

    @Override
    public void deletePaciente(int id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public List<CitaDto> joinCitas(int id) {
        List<Cita> citas = citaRepository.getByPacienteId(id);
        return citas.stream()
                .map(citaMapper::toDto)
                .collect(Collectors.toList());
    }
}
