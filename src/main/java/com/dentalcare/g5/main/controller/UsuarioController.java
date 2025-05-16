package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioFilterRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioUpdateRequest;
import com.dentalcare.g5.main.service.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${server.base}/${server.version}/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUser(@PathVariable int id) {
        try {
            log.info("Buscando usuario...");
            UsuarioDto usuario = usuarioService.getUserById(id);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDto>> getAllUsers(Pageable pageable) {
        Page<UsuarioDto> usuariosPage = usuarioService.getAllUsers(pageable);
        return ResponseEntity.ok(usuariosPage);
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody UsuarioCreateRequest payload) {
        try {
            UsuarioDto usuario = usuarioService.addUser(payload);
            return ResponseEntity.status(201).body(usuario);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(400).body("El usuario, telefono o nombre de usuario ya estan en uso");
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<List<UsuarioDto>> filterUsers(@RequestBody UsuarioFilterRequest payload) {
        List<UsuarioDto> usuarios = usuarioService.filterUsers(payload);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> updateUser(@RequestBody UsuarioUpdateRequest payload, @PathVariable int id) {
        try {
            UsuarioDto usuario = usuarioService.updateUser(payload, id);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletUser(@PathVariable int id) {
        try {
            usuarioService.deleteUser(id);
            return ResponseEntity.ok("Usuario eliminado con exito");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/paciente")
    public ResponseEntity<PacienteDto> getPacienteProfile(@PathVariable int id) {
        try {
            PacienteDto paciente = usuarioService.joinPaciente(id);
            return ResponseEntity.ok(paciente);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/doctor")
    public ResponseEntity<DoctorDto> getDoctorProfile(@PathVariable int id) {
        try {
            DoctorDto doctor = usuarioService.joinDoctor(id);
            return ResponseEntity.ok(doctor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/rol")
    public ResponseEntity<RolDto> getUserRol(@PathVariable int id) {
        try {
            RolDto rol = usuarioService.joinRol(id);
            return ResponseEntity.ok(rol);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
