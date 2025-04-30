package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.payload.cita.CitaCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaUpdateRequest;
import com.dentalcare.g5.main.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping
    public ResponseEntity<CitaDto> createCita(@RequestBody CitaCreateRequest request) {
        CitaDto cita = citaService.addCita(request);
        return ResponseEntity.ok(cita);
    }

    @PutMapping
    public ResponseEntity<CitaDto> updateCita(@RequestBody CitaUpdateRequest request) {
        CitaDto cita = citaService.updateCita(request);
        return ResponseEntity.ok(cita);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> getCitaById(@PathVariable int id) {
        System.out.println("Buscando cita...");
        Optional<CitaDto> cita = citaService.getCitaById(id);
        return cita.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CitaDto>> getAllCitas() {
        List<CitaDto> citas = citaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<CitaDto>> filterCitas(@RequestBody CitaFilterRequest request) {
        List<CitaDto> citas = citaService.filterCitas(request);
        return ResponseEntity.ok(citas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable int id) {
        citaService.deleteCita(id);
        return ResponseEntity.noContent().build();
    }
}