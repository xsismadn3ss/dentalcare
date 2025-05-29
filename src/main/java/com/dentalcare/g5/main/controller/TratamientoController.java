package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.annotation.NotificarErrores;
import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.payload.cita.TratamientoCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.TratamientoFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.TratamientoUpdateRequest;
import com.dentalcare.g5.main.service.cita.TratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NotificarErrores
@RequestMapping("/api")
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    // Obtener tratamientos de la cita
    @GetMapping("/cita/{id}/tratamientos")
    public ResponseEntity<List<TratamientoDto>> getTratamientosByCita(@PathVariable int id) {
        return ResponseEntity.ok(tratamientoService.filterTratamientos(TratamientoFilterRequest.builder()
                .cita_id(id)
                .build()));
    }

    // Crear tratamiento para la cita
    @PostMapping("/cita/{id}/tratamiento")
    public ResponseEntity<TratamientoDto> createTratamiento(
            @PathVariable int id,
            @RequestBody TratamientoCreateRequest request) {
        request.setCita_id(id);
        return new ResponseEntity<>(tratamientoService.addTratamiento(request), HttpStatus.CREATED);
    }

    // Actualizar tratamiento de la cita
    @PutMapping("/cita/{id}/tratamiento/{tratamientoId}")
    public ResponseEntity<TratamientoDto> updateTratamiento(
            @PathVariable int id,
            @PathVariable int tratamientoId,
            @RequestBody TratamientoUpdateRequest request) {
        return ResponseEntity.ok(tratamientoService.updateTratamiento(request, tratamientoId));
    }

    // Eliminar tratamiento de la cita
    @DeleteMapping("/cita/{id}/tratamiento/{tratamientoId}")
    public ResponseEntity<Void> deleteTratamiento(
            @PathVariable int id,
            @PathVariable int tratamientoId) {
        tratamientoService.deleteTratamiento(tratamientoId);
        return ResponseEntity.ok().build();
    }
}
