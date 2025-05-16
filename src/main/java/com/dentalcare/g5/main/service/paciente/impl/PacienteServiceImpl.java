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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Paciente savedPaciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(savedPaciente);
    }

    @Override
    public PacienteDto updatePaciente(PacienteUpdateRequest payload, int id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
        Paciente updatedPaciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(updatedPaciente);
    }

    @Override
    public PacienteDto getPacienteById(int id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
        return pacienteMapper.toDto(paciente);
    }

    @Override
    public Page<PacienteDto> getAllPacientes(Pageable pageable) {
        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);
        return pacientes.map(pacienteMapper::toDto);
    }

    @Override
    public List<PacienteDto> filterPacientes(PacienteFilterRequest payload) {
        List<Paciente> pacientes_filtered = pacienteRepository.filterPacientes(
                payload.getId(),
                payload.getUsuario_id(),
                payload.getFechaRegistroDesde(),
                payload.getFechaRegistroHasta(),
                payload.getNombre(),
                payload.getApellido(),
                payload.getEmail(),
                payload.getTelefono()
        );
        return pacienteMapper.toDto(pacientes_filtered);
    }

    @Override
    public void deletePaciente(int id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public List<CitaDto> joinCitas(int id) {
        List<Cita> citas = citaRepository.getByPacienteId(id);
        return citaMapper.toDtoList(citas);
    }
}
