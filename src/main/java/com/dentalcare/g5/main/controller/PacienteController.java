package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.annotation.NotificarErrores;
import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.payload.paciente.PacienteCreateRequest;
import com.dentalcare.g5.main.model.payload.paciente.PacienteFilterRequest;
import com.dentalcare.g5.main.model.payload.paciente.PacienteUpdateRequest;
import com.dentalcare.g5.main.service.paciente.PacienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NotificarErrores
@RequestMapping("${server.base}/${server.version}/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteDto> addPaciente(PacienteCreateRequest payload){
        PacienteDto paciente = pacienteService.addPaciente(payload);
        return ResponseEntity.status(201).body(paciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaciente(@RequestBody PacienteUpdateRequest payload, @PathVariable int id){
        try{
            PacienteDto paciente = pacienteService.updatePaciente(payload, id);
            return ResponseEntity.ok(paciente);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaciente(@PathVariable int id){
        try{
            PacienteDto paciente = pacienteService.getPacienteById(id);
            return ResponseEntity.ok(paciente);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<PacienteDto>> getAllPacientes(Pageable pageable){
        Page<PacienteDto> pacientes = pacienteService.getAllPacientes(pageable);
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PacienteDto>> fliterPacientes(@RequestBody PacienteFilterRequest payload){
        List<PacienteDto> pacientes = pacienteService.filterPacientes(payload);
        return ResponseEntity.ok(pacientes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaciente(@PathVariable int id){
        pacienteService.deletePaciente(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/{id}/citas")
    public ResponseEntity<List<CitaDto>> getCitas(@PathVariable int id){
        List<CitaDto> citas = pacienteService.joinCitas(id);
        return ResponseEntity.ok(citas);
    }
}
