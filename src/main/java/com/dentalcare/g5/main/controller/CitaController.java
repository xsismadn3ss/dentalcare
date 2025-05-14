package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.payload.cita.CitaCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaUpdateRequest;
import com.dentalcare.g5.main.service.cita.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CitaController {

    @Autowired
    private CitaService citaService;

    // Obtener todas las citas
    @GetMapping("/cita")
    public ResponseEntity<List<CitaDto>> getAllCitas() {
        return ResponseEntity.ok(citaService.getAllCitas());
    }

    // Obtener cita por ID
    @GetMapping("/cita/{id}")
    public ResponseEntity<CitaDto> getCitaById(@PathVariable int id) {
        return ResponseEntity.ok(citaService.getCitaById(id));
    }

    // Crear nueva cita
    @PostMapping("/cita")
    public ResponseEntity<CitaDto> createCita(@RequestBody CitaCreateRequest request) {
        return new ResponseEntity<>(citaService.addCita(request), HttpStatus.CREATED);
    }

    // Actualizar cita por ID
    @PutMapping("/cita/{id}")
    public ResponseEntity<CitaDto> updateCita(@RequestBody CitaUpdateRequest request, @PathVariable int id) {
        return ResponseEntity.ok(citaService.updateCita(request, id));
    }

    // Eliminar cita por ID
    @DeleteMapping("/cita/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable int id) {
        citaService.deleteCita(id);
        return ResponseEntity.ok().build();
    }

    // Obtener citas del doctor
    @GetMapping("/doctor/{id}/citas")
    public ResponseEntity<List<CitaDto>> getCitasByDoctorId(@PathVariable int id) {
        CitaFilterRequest filter = CitaFilterRequest.builder()
                .doctor_id(id)
                .build();
        return ResponseEntity.ok(citaService.filterCitas(filter));
    }

    // Obtener citas del paciente
    @GetMapping("/paciente/{id}/citas")
    public ResponseEntity<List<CitaDto>> getCitasByPacienteId(@PathVariable int id) {
        CitaFilterRequest filter = CitaFilterRequest.builder()
                .paciente_id(id)
                .build();
        return ResponseEntity.ok(citaService.filterCitas(filter));
    }
}
