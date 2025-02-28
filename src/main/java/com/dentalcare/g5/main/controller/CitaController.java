package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.dto.CitaDto;
import com.dentalcare.g5.main.payload.RequestCitaDto;
import com.dentalcare.g5.main.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${server.base}/${server.version}/citas")
public class CitaController {
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    //Para agregar nueva cita
    @PostMapping("/agregar")
    public ResponseEntity<CitaDto> addCita(@RequestBody CitaDto citaDto) {
        CitaDto nuevaCita = citaService.addCita(citaDto);
        return ResponseEntity.ok(nuevaCita);
    }

    //Actualizar cita
    @PutMapping("/actualizar")
    public ResponseEntity<CitaDto> updateCita(@RequestBody CitaDto citaDto) {
        CitaDto citaactualizada = citaService.updateCita(citaDto);
        if (citaactualizada != null) {
            return ResponseEntity.ok(citaactualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Buscar cita por Id
    @GetMapping("buscarId/{id}")
    public ResponseEntity<CitaDto> getCitaById(@PathVariable int id) {
        CitaDto cita = citaService.getCitaById(id);
        if (cita != null) {
            return ResponseEntity.ok(cita);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Para todas las citas
    @GetMapping("/todas")
    public ResponseEntity<List<CitaDto>> getAllCitas() {
        List<CitaDto> citas = citaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }

    //Para filtrar citas
    @PostMapping("/filtrar")
    public ResponseEntity<List<CitaDto>> filterCitas(@RequestBody RequestCitaDto payload) {
        List<CitaDto> citasFiltradas = citaService.filterCitas(payload);
        return ResponseEntity.ok(citasFiltradas);
    }

    //Paa eliminar citas
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable int id) {
        citaService.deleteCita(id);
        return ResponseEntity.noContent().build();
    }
}
