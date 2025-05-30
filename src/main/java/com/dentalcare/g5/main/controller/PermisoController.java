package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.annotation.NotificarErrores;
import com.dentalcare.g5.main.model.dto.usuario.PermisoDto;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.payload.usuario.PermisoCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.PermisoFilterRequest;
import com.dentalcare.g5.main.model.payload.usuario.PermisoUpdateRequest;
import com.dentalcare.g5.main.service.usuario.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NotificarErrores
@RequestMapping("/api")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    //Para Obtener todos los permisos
    @GetMapping("/permiso")
    public ResponseEntity<List<PermisoDto>> getAllPermisos() {
        return ResponseEntity.ok(permisoService.getAllPermisos());
    }

    // Para Obtener permiso por ID
    @GetMapping("/permiso/{id}")
    public ResponseEntity<PermisoDto> getPermisoById(@PathVariable int id) {
        return ResponseEntity.ok(permisoService.getPermisoById(id));
    }

    // Para Crear nuevo permiso
    @PostMapping("/permiso")
    public ResponseEntity<PermisoDto> createPermiso(@RequestBody PermisoCreateRequest request) {
        return new ResponseEntity<>(permisoService.addPermiso(request), HttpStatus.CREATED);
    }

    // Para Actualizar permiso por ID
    @PutMapping("/permiso/{id}")
    public ResponseEntity<PermisoDto> updatePermiso(@RequestBody PermisoUpdateRequest request, @PathVariable int id) {
        return ResponseEntity.ok(permisoService.updatePermiso(request, id));
    }

    // Eliminar permisos por ID
    @DeleteMapping("/permiso/{id}")
    public ResponseEntity<Void> deletePermiso(@PathVariable int id) {
        permisoService.deletePermiso(id);
        return ResponseEntity.ok().build();
    }

    // Filtrar permisos  ( AUN NO IMPLEMENTADO )
    @PostMapping("/permiso/filter")
    public ResponseEntity<List<PermisoDto>> filterPermisos(@RequestBody PermisoFilterRequest request) {
        return ResponseEntity.ok(permisoService.filterPermisos(request));
    }

    // Obtener relaciones Rol-Permiso asociadas a un permiso
    @GetMapping("/permiso/{id}/rolpermisos")
    public ResponseEntity<List<RolPermisoDto>> getRolPermisos(@PathVariable int id) {
        return ResponseEntity.ok(permisoService.joinRolPermiso(id));
    }
}