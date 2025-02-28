package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.dto.PacienteDto;
import com.dentalcare.g5.main.payload.RequestPacienteDto;
import com.dentalcare.g5.main.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${server.base}/${server.version}/paciente")
public class PacienteController {
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteDto> addPaciente(@RequestBody PacienteDto pacienteDto) {
        return ResponseEntity.ok(pacienteService.addPaciente(pacienteDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<PacienteDto> updatePaciente(@PathVariable int id, @RequestBody PacienteDto pacienteDto) {
        return ResponseEntity.ok(pacienteService.updatePaciente(pacienteDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> getPacienteById(@PathVariable int id) {
        return ResponseEntity.ok(pacienteService.getPacienteById(id));
    }

    @GetMapping
    public ResponseEntity<List<PacienteDto>> getAllPacientes() {
        return ResponseEntity.ok(pacienteService.getAllPacientes());
    }

    @PostMapping("/filter")
    public ResponseEntity<List<PacienteDto>> filterPaciente(@RequestBody RequestPacienteDto payload) {
        return ResponseEntity.ok(pacienteService.filterPaciente(payload));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaciente(@PathVariable int id) {
        PacienteDto paciente = pacienteService.getPacienteById(id);

        if (paciente != null) {
            pacienteService.deletePaciente(id);
            return ResponseEntity.ok("Paciente eliminado correctamente");
        } else {
            return ResponseEntity.status(404).body("Paciente no encontrado");
        }
    }
}
