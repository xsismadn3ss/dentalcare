package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.payload.cita.NotaCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.NotaFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.NotaUpdateRequest;
import com.dentalcare.g5.main.service.cita.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotaController {

    @Autowired
    private NotaService notaService;

    // Obtener notas de la cita
    @GetMapping("/cita/{id}/notas")
    public ResponseEntity<List<NotaDto>> getNotasByCita(@PathVariable int id) {
        return ResponseEntity.ok(notaService.filterNotas(NotaFilterRequest.builder()
                .cita_id(id)
                .build()));
    }

    // Crear nota para la cita
    @PostMapping("/cita/{id}/nota")
    public ResponseEntity<NotaDto> createNota(@PathVariable int id, @RequestBody NotaCreateRequest request) {
        request.setCita_id(id);
        return new ResponseEntity<>(notaService.addNota(request), HttpStatus.CREATED);
    }

    // Actualizar nota de la cita
    @PutMapping("/cita/{id}/nota/{notaId}")
    public ResponseEntity<NotaDto> updateNota(
            @PathVariable int id,
            @PathVariable int notaId,
            @RequestBody NotaUpdateRequest request) {
        return ResponseEntity.ok(notaService.updateNota(request, notaId));
    }

    // Eliminar nota de la cita
    @DeleteMapping("/cita/{id}/nota/{notaId}")
    public ResponseEntity<Void> deleteNota(@PathVariable int id, @PathVariable int notaId) {
        notaService.deleteNota(notaId);
        return ResponseEntity.ok().build();
    }
}
