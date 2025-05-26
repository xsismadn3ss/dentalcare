package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.payload.doctor.DoctorCreateRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorFilterRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorUpdateRequest;
import com.dentalcare.g5.main.service.doctor.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${server.base}/${server.version}/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDto> addDoctor(@RequestBody DoctorCreateRequest payload) {
        DoctorDto doctor = doctorService.addDoctor(payload);
        return ResponseEntity.status(201).body(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@RequestBody DoctorUpdateRequest payload, @PathVariable int id) {
        try {
            DoctorDto doctor = doctorService.updateDoctor(payload, id);
            return ResponseEntity.ok(doctor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctor(@PathVariable int id) {
        try {
            DoctorDto doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(doctor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<DoctorDto>> getAllDoctors(Pageable pageable) {
        return ResponseEntity.ok(doctorService.getAllDoctors(pageable));
    }

    @PostMapping("/filter")
    public ResponseEntity<List<DoctorDto>> filterDoctors(@RequestBody DoctorFilterRequest payload) {
        List<DoctorDto> doctores = doctorService.filterDoctors(payload);
        if (doctores.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(doctores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Uusario eliminado con exito");
    }

    @GetMapping("/{id}/especialidad")
    public ResponseEntity<EspecialidadDto> getEspecialidad(@PathVariable int id) {
        EspecialidadDto especialidad = doctorService.joinEspecialidad(id);
        return ResponseEntity.ok(especialidad);
    }

    @GetMapping("/{id}/citas")
    public ResponseEntity<List<CitaDto>> getCitas(@PathVariable int id) {
        return ResponseEntity.ok(doctorService.joinCitas(id));
    }
}
