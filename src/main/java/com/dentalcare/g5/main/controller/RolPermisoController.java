package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.payload.usuario.RolPermisoCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.RolPermisoFilterRequest;
import com.dentalcare.g5.main.service.usuario.RolPermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolPermisoController {

    @Autowired
    private RolPermisoService rolPermisoService;

    // Para Obtener todas las relaciones Rol-Permiso
    @GetMapping("/rolper")
    public ResponseEntity<List<RolPermisoDto>> getAllRolPermisos() {
        return ResponseEntity.ok(rolPermisoService.getAllRolPer());
    }

    // Para Obtener una relación Rol-Permiso por ID
    @GetMapping("/rolper/{id}")
    public ResponseEntity<RolPermisoDto> getRolPermisoById(@PathVariable int id) {
        return ResponseEntity.ok(rolPermisoService
                .getRolPerById(id));
    }

    // Crear una nueva relación Rol-Permiso
    @PostMapping("/rolper")
    public ResponseEntity<RolPermisoDto> createRolPermiso(@RequestBody RolPermisoCreateRequest request) {
        return new ResponseEntity<>(rolPermisoService
                .addRolPer(request), HttpStatus.CREATED);
    }

    // Actualizar una relación Rol-Permiso existente
    @PutMapping("/rolper/{id}")
    public ResponseEntity<RolPermisoDto> updateRolPermiso(@RequestBody RolPermisoCreateRequest request,
                                                          @PathVariable int id) {
        return ResponseEntity.ok(rolPermisoService.updateRolPer(request, id));
    }

    // Eliminar una relación Rol-Permiso
    @DeleteMapping("/rolper/{id}")
    public ResponseEntity<Void> deleteRolPermiso(@PathVariable int id) {
        rolPermisoService.deleteRolPer(id);
        return ResponseEntity.ok().build();
    }

    // Filtrar relaciones Rol-Permiso        ( AUN NO IMPLEMENTADO )
    @PostMapping("/rolper/filter")
    public ResponseEntity<List<RolPermisoDto>> filterRolPermiso(@RequestBody RolPermisoFilterRequest request) {
        return ResponseEntity.ok(rolPermisoService.filterRolPer(request));
    }
}