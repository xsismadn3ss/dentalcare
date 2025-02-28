package com.dentalcare.g5.main.service.impl;

import com.dentalcare.g5.main.dto.PacienteDto;
import com.dentalcare.g5.main.payload.RequestPacienteDto;
import com.dentalcare.g5.main.service.PacienteService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {
    private final List<PacienteDto> pacientes = new ArrayList<>();

    public PacienteServiceImpl() {
        generarDataDummy();
    }

    private void generarDataDummy() {
        pacientes.add(new PacienteDto(1, "Juan", "Perez", "123456789", "juan.perez@example.com", LocalDate.of(2023, 5, 10)));
        pacientes.add(new PacienteDto(2, "Maria", "Gomez", "987654321", "maria.gomez@example.com", LocalDate.of(2022, 8, 15)));
        pacientes.add(new PacienteDto(3, "Carlos", "Lopez", "1122334455", "carlos.lopez@example.com", LocalDate.of(2021, 3, 20)));
    }

    @Override
    public PacienteDto addPaciente(PacienteDto pacienteDto) {
        pacienteDto.setId(pacientes.size() + 1);
        pacienteDto.setFechaRegistro(LocalDate.now());
        pacientes.add(pacienteDto);
        return pacienteDto;
    }

    @Override
    public PacienteDto updatePaciente(PacienteDto pacienteDto) {
        Optional<PacienteDto> existingPaciente = pacientes.stream()
                .filter(p -> p.getId().equals(pacienteDto.getId()))
                .findFirst();

        if (existingPaciente.isPresent()) {
            PacienteDto paciente = existingPaciente.get();
            paciente.setNombre(pacienteDto.getNombre());
            paciente.setApellido(pacienteDto.getApellido());
            paciente.setTelefono(pacienteDto.getTelefono());
            paciente.setEmail(pacienteDto.getEmail());
            return paciente;
        }
        return null;
    }

    @Override
    public PacienteDto getPacienteById(int id) {
        return pacientes.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<PacienteDto> getAllPacientes() {
        return new ArrayList<>(pacientes);
    }

    @Override
    public List<PacienteDto> filterPaciente(RequestPacienteDto payload) {
        return pacientes.stream().
                filter(paciente ->
                        (payload.getId() != paciente.getId() || payload.getId() == paciente.getId()) &&
                                (payload.getNombre() == null || payload.getNombre().contains((paciente.getNombre()))) &&
                                (payload.getApellido() == null || payload.getApellido().contains(paciente.getApellido())) &&
                                (payload.getEmail()) == null || payload.getEmail().contains((paciente.getEmail()))).toList();
    }

    @Override
    public void deletePaciente(int id) {
        pacientes.removeIf(p -> p.getId().equals(id));
    }
}
