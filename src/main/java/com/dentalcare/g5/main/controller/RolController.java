package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.payload.usuario.RolCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.RolFilterRequest;
import com.dentalcare.g5.main.service.usuario.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolController {

    @Autowired
    private RolService rolService;

    // Obtener todos los roles
    @GetMapping("/rol")
    public ResponseEntity<List<RolDto>> getAllRoles() {
        return ResponseEntity.ok(rolService.getAllRoles());
    }

    // Obtener rol por ID
    @GetMapping("/rol/{id}")
    public ResponseEntity<RolDto> getRolById(@PathVariable int id) {
        return ResponseEntity.ok(rolService.getRolById(id));
    }

    // Crear nuevo rol
    @PostMapping("/rol")
    public ResponseEntity<RolDto> createRol(@RequestBody RolCreateRequest request) {
        return new ResponseEntity<>(rolService
        .addRol(request), HttpStatus.CREATED);
    }

    // Actualizar rol por ID
    @PutMapping("/rol/{id}")
    public ResponseEntity<RolDto> updateRol(@RequestBody RolCreateRequest request, @PathVariable int id) {
        return ResponseEntity.ok(rolService.updateRol(request, id));
    }

    // Eliminar rol por ID
    @DeleteMapping("/rol/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable int id) {
        rolService.deleteRol(id);
        return ResponseEntity.ok()
        .build();
    }

    // Filtrar roles              ( AUN NO IMPLEMENTADO )
    @PostMapping("/rol/filter")
    public ResponseEntity<List<RolDto>> filterRoles(@RequestBody RolFilterRequest request) {
        return ResponseEntity.ok(rolService.filterRoles(request));
    }

    // Obtener permisos asociados a un rol
    @GetMapping("/rol/{id}/permisos")
    public ResponseEntity<List<RolPermisoDto>> getRolPermisos(@PathVariable int id) {
        return ResponseEntity.ok(rolService
        .joinRolPermisos(id));
    }
}
